import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def test_PropertyTagsTypeAttribute(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    
    expectedPropertyTypeAttr = []
    outputPropertyTypeAttr = []

    try:
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
        
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()     
        
        for propertys in expectedRootTag.iter('properties'):
            length = len(propertys)
            for i in range(length):
                expectedPropertyTypeAttr.append(propertys[i].get('type'))            

        for propertys in outputRootTag.iter('properties'):
            length = len(propertys)
            for i in range(length):
                outputPropertyTypeAttr.append(propertys[i].get('type')) 
                
        length = len(expectedPropertyTypeAttr)
        passCounter = length
        
        for i in range(length):
            if(expectedPropertyTypeAttr[i] != outputPropertyTypeAttr[i]):
                passCounter = length - 1
        
              
        assert length == passCounter, "One or more type attribute of the property tags are not same!"
        
    except ET.ParseError as exception:
        pytest.fail("Missing or mystyped tag or tags!")
    
    
    
    
