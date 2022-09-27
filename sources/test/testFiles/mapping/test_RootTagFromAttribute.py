import pytest
import xml.etree.ElementTree as ET

jar = 'mapping'

def fromAttribute(RootTag, List):
    List.append(RootTag.get('from'))  
            
def testRootTagFromAttribute(expected, output):

    outputFilePath = output + "/mappingToPlacing.xml"
    expectedFilePath = expected
    expectedFromAttributesList = []
    outputFromAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctFrom = 0 
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
    passCounter = expectedListLength
    
    try:
        for i in range(expectedListLength):
            if(expectedFromAttributesList[i] != outputFromAttributesList[i]):
                correctFrom = expectedFromAttributesList[i]
                errorFrom = outputFromAttributesList[i]
                passCounter = expectedListLength - 1
                break

    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    try:
        assert expectedListLength == passCounter
    except AssertionError as exception:
        pytest.fail("Mismatched from atribute in root tag. From attribute is '" + str(errorFrom) + "' but the correct should be '" + str(correctFrom) + "'.")    
    
    
    
    
    
