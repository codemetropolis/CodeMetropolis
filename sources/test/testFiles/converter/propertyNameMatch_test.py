import pytest
from xml.dom import minidom

jar = 'converter'

def testPropertyNameMatch(expected, output):
    outputFullPath = output + "/converterToMapping.xml"
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(outputFullPath)
    propertyNameExpected = excpected_file.getElementsByTagName('property')
    propertyNameOutput = output_file.getElementsByTagName('property')
    
    
    length = len(propertyNameExpected)
    passed = 0
    
    for i in range(length):
        if(propertyNameExpected[i].attributes['type'].value == propertyNameOutput[i].attributes['type'].value):
            passed = passed + 1
    
    assert passed == length, "propertyNameMatch_test: The number of attributes where 'type = package' does not match with the expected value" "Not every name attribute of property tag matches with excepted file attributes"
    
