import pytest
from xml.dom import minidom

jar = 'mapping'

def testSizeZCoordinateIsEqual(expected, output):
    outputFullPath = output + "/mappingToPlacing.xml"
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(outputFullPath)
    sizeZExpected = excpected_file.getElementsByTagName('size')
    sizeZOutput = output_file.getElementsByTagName('size')
    
    
    length = len(sizeZExpected)
    passed = 0
    
    for i in range(length):
        if(sizeZExpected[i].attributes['z'].value == sizeZOutput[i].attributes['z'].value):
            passed = passed + 1
    
    assert passed == length, "Not every size 'z' coordinate value is same"
    
