import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def testElementTagsTypeAttributes(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    
    expectedElementTagsTypeAttributesList = []
    outputElementTagsTypeAttributesList = []
    
    expectedFile = ET.parse(outputFilePath)
    outputFile = ET.parse(expectedFilePath)
    
    expectedRootTag = expectedFile.getroot()
    outputRootTag = outputFile.getroot()    

    for elements in expectedRootTag.iter('element'):
        expectedElementTagsTypeAttributesList.append(elements.get('type')) 
    
    for elements in outputRootTag.iter('element'):
        outputElementTagsTypeAttributesList.append(elements.get('type')) 
    
    assert expectedElementTagsTypeAttributesList == outputElementTagsTypeAttributesList, "One or more 'type' attribute of the element tags are not same!"
    
    
    
    
    
