import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def typeAttribute(RootTag, List):
    List.append(RootTag.get('type'))  
            
def testRootElementTypeAttribute(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedTypeAttributesList = []
    outputTypeAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctType = "None" 
    errorType = 0
    
    try:    
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
    
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()
    
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")    

    typeAttribute(expectedRootTag, expectedTypeAttributesList)
    
    typeAttribute(outputRootTag, outputTypeAttributesList)   
    
    expectedListLength = len(expectedTypeAttributesList)
    outputListLength = len(outputTypeAttributesList)
    passCounter = expectedListLength
    
    try:
        if (expectedListLength == 0 and outputListLength != 0):
            passCounter = -1
            errorType = outputTypeAttributesList[0]

        for i in range(expectedListLength):
            if(expectedTypeAttributesList[i] != outputTypeAttributesList[i]):
                correctType = expectedTypeAttributesList[i]
                errorType = outputTypeAttributesList[i]
                passCounter = expectedListLength - 1
                break
            
        assert expectedListLength == passCounter, f"test_RootElementTypeAttribute: The value of the root element type attribute is '{errorType}' in the output file, but it should be '{correctType}' based on the expected file."
                
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
  
    
    
    

