import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def nameAttribute(RootTag, List):
    List.append(RootTag.get('name'))  
            
def testElementTagsNameAttributes(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedNameAttributesList = []
    outputNameAttributesList = []
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

    nameAttribute(expectedRootTag, expectedNameAttributesList)
    
    nameAttribute(outputRootTag, outputNameAttributesList)   
    
    expectedListLength = len(expectedNameAttributesList)
    passCounter = expectedListLength
    
    try:
        for i in range(expectedListLength):
            if(expectedNameAttributesList[i] != outputNameAttributesList[i]):
                correctName = expectedNameAttributesList[i]
                errorName = outputNameAttributesList[i]
                passCounter = expectedListLength - 1
                break

    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    try:
        assert expectedListLength == passCounter
    except AssertionError as exception:
        pytest.fail("Mismatched name atributes in root tag. One name is '" + str(errorName) + "' but the correct should be '" + str(correctName) + "'.")    
    
    
    
    
    
