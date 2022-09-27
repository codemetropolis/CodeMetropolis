import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def typeAttributesWalk(RootTag, List):
    for elements in RootTag.iter('property'):
        List.append(elements.get('type')) 
            
def testPropertyTagsTypeAttributes(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedPropertyAttributesList = []
    outputPropertyAttributesList = []
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

    typeAttributesWalk(expectedRootTag, expectedPropertyAttributesList)
    
    typeAttributesWalk(outputRootTag, outputPropertyAttributesList)   
    
    expectedListLength = len(expectedPropertyAttributesList)
    passCounter = expectedListLength
    
    try:
        for i in range(expectedListLength):
            if(expectedPropertyAttributesList[i] != outputPropertyAttributesList[i]):
                correctType = expectedPropertyAttributesList[i]
                errorType = outputPropertyAttributesList[i]
                passCounter = expectedListLength - 1
                break
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    try:
        assert expectedListLength == passCounter
    except AssertionError as exception:
        pytest.fail("Mismatched type atributes. One type is '" + str(errorType) + "' but the correct should be '" + str(correctType) + "'.")    
        
    
    
    
    
