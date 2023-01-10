import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def propertiesWalk(RootTag, Counter):
    for elements in RootTag.iter('properties'):
        Counter = Counter + 1
    return Counter

def testPropertiesElementsCounter(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedPropertiesTagCounter = 0
    outputPropertiesTagCounter = 0

    try:
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
        
        expectedRoot = expectedFile.getroot()
        outputRoot = outputFile.getroot()

    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")

    expectedPropertiesTagCounter = propertiesWalk(expectedRoot, expectedPropertiesTagCounter)
    
    outputPropertiesTagCounter = propertiesWalk(outputRoot, outputPropertiesTagCounter)
    
    assert expectedPropertiesTagCounter == outputPropertiesTagCounter, f"test_PropertiesElementsCounter: The number of properties elements not match. There are '{outputPropertiesTagCounter}' in the output, but it should be {expectedPropertiesTagCounter} based on the expected file."
    
    
    
    
    
    
