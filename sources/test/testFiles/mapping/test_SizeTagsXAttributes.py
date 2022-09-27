import pytest
import xml.etree.ElementTree as ET

jar = 'mapping'

def xAttributesWalk(RootTag, List):
    for elements in RootTag.iter('size'):
        List.append(elements.get('x')) 
            
def testSizeTagsXAttributes(expected, output):

    outputFilePath = output + "/mappingToPlacing.xml"
    expectedFilePath = expected
    expectedXAttributesList = []
    outputXAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctX = 0 
    errorX = 0
    
    try:    
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
    
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()
    
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")    

    xAttributesWalk(expectedRootTag, expectedXAttributesList)
    
    xAttributesWalk(outputRootTag, outputXAttributesList)   
    
    expectedListLength = len(expectedXAttributesList)
    passCounter = expectedListLength
    
    try:
        for i in range(expectedListLength):
            if(expectedXAttributesList[i] != outputXAttributesList[i]):
                correctX = expectedXAttributesList[i]
                errorX = outputXAttributesList[i]
                passCounter = expectedListLength - 1
                break
                
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    try:
        assert expectedListLength == passCounter
    except AssertionError as exception:
        pytest.fail("Mismatched x atributes. One x is '" + str(errorX) + "' but the correct should be '" + str(correctX) + "'.")    
    
    
    
    
    
