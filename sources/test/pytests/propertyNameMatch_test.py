import pytest
from xml.dom import minidom

jar = "converter"
input = "converter_IO\inputs\input.graph"
expected = "converter_IO\expected\converterToMapping.xml"
output = "converter_IO\output\converterToMapping.xml"

def testPropertyNameMatch():
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(output)
    propertyNameExpected = excpected_file.getElementsByTagName('property')
    propertyNameOutput = output_file.getElementsByTagName('property')
    
    
    length = len(propertyNameExpected)
    passed = 0
    
    for i in range(length):
        if(propertyNameExpected[i].attributes['type'].value == propertyNameOutput[i].attributes['type'].value):
            passed = passed + 1
    
    assert passed == length, "Not every name attribute of property tag matches with excepted file attributes"
    
