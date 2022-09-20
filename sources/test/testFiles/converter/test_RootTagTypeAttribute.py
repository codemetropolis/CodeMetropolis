import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def testRootTagTypeAttribute(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    
    expectedFile = ET.parse(outputFilePath)
    outputFile = ET.parse(expectedFilePath)

    expectedName = expectedFile.getroot().get('type')
    outputName = outputFile.getroot().get('type')
    
    assert expectedName == outputName, "The 'type' attribute of the root tag is not same!"
    
    
    
    
    
