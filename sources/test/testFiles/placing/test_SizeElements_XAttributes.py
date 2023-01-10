import pytest
import xml.etree.ElementTree as ET

jar = 'placing'

def xAttributesWalk(RootTag, List):
    for elements in RootTag.iter('size'):
        List.append(elements.get('x')) 
            
def testSizeElements_XAttributes(expected, output):

    outputFilePath = output + "/placingToRendering.xml"
    expectedFilePath = expected
    expectedXAttributesList = []
    outputXAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctX = "None" 
    errorX = 0
    
    try:    
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
    
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()
    
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")    

    xAttributesWalk(expectedRootTag, expectedXAttributesList)
    
    xAttributesWalk(outputRootTag, outputXAttributesList)   
    
    expectedListLength = len(expectedXAttributesList)
    outputListLength = len(outputXAttributesList)
    passCounter = expectedListLength
    
    try:
        if (expectedListLength == 0 and outputListLength != 0):
            passCounter = -1
            errorX = outputXAttributesList[0]

        for i in range(expectedListLength):
            if(expectedXAttributesList[i] != outputXAttributesList[i]):
                correctX = expectedXAttributesList[i]
                errorX = outputXAttributesList[i]
                passCounter = expectedListLength - 1
                break
        assert expectedListLength == passCounter, f"test_SizeElements_XAttributes: The value of the size element x attribute is '{errorX}' in the output file, but it should be '{correctX}' based on the expected file."
                
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    

    
    
    
    
    
