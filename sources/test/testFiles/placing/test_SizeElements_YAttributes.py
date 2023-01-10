import pytest
import xml.etree.ElementTree as ET

jar = 'placing'

def yAttributesWalk(RootTag, List):
    for elements in RootTag.iter('size'):
        List.append(elements.get('y')) 
            
def testSizeElements_YAttributes(expected, output):

    outputFilePath = output + "/placingToRendering.xml"
    expectedFilePath = expected
    expectedYAttributesList = []
    outputYAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctY = "None" 
    errorY = 0
    
    try:    
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
    
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()
    
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")    

    yAttributesWalk(expectedRootTag, expectedYAttributesList)
    
    yAttributesWalk(outputRootTag, outputYAttributesList)   
    
    expectedListLength = len(expectedYAttributesList)
    outputListLength = len(outputYAttributesList)
    passCounter = expectedListLength
    
    try:
        if (expectedListLength == 0 and outputListLength != 0):
            passCounter = -1
            errorY = outputYAttributesList[0]

        for i in range(expectedListLength):
            if(expectedYAttributesList[i] != outputYAttributesList[i]):
                correctY = expectedYAttributesList[i]
                errorY = outputYAttributesList[i]
                passCounter = expectedListLength - 1
                break
        assert expectedListLength == passCounter, f"test_SizeElements_YAttributes: The value of the size element y attribute is '{errorY}' in the output file, but it should be '{correctY}' based on the expected file."
                
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    

    
    
    
