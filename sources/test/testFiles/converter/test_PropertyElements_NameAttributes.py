import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def nameAttributesWalk(RootTag, List):
    for elements in RootTag.iter('property'):
        List.append(elements.get('name')) 
            
def testPropertyElements_NameAttributes(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedPropertyAttributesList = []
    outputPropertyAttributesList = []
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

    nameAttributesWalk(expectedRootTag, expectedPropertyAttributesList)
    
    nameAttributesWalk(outputRootTag, outputPropertyAttributesList)   
    
    expectedListLength = len(expectedPropertyAttributesList)
    outputListLength = len(outputPropertyAttributesList)
    passCounter = expectedListLength
    
    try:
        if (expectedListLength == 0 and outputListLength != 0):
            passCounter = -1
            errorName = outputPropertyAttributesList[0]

        for i in range(expectedListLength):
            if(expectedPropertyAttributesList[i] != outputPropertyAttributesList[i]):
                correctName = expectedPropertyAttributesList[i]
                errorName = outputPropertyAttributesList[i]
                passCounter = expectedListLength - 1
                break
        assert expectedListLength == passCounter, f"test_PropertyElements_NameAttributes: The value of the property element name attribute is '{errorName}' in the output file, but it should be '{correctName}' based on the expected file."
                
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
        

    
    
    
