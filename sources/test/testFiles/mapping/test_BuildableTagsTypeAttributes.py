import pytest
import xml.etree.ElementTree as ET

jar = 'mapping'

def typeAttributesWalk(RootTag, List):
    for elements in RootTag.iter('buildable'):
        List.append(elements.get('type')) 
            
def testBuildableTagsTypeAttributes(expected, output):

    outputFilePath = output + "/mappingToPlacing.xml"
    expectedFilePath = expected
    expectedTypeAttributesList = []
    outputTypeAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctType = 0 
    errorType = 0
    
    try:    
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
    
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()
    
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")    

    typeAttributesWalk(expectedRootTag, expectedTypeAttributesList)
    
    typeAttributesWalk(outputRootTag, outputTypeAttributesList)   
    
    expectedListLength = len(expectedTypeAttributesList)
    passCounter = expectedListLength
    
    try:
        for i in range(expectedListLength):
            if(expectedTypeAttributesList[i] != outputTypeAttributesList[i]):
                correctType = expectedTypeAttributesList[i]
                errorType = outputTypeAttributesList[i]
                passCounter = expectedListLength - 1
                break
                
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    try:
        assert expectedListLength == passCounter
    except AssertionError as exception:
        pytest.fail("Mismatched type atributes. One type is '" + str(errorType) + "' but the correct should be '" + str(correctType) + "'.")    
    
    
    
    
    
