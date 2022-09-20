import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def test_PropertyTagsNameAttribute(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    
    expectedPropertyNameAttr = []
    outputPropertyNameAttr = []

    try:
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
        
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()     
        
        for propertys in expectedRootTag.iter('properties'):
            length = len(propertys)
            for i in range(length):
                expectedPropertyNameAttr.append(propertys[i].get('name'))            

        for propertys in outputRootTag.iter('properties'):
            length = len(propertys)
            for i in range(length):
                outputPropertyNameAttr.append(propertys[i].get('name')) 
                
        length = len(expectedPropertyNameAttr)
        passCounter = length
        
        for i in range(length):
            if(expectedPropertyNameAttr[i] != outputPropertyNameAttr[i]):
                passCounter = length - 1
        
              
        assert length == passCounter, "One or more name attribute of the property tags are not same!"
        
    except ET.ParseError as exception:
        pytest.fail("Missing or mystyped tag or tags!")
    
    
    
    
