import pytest
import xml.etree.ElementTree as ET

jar = 'mapping'

def versionAttribute(RootTag, List):
    List.append(RootTag.get('version'))  
            
def testRootElements_VersionAttributes(expected, output):

    outputFilePath = output + "/mappingToPlacing.xml"
    expectedFilePath = expected
    expectedVersionAttributesList = []
    outputVersionAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctVersion = "None" 
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
    outputListLength = len(outputVersionAttributesList)
    passCounter = expectedListLength
    
    try:
        if (expectedListLength == 0 and outputListLength != 0):
            passCounter = -1
            errorVersion = outputVersionAttributesList[0]

        for i in range(expectedListLength):
            if(expectedVersionAttributesList[i] != outputVersionAttributesList[i]):
                correctVersion = expectedVersionAttributesList[i]
                errorVersion = outputVersionAttributesList[i]
                passCounter = expectedListLength - 1
                break

        assert expectedListLength == passCounter, f"test_BuildablesElement_VersionAttributes: The value of the buildables element version attribute is '{errorVersion}' in the output file, but it should be '{correctVersion}' based on the expected file."
                
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    
    
    
