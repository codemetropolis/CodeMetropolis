import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def testElementTagCounter(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    
    expectedElementTagCounter = 0
    outputElementTagCounter = 0

    try:
        expectedFile = ET.parse(outputFilePath)
        outputFile = ET.parse(expectedFilePath)
        
        expectedRoot = expectedFile.getroot()
        outputRoot = outputFile.getroot()
        
        for elements in outputRoot.iter('element'):
            expectedElementTagCounter = expectedElementTagCounter + 1
        for elements in expectedRoot.iter('element'):
            outputElementTagCounter = outputElementTagCounter + 1
            
        assert outputElementTagCounter == outputElementTagCounter, "The number of 'element' tags are not same!"
    except ET.ParseError as exception:
        pytest.fail("Missing or mystyped tag or tags!")
    
    
    
    
    
    
