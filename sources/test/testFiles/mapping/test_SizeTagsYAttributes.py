import pytest
import xml.etree.ElementTree as ET

jar = 'mapping'

def yAttributesWalk(RootTag, List):
    for elements in RootTag.iter('size'):
        List.append(elements.get('y')) 
            
def testSizeTagsYAttributes(expected, output):

    outputFilePath = output + "/mappingToPlacing.xml"
    expectedFilePath = expected
    expectedYAttributesList = []
    outputYAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctY = 0 
    errorY = 0
    
    try:    
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
    
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()
    
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")    

    yAttributesWalk(expectedRootTag, expectedYAttributesList)
    
    yAttributesWalk(outputRootTag, outputYAttributesList)   
    
    expectedListLength = len(expectedYAttributesList)
    passCounter = expectedListLength
    
    try:
        for i in range(expectedListLength):
            if(expectedYAttributesList[i] != outputYAttributesList[i]):
                correctY = expectedYAttributesList[i]
                errorY = outputYAttributesList[i]
                passCounter = expectedListLength - 1
                break
                
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    try:
        assert expectedListLength == passCounter
    except AssertionError as exception:
        pytest.fail("Mismatched y atributes. One y is '" + str(errorY) + "' but the correct should be '" + str(correctY) + "'.")    
    
    
    
    
    
