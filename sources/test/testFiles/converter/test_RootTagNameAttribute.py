import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def testRootTagNameAttribute(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    
    expectedFile = ET.parse(outputFilePath)
    outputFile = ET.parse(expectedFilePath)

    expectedName = expectedFile.getroot().get('name')
    outputName = outputFile.getroot().get('name')
    
    assert expectedName == outputName, "The 'name' attribute of the root tag is not same!"
    
    
    
    
    
