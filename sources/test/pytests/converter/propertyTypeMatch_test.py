import pytest
from xml.dom import minidom

jar = 'converter'
input = 'inputs/'
expected = 'expected/converterToMapping.xml'
output = 'output/converterToMapping.xml'

def testPropertyTypeMatch():
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(output)
    propertyTypeExpected = excpected_file.getElementsByTagName('property')
    propertyTypeOutput = output_file.getElementsByTagName('property')
    
    
    length = len(propertyTypeExpected)
    passed = 0
    
    for i in range(length):
        if(propertyTypeExpected[i].attributes['type'].value == propertyTypeOutput[i].attributes['type'].value):
            passed = passed + 1
    
    assert passed == length, "Not every type attribute of property tag matches with excepted file attributes"
    
