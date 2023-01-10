import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def typeAttributesWalk(RootTag, List):
    for elements in RootTag.iter('property'):
        List.append(elements.get('type')) 
            
def testPropertyElements_TypeAttributes(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedPropertyAttributesList = []
    outputPropertyAttributesList = []
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

    typeAttributesWalk(expectedRootTag, expectedPropertyAttributesList)
    
    typeAttributesWalk(outputRootTag, outputPropertyAttributesList)   
    
    expectedListLength = len(expectedPropertyAttributesList)
    outputListLength = len(outputPropertyAttributesList)
    passCounter = expectedListLength
    
    try:
        if (expectedListLength == 0 and outputListLength != 0):
            passCounter = -1
            errorType = outputPropertyAttributesList[0]

        for i in range(expectedListLength):
            if(expectedPropertyAttributesList[i] != outputPropertyAttributesList[i]):
                correctType = expectedPropertyAttributesList[i]
                errorType = outputPropertyAttributesList[i]
                passCounter = expectedListLength - 1
                break
        assert expectedListLength == passCounter, f"test_PropertyElements_TypeAttributes: The value of the property element type attribute is '{errorType}' in the output file, but it should be '{correctType}' based on the expected file."
                
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
  

    
    
    
