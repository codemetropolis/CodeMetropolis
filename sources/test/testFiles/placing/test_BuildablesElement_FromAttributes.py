import pytest
import xml.etree.ElementTree as ET

jar = 'placing'

def fromAttribute(RootTag, List):
    List.append(RootTag.get('from'))  
            
def testRootElements_FromAttributes(expected, output):

    outputFilePath = output + "/placingToRendering.xml"
    expectedFilePath = expected
    expectedFromAttributesList = []
    outputFromAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctFrom = "None"
    errorFrom = 0
    
    try:    
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
    
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()
    
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")    

    fromAttribute(expectedRootTag, expectedFromAttributesList)
    
    fromAttribute(outputRootTag, outputFromAttributesList)   
    
    expectedListLength = len(expectedFromAttributesList)
    outputListLength = len(outputFromAttributesList)
    passCounter = expectedListLength
    
    try:
        if (expectedListLength == 0 and outputListLength != 0):
            passCounter = -1
            errorFrom = outputFromAttributesList[0]

        for i in range(expectedListLength):
            if(expectedFromAttributesList[i] != outputFromAttributesList[i]):
                correctFrom = expectedFromAttributesList[i]
                errorFrom = outputFromAttributesList[i]
                passCounter = expectedListLength - 1
                break
        assert expectedListLength == passCounter, f"test_BuildablesElements_FromAttributes: The value of the buildables element from attribute is '{errorFrom}' in the output file, but it should be '{correctFrom}' based on the expected file."

    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    
    
    
    
