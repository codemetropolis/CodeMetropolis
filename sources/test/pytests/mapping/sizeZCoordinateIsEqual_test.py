import pytest
from xml.dom import minidom

jar = 'mapping'
input = 'IO/inputs/'
expected = 'IO/expected/mappingToPlacing.xml'
output = 'IO/output/mappingToPlacing.xml'

def testSizeZCoordinateIsEqual():
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(output)
    sizeZExpected = excpected_file.getElementsByTagName('size')
    sizeZOutput = output_file.getElementsByTagName('size')
    
    
    length = len(sizeZExpected)
    passed = 0
    
    for i in range(length):
        if(sizeZExpected[i].attributes['z'].value == sizeZOutput[i].attributes['z'].value):
            passed = passed + 1
    
    assert passed == length, "Not every size 'z' coordinate value is same"
    
