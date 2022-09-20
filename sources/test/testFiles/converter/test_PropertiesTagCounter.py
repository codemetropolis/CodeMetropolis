import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def testPropertiesTagCounter(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    
    expectedPropertiesTagCounter = 0
    outputPropertiesTagCounter = 0

    try:
        expectedFile = ET.parse(outputFilePath)
        outputFile = ET.parse(expectedFilePath)
        
        expectedRoot = expectedFile.getroot()
        outputRoot = outputFile.getroot()
        
        for elements in outputRoot.iter('properties'):
            expectedPropertiesTagCounter = expectedPropertiesTagCounter + 1
        for elements in expectedRoot.iter('properties'):
            outputPropertiesTagCounter = outputPropertiesTagCounter + 1
        
        assert expectedPropertiesTagCounter == outputPropertiesTagCounter, "The number of 'properties' tags are not same!"
    except ET.ParseError as exception:
        pytest.fail("Missing or mystyped tag or tags!")
    
    
    
    
    
    
