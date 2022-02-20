import pytest
from xml.dom import minidom

excpected_file = minidom.parse('mappingTest\\expected\\mappingToPlacing.xml')
output_file = minidom.parse('mappingTest\\output\\mappingToPlacing.xml')

def testSizeYCoordinateIsEqual():
    sizeYExpected = excpected_file.getElementsByTagName('size')
    sizeYOutput = output_file.getElementsByTagName('size')
    
    
    length = len(sizeYExpected)
    passed = 0
    
    for i in range(length):
        if(sizeYExpected[i].attributes['y'].value == sizeYOutput[i].attributes['y'].value):
            passed = passed + 1
    
    assert passed == length, "Not every size 'y' coordinate value is same"
    
