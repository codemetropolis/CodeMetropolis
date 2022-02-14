import pytest
from xml.dom import minidom

excpected_file = minidom.parse('mappingTest\\expected\\mappingToPlacing.xml')
output_file = minidom.parse('mappingTest\\output\\mappingToPlacing.xml')

def testSizeZCoordinateIsEqual():
    sizeZExpected = excpected_file.getElementsByTagName('size')
    sizeZOutput = output_file.getElementsByTagName('size')
    
    
    length = len(sizeZExpected)
    passed = 0
    
    for i in range(length):
        if(sizeZExpected[i].attributes['z'].value == sizeZOutput[i].attributes['z'].value):
            passed = passed + 1
    
    assert passed == length, "Not every size 'z' coordinate value is same"
    
