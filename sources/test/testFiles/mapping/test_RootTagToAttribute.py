import pytest
import xml.etree.ElementTree as ET

jar = 'mapping'

def toAttribute(RootTag, List):
    List.append(RootTag.get('to'))  
            
def testRootTagToAttribute(expected, output):

    outputFilePath = output + "/mappingToPlacing.xml"
    expectedFilePath = expected
    expectedToAttributesList = []
    outputToAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctTo = 0 
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
    passCounter = expectedListLength
    
    try:
        for i in range(expectedListLength):
            if(expectedToAttributesList[i] != outputToAttributesList[i]):
                correctTo = expectedToAttributesList[i]
                errorTo = outputToAttributesList[i]
                passCounter = expectedListLength - 1
                break

    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    try:
        assert expectedListLength == passCounter
    except AssertionError as exception:
        pytest.fail("Mismatched to atribute in root tag. To attribute is '" + str(errorTo) + "' but the correct should be '" + str(correctTo) + "'.")    
    
    
    
    
    
