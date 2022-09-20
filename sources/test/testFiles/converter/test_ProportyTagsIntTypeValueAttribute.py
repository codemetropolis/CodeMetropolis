import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def test_PropertyTagsIntTypeValueAttribute(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    
    expectedPropertyValueAttr = []
    outputPropertyValueAttr = []

    try:
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
        
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()     
        
        for propertys in expectedRootTag.iter('properties'):
            length = len(propertys)
            for i in range(length):
                if(propertys[i].get('type') == 'int'):
                    expectedPropertyValueAttr.append(propertys[i].get('value'))            

        for propertys in outputRootTag.iter('properties'):
            length = len(propertys)
            for i in range(length):
                if(propertys[i].get('type') == 'int'):
                    outputPropertyValueAttr.append(propertys[i].get('value')) 
                
        length = len(expectedPropertyValueAttr)
        passCounter = length
        
        for i in range(length):
            if(expectedPropertyValueAttr[i] != outputPropertyValueAttr[i]):
                passCounter = length - 1
                     
        assert length == passCounter, "One or more value attribute of the property tags are not same!"
        
    except ET.ParseError as exception:
        pytest.fail("Missing or mystyped tag or tags!")
    
    
    
    
