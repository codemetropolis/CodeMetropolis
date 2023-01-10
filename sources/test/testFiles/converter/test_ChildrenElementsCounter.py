import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def childrenWalk(RootTag, Counter):
    for elements in RootTag.iter('children'):
        Counter = Counter + 1
    return Counter

def testChildrenElementsCounter(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedChildrenTagCounter = 0
    outputChildrenTagCounter = 0

    try:
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
        
        expectedRoot = expectedFile.getroot()
        outputRoot = outputFile.getroot()
            
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")
        
    expectedChildrenTagCounter = childrenWalk(expectedRoot, expectedChildrenTagCounter)
    
    outputChildrenTagCounter = childrenWalk(outputRoot, outputChildrenTagCounter)

    assert expectedChildrenTagCounter == outputChildrenTagCounter, f"test_ChildrenElementsCounter: The number of children elements not match. There are {outputChildrenTagCounter} in the output, but it should be {expectedChildrenTagCounter} based on the expected file."

    
    
    
    
    
    
