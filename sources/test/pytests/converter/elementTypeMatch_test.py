import pytest
from xml.dom import minidom

jar = "converter"
input = "converter_IO\inputs\input.graph"
expected = "converter_IO\expected\converterToMapping.xml"
output = "converter_IO\output\converterToMapping.xml"

def testElementTypeMatch():
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(output)
    elementTypeExpected = excpected_file.getElementsByTagName('element')
    elementTypeOutput = output_file.getElementsByTagName('element')
    
    
    length = len(elementTypeExpected)
    passed = 0
    
    for i in range(length):
        if(elementTypeExpected[i].attributes['type'].value == elementTypeOutput[i].attributes['type'].value):
            passed = passed + 1
    
    assert passed == length, "Not every type attribute matches with the excepted file attributes"
    
