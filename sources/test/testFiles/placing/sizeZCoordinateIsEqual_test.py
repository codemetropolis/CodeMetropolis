import pytest
from xml.dom import minidom

jar = 'placing'

def testSizeZCoordinateIsEqual(expected, output):
    outputFullPath = output + "/placingToRendering.xml"
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(outputFullPath)
    sizeZExpected = excpected_file.getElementsByTagName('size')
    sizeZOutput = output_file.getElementsByTagName('size')
    
    
    length = len(sizeZExpected)
    passed = 0
    
    for i in range(length):
        if(sizeZExpected[i].attributes['z'].value == sizeZOutput[i].attributes['z'].value):
            passed = passed + 1
    
    assert passed == length, "Not every 'z' attribute value of size tag is same"
    
