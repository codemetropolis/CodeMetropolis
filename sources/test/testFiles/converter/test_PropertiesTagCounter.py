import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def propertiesWalk(RootTag, Counter):
    for elements in RootTag.iter('properties'):
        Counter = Counter + 1
    return Counter

def testPropertiesTagCounter(expected, output):

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
    
    try:
        assert expectedPropertiesTagCounter == outputPropertiesTagCounter
        
    except AssertionError as exception:
        pytest.fail("The number of properties tags does not match. There are " + str(outputPropertiesTagCounter) + " in the output, while the expected number is " + str(expectedPropertiesTagCounter) + ".") 
    
    
    
    
    
    
