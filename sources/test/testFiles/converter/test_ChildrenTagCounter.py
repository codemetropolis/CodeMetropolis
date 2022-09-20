import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def testChildrenTagCounter(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    
    expectedChildrenTagCounter = 0
    outputChildrenTagCounter = 0

    try:
        expectedFile = ET.parse(outputFilePath)
        outputFile = ET.parse(expectedFilePath)
        
        expectedRoot = expectedFile.getroot()
        outputRoot = outputFile.getroot()
        
        for elements in outputRoot.iter('children'):
            expectedChildrenTagCounter = expectedChildrenTagCounter + 1
        for elements in expectedRoot.iter('children'):
            outputChildrenTagCounter = outputChildrenTagCounter + 1
        
        assert expectedChildrenTagCounter == outputChildrenTagCounter, "The number of 'children' tags are not same!"
    except ET.ParseError as exception:
        pytest.fail("Missing or mystyped tag or tags!")
    
    
    
    
    
    
