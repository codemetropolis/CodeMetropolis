import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def elementWalk(RootTag, Counter):
    for elements in RootTag.iter('element'):
        Counter = Counter + 1
    return Counter

def testElementTagCounter(expected, output):

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
    
    try:
        assert expectedElementTagCounter == outputElementTagCounter
    except AssertionError as exception:
        pytest.fail("The number of element tags does not match. There are " + str(outputElementTagCounter) + " in the output, while the expected number is " + str(expectedElementTagCounter) + ".") 
    
    
    
    
    
    
