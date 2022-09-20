import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def test_PropertyTagsFloatValueAttributes(expected, output):

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
                if(propertys[i].get('type') == 'float'):
                    expectedPropertyValueAttr.append(round(float(propertys[i].get('value')),3))            

        for propertys in outputRootTag.iter('properties'):
            length = len(propertys)
            for i in range(length):
                if(propertys[i].get('type') == 'float'):
                    outputPropertyValueAttr.append(round(float(propertys[i].get('value')),3)) 
                
        length = len(expectedPropertyValueAttr)
        passCounter = length
        
        for i in range(length):
            if(expectedPropertyValueAttr[i] != outputPropertyValueAttr[i]):
                passCounter = length - 1
        
        print("\n"+str(expectedPropertyValueAttr))
        print("\n"+str(expectedPropertyValueAttr))
                     
        assert length == passCounter, "One or more value attribute of the property tags are not same!"
        
    except ET.ParseError as exception:
        pytest.fail("Missing or mystyped tag or tags!")
    
    
    
    