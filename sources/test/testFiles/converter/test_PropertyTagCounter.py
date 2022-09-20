import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def testPropertyTagCounter(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    
    expectedPropertyTagCounter = 0
    outputPropertyTagCounter = 0

    try:
        expectedFile = ET.parse(outputFilePath)
        outputFile = ET.parse(expectedFilePath)
        
        expectedRoot = expectedFile.getroot()
        outputRoot = outputFile.getroot()
        
        for elements in outputRoot.iter('property'):
            expectedPropertyTagCounter = expectedPropertyTagCounter + 1
        for elements in expectedRoot.iter('property'):
            outputPropertyTagCounter = outputPropertyTagCounter + 1
        
        assert expectedPropertyTagCounter == outputPropertyTagCounter, "The number of 'property' tags are not same!"
    except ET.ParseError as exception:
        pytest.fail("Missing or mystyped tag or tags!")
    
    
    
    
    
    
