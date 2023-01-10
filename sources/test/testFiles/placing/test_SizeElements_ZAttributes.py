import pytest
import xml.etree.ElementTree as ET

jar = 'placing'

def zAttributesWalk(RootTag, List):
    for elements in RootTag.iter('size'):
        List.append(elements.get('z')) 
            
def testSizeElements_ZAttributes(expected, output):

    outputFilePath = output + "/placingToRendering.xml"
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
        assert expectedListLength == passCounter, f"test_SizeElements_ZAttributes: The value of the size element z attribute is '{errorZ}' in the output file, but it should be '{correctZ}' based on the expected file."
                
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    
    
    
