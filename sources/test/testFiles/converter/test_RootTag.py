import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def testRootTag(expected, output):

    try:
        outputFilePath = output + "/converterToMapping.xml"
        expectedFilePath = expected
        
        expectedFile = ET.parse(outputFilePath)
        outputFile = ET.parse(expectedFilePath)

        expectedRootTag = expectedFile.getroot().tag
        outputRootTag = outputFile.getroot().tag
        
        assert expectedRootTag == outputRootTag
    except ET.ParseError as exception:
        pytest.fail("Missing or mystyped tag or tags!")
    
    
    
    
