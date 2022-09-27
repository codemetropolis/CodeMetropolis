import pytest
import xml.etree.ElementTree as ET

jar = 'mapping'

def zAttributesWalk(RootTag, List):
    for elements in RootTag.iter('size'):
        List.append(elements.get('z')) 
            
def testSizeTagsZAttributes(expected, output):

    outputFilePath = output + "/mappingToPlacing.xml"
    expectedFilePath = expected
    expectedZAttributesList = []
    outputZAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctZ = 0 
    errorZ = 0
    
    try:    
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
    
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()
    
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")    

    zAttributesWalk(expectedRootTag, expectedZAttributesList)
    
    zAttributesWalk(outputRootTag, outputZAttributesList)   
    
    expectedListLength = len(expectedZAttributesList)
    passCounter = expectedListLength
    
    try:
        for i in range(expectedListLength):
            if(expectedZAttributesList[i] != outputZAttributesList[i]):
                correctZ = expectedZAttributesList[i]
                errorZ = outputZAttributesList[i]
                passCounter = expectedListLength - 1
                break
                
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    try:
        assert expectedListLength == passCounter
    except AssertionError as exception:
        pytest.fail("Mismatched z atributes. One z is '" + str(errorZ) + "' but the correct should be '" + str(correctZ) + "'.")    
    
    
    
    
    
