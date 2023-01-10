import pytest
import xml.etree.ElementTree as ET

jar = 'placing'

def valueAttributesWalk(RootTag, List):
    for elements in RootTag.iter('attribute'):
        List.append(elements.get('value')) 
            
def testAttributeElements_ValueAttributes(expected, output):

    outputFilePath = output + "/placingToRendering.xml"
    expectedFilePath = expected
    expectedValueAttributesList = []
    outputValueAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctValue = "None" 
    errorValue = 0
    
    try:    
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
    
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()
    
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")    

    valueAttributesWalk(expectedRootTag, expectedValueAttributesList)
    
    valueAttributesWalk(outputRootTag, outputValueAttributesList)   
    
    expectedListLength = len(expectedValueAttributesList)
    outputListLength = len(outputValueAttributesList)
    passCounter = expectedListLength
    
    try:
        if (expectedListLength == 0 and outputListLength != 0):
            passCounter = -1
            errorValue = outputValueAttributesList[0]

        for i in range(expectedListLength):
            if(expectedValueAttributesList[i] != outputValueAttributesList[i]):
                correctValue = expectedValueAttributesList[i]
                errorValue = outputValueAttributesList[i]
                passCounter = expectedListLength - 1
                break

        assert expectedListLength == passCounter, f"test_AttributeElement_ValueAttributesValues: The value of the attribute element value attribute is '{errorValue}' in the output file, but it should be '{correctValue}' based on the expected file."
           
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
        
               

    
    
    
