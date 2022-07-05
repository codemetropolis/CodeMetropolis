import pytest
from xml.dom import minidom

jar = 'converter'

def testElementTypeMatch(expected, output):
    outputFullPath = output + "/converterToMapping.xml"
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(outputFullPath)
    elementTypeExpected = excpected_file.getElementsByTagName('element')
    elementTypeOutput = output_file.getElementsByTagName('element')
    
    
    length = len(elementTypeExpected)
    passed = 0
    
    for i in range(length):
        if(elementTypeExpected[i].attributes['type'].value == elementTypeOutput[i].attributes['type'].value):
            passed = passed + 1
    
    assert passed == length, "elementTypeMatch_test: Not all 'type' attribute values ​​match with the expected 'type' attribute values"
    
