import pytest
import xml.etree.ElementTree as ET

jar = 'mapping'

def zAttributesWalk(RootTag, List):
    for elements in RootTag.iter('position'):
        List.append(elements.get('z')) 
            
def testPositionElements_ZAttributes(expected, output):

    outputFilePath = output + "/mappingToPlacing.xml"
    expectedFilePath = expected
    expectedZAttributesList = []
    outputZAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctZ = "None" 
    errorZ = 0
    
    try:    
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
    
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()
    
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")    

    zAttributesWalk(expectedRootTag, expectedZAttributesList)
    
    zAttributesWalk(outputRootTag, outputZAttributesList)   
    
    expectedListLength = len(expectedZAttributesList)
    outputListLength = len(outputZAttributesList)
    passCounter = expectedListLength
    
    try:
        if (expectedListLength == 0 and outputListLength != 0):
            passCounter = -1
            errorZ = outputZAttributesList[0]

        for i in range(expectedListLength):
            if(expectedZAttributesList[i] != outputZAttributesList[i]):
                correctZ = expectedZAttributesList[i]
                errorZ = outputZAttributesList[i]
                passCounter = expectedListLength - 1
                break
        assert expectedListLength == passCounter, f"test_PositionElements_ZAttributes: The value of the position element Z attribute is '{errorZ}' in the output file, but it should be '{correctZ}' based on the expected file."
                
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    
    
    
    
    
