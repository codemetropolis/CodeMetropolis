import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def nameAttribute(RootTag, List):
    List.append(RootTag.get('name'))  
            
def testRootElementNameAttribute(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedNameAttributesList = []
    outputNameAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctName = "None"  
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
    outputListLength = len(outputNameAttributesList)
    passCounter = expectedListLength
    
    try:
        if (expectedListLength == 0 and outputListLength != 0):
            passCounter = -1
            errorName = outputNameAttributesList[0]

        for i in range(expectedListLength):
            if(expectedNameAttributesList[i] != outputNameAttributesList[i]):
                correctName = expectedNameAttributesList[i]
                errorName = outputNameAttributesList[i]
                passCounter = expectedListLength - 1
                break
        assert expectedListLength == passCounter, f"test_RootElementNameAttribute: The value of the root element name attribute is '{errorName}' in the output file, but it should be '{correctName}' based on the expected file."
                

    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    
    
    
    
