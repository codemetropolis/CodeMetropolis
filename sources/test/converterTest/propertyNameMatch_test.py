import pytest
from xml.dom import minidom

excpected_file = minidom.parse('converterTest\\expected\\converterToMapping.xml')
output_file = minidom.parse('converterTest\\output\\converterToMapping.xml')

def testPropertyNameMatch():
    propertyNameExpected = excpected_file.getElementsByTagName('property')
    propertyNameOutput = output_file.getElementsByTagName('property')
    
    
    length = len(propertyNameExpected)
    passed = 0
    
    for i in range(length):
        if(propertyNameExpected[i].attributes['type'].value == propertyNameOutput[i].attributes['type'].value):
            passed = passed + 1
    
    assert passed == length, "Not every name attribute of property tag matches with excepted file attributes"
    
