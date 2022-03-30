import pytest
from xml.dom import minidom

jar = 'mapping'
input = 'IO/inputs/'
expected = 'IO/expected/mappingToPlacing.xml'
output = 'IO/output/mappingToPlacing.xml'

def testSizeXCoordinateIsEqual():
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(output)
    sizeXExpected = excpected_file.getElementsByTagName('size')
    sizeXOutput = output_file.getElementsByTagName('size')
    
    
    length = len(sizeXExpected)
    passed = 0
    
    for i in range(length):
        if(sizeXExpected[i].attributes['x'].value == sizeXOutput[i].attributes['x'].value):
            passed = passed + 1
    
    assert passed == length, "Not every size 'x' coordinate value is same"
    
