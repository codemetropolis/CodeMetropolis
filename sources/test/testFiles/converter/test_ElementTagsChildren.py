import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def test_ElementTagsChildren(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    
    expectedElementChildren = []
    outputElementChildren = []

    try:
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
        
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()     
        
        for elements in expectedRootTag.iter('element'):
            length = len(elements)
            for i in range(length):
                expectedElementChildren.append(elements[i].tag)            

        for elements in outputRootTag.iter('element'):
            length = len(elements)
            for i in range(length):
                outputElementChildren.append(elements[i].tag) 
                
        length = len(expectedElementChildren)
        passCounter = length
        
        for i in range(length):
            if(expectedElementChildren[i] != outputElementChildren[i]):
                passCounter = length - 1
        
              
        assert length == passCounter, "One or more children of the Element tags are not same!"
        
    except ET.ParseError as exception:
        pytest.fail("Missing or mystyped tag or tags!")
    
    
    
    
