import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def testElementTagsNameAttributes(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    
    expectedElementTagsNameAttributesList = []
    outputElementTagsNameAttributesList = []
    
    expectedFile = ET.parse(outputFilePath)
    outputFile = ET.parse(expectedFilePath)
    
    expectedRootTag = expectedFile.getroot()
    outputRootTag = outputFile.getroot()    

    for elements in expectedRootTag.iter('element'):
        expectedElementTagsNameAttributesList.append(elements.get('name')) 
    
    for elements in outputRootTag.iter('element'):
        outputElementTagsNameAttributesList.append(elements.get('name')) 
    
    assert expectedElementTagsNameAttributesList == outputElementTagsNameAttributesList, "One or more 'name' attribute of the element tags are not same!"
    
    
    
    
    
