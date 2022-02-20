import pytest
from xml.dom import minidom

jar = 'converter'
input = 'IO/inputs/'
expected = 'IO\expected\converterToMapping.xml'
output = 'IO\output\converterToMapping.xml'

def testElementNameMatch():
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(output)
    elementNameExpected = excpected_file.getElementsByTagName('element')
    elementNameOutput = output_file.getElementsByTagName('element')
    
    
    length = len(elementNameExpected)
    passed = 0
    
    for i in range(length):
        if(elementNameExpected[i].attributes['name'].value == elementNameOutput[i].attributes['name'].value):
            passed = passed + 1
    
    assert passed == length, "Not every name attribute matches with excepted file attributes"
    
