import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def nameAttributesWalk(RootTag, List):
    for elements in RootTag.iter('property'):
        List.append(elements.get('name')) 
            
def testPropertyTagsNameAttributes(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedPropertyAttributesList = []
    outputPropertyAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctName = 0 
    errorName = 0
    
    try:    
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
    
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()
    
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")    

    nameAttributesWalk(expectedRootTag, expectedPropertyAttributesList)
    
    nameAttributesWalk(outputRootTag, outputPropertyAttributesList)   
    
    expectedListLength = len(expectedPropertyAttributesList)
    passCounter = expectedListLength
    
    try:
        for i in range(expectedListLength):
            if(expectedPropertyAttributesList[i] != outputPropertyAttributesList[i]):
                correctName = expectedPropertyAttributesList[i]
                errorName = outputPropertyAttributesList[i]
                passCounter = expectedListLength - 1
                break
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    try:
        assert expectedListLength == passCounter
    except AssertionError as exception:
        pytest.fail("Mismatched name atributes. One name is '" + str(errorName) + "' but the correct should be '" + str(correctName) + "'.")    
        
    
    
    
    
