import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def propertyWalk(RootTag, Counter):
    for elements in RootTag.iter('property'):
        Counter = Counter + 1
    return Counter

def testPropertyElementsCounter(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedPropertyTagCounter = 0
    outputPropertyTagCounter = 0

    try:
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
        
        expectedRoot = expectedFile.getroot()
        outputRoot = outputFile.getroot()

    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")

    expectedPropertyTagCounter = propertyWalk(expectedRoot, expectedPropertyTagCounter)
    
    outputPropertyTagCounter = propertyWalk(outputRoot, outputPropertyTagCounter)
    
    assert expectedPropertyTagCounter == outputPropertyTagCounter, f"test_PropertyElementsCounter: The number of property elements not match. There are '{outputPropertyTagCounter}' in the output, but it should be '{expectedPropertyTagCounter}' based on the expected file."
    
    
    
    
