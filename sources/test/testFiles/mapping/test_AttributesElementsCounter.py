import pytest
import xml.etree.ElementTree as ET

jar = 'mapping'

def childrenWalk(RootTag, Counter):
    for elements in RootTag.iter('attributes'):
        Counter = Counter + 1
    return Counter

def testAttributesElementsCounter(expected, output):

    outputFilePath = output + "/mappingToPlacing.xml"
    expectedFilePath = expected
    expectedAttributesElementCounter = 0
    outputAttributesElementCounter = 0

    try:
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
        
        expectedRoot = expectedFile.getroot()
        outputRoot = outputFile.getroot()
            
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")
        
    expectedAttributesElementCounter = childrenWalk(expectedRoot, expectedAttributesElementCounter)
    
    outputAttributesElementCounter = childrenWalk(outputRoot, outputAttributesElementCounter)

    assert expectedAttributesElementCounter == outputAttributesElementCounter, f"test_ChildrenElementsCounter: The number of children elements not match. There are {outputAttributesElementCounter} in the output, but it should be {expectedAttributesElementCounter} based on the expected file."

    
    
    
    
    
    
