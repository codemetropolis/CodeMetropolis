import pytest
import xml.etree.ElementTree as ET

jar = 'mapping'

def versionAttribute(RootTag, List):
    List.append(RootTag.get('version'))  
            
def testRootTagVersionAttribute(expected, output):

    outputFilePath = output + "/mappingToPlacing.xml"
    expectedFilePath = expected
    expectedVersionAttributesList = []
    outputVersionAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctVersion = 0 
    errorVersion = 0
    
    try:    
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
    
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()
    
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")    

    versionAttribute(expectedRootTag, expectedVersionAttributesList)
    
    versionAttribute(outputRootTag, outputVersionAttributesList)   
    
    expectedListLength = len(expectedVersionAttributesList)
    passCounter = expectedListLength
    
    try:
        for i in range(expectedListLength):
            if(expectedVersionAttributesList[i] != outputVersionAttributesList[i]):
                correctVersion = expectedVersionAttributesList[i]
                errorVersion = outputVersionAttributesList[i]
                passCounter = expectedListLength - 1
                break

    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    try:
        assert expectedListLength == passCounter
    except AssertionError as exception:
        pytest.fail("Mismatched Version atribute in root tag. Version attribute is '" + str(errorVersion) + "' but the correct should be '" + str(correctVersion) + "'.")    
    
    
    
    
    
