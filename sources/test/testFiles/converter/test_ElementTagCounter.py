import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def testElementTagCounter(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    
    expectedElementTagList = []
    outputElementTagList = []

    try:
        expectedFile = ET.parse(outputFilePath)
        outputFile = ET.parse(expectedFilePath)
        
        expectedRoot = expectedFile.getroot()
        outputRoot = outputFile.getroot()
        
        for elements in outputRoot.iter('element'):
            outputElementTagList.append(elements.get('name'))
        for elements in expectedRoot.iter('element'):
            expectedElementTagList.append(elements.get('name'))   
            
        assert expectedElementTagList == outputElementTagList, "The number of 'element' tags are not same!"
    except ET.ParseError as exception:
        pytest.fail("Missing or mystyped tag or tags!")
    
    
    
    
    
    
