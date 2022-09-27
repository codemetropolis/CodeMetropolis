import pytest
import xml.etree.ElementTree as ET

jar = 'mapping'

def valueAttributesWalk(RootTag, List):
    for elements in RootTag.iter('attribute'):
        List.append(elements.get('value')) 
            
def testAttributeTagsValueAttributes(expected, output):

    outputFilePath = output + "/mappingToPlacing.xml"
    expectedFilePath = expected
    expectedValueAttributesList = []
    outputValueAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctValue = 0 
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
    passCounter = expectedListLength
    
    try:
        for i in range(expectedListLength):
            if(expectedValueAttributesList[i] != outputValueAttributesList[i]):
                correctValue = expectedValueAttributesList[i]
                errorValue = outputValueAttributesList[i]
                passCounter = expectedListLength - 1
                break
                
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
        
    try:
        assert expectedListLength == passCounter
    except AssertionError as exception:
        pytest.fail("Mismatched value atributes. One value is '" + str(errorValue) + "' but the correct should be '" + str(correctValue) + "'.")    
    
    
    
    
    
