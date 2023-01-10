import pytest
import xml.etree.ElementTree as ET

jar = 'mapping'

def toAttribute(RootTag, List):
    List.append(RootTag.get('to'))  
            
def testRootElements_ToAttributes(expected, output):

    outputFilePath = output + "/mappingToPlacing.xml"
    expectedFilePath = expected
    expectedToAttributesList = []
    outputToAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctTo = "None" 
    errorTo = 0
    
    try:    
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
    
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()
    
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")    

    toAttribute(expectedRootTag, expectedToAttributesList)
    
    toAttribute(outputRootTag, outputToAttributesList)   
    
    expectedListLength = len(expectedToAttributesList)
    outputListLength = len(outputToAttributesList)
    passCounter = expectedListLength
    
    try:
        if (expectedListLength == 0 and outputListLength != 0):
            passCounter = -1
            errorTo = outputToAttributesList[0]

        for i in range(expectedListLength):
            if(expectedToAttributesList[i] != outputToAttributesList[i]):
                correctTo = expectedToAttributesList[i]
                errorTo = outputToAttributesList[i]
                passCounter = expectedListLength - 1
                break

            assert expectedListLength == passCounter, f"test_BuildablesElement_ToAttributes: The value of the buildables element to attribute is '{errorTo}' in the output file, but it should be '{correctTo}' based on the expected file."
                
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    
    
    
    
    
