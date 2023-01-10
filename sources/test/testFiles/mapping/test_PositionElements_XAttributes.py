import pytest
import xml.etree.ElementTree as ET

jar = 'mapping'

def xAttributesWalk(RootTag, List):
    for elements in RootTag.iter('position'):
        List.append(elements.get('x')) 
            
def testPositionElements_XAttributes(expected, output):

    outputFilePath = output + "/mappingToPlacing.xml"
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
        assert expectedListLength == passCounter, f"test_PositionElements_XAttributes: The value of the position element x attribute is '{errorX}' in the output file, but it should be '{correctX}' based on the expected file."
                    
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    
    
    
    
    
    
