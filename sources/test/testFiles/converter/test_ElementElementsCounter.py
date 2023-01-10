import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def elementWalk(RootTag, Counter):
    for elements in RootTag.iter('element'):
        Counter = Counter + 1
    return Counter

def testElementElementsCounter(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedElementTagCounter = 0
    outputElementTagCounter = 0

    try:
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
        
        expectedRoot = expectedFile.getroot()
        outputRoot = outputFile.getroot()

    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")

    expectedElementTagCounter = elementWalk(expectedRoot, expectedElementTagCounter)
    
    outputElementTagCounter = elementWalk(outputRoot, outputElementTagCounter)
    
    assert expectedElementTagCounter == outputElementTagCounter, f"test_ElementElementsCounter: The number of element elements not match. There are {outputElementTagCounter} in the output, but it should be {expectedElementTagCounter} based on the expected file."

    
    
    
    
