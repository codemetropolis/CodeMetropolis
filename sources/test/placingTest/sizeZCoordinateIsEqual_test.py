import pytest
from xml.dom import minidom

excpected_file = minidom.parse('placingTest\\expected\\placingToRendering.xml')
output_file = minidom.parse('placingTest\\output\\placingToRendering.xml')

def testSizeZCoordinateIsEqual():
    sizeZExpected = excpected_file.getElementsByTagName('size')
    sizeZOutput = output_file.getElementsByTagName('size')
    
    
    length = len(sizeZExpected)
    passed = 0
    
    for i in range(length):
        if(sizeZExpected[i].attributes['z'].value == sizeZOutput[i].attributes['z'].value):
            passed = passed + 1
    
    assert passed == length, "Not every 'z' attribute value of size tag is same"
    
