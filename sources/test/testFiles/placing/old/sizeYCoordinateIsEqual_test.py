import pytest
from xml.dom import minidom

jar = 'placing'

def testSizeYCoordinateIsEqual(expected, output):

    outputFullPath = output + "/placingToRendering.xml"
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(outputFullPath)
    sizeYExpected = excpected_file.getElementsByTagName('size')
    sizeYOutput = output_file.getElementsByTagName('size')    
    
    length = len(sizeYExpected)
    passed = 0
    
    for i in range(length):
        if(sizeYExpected[i].attributes['y'].value == sizeYOutput[i].attributes['y'].value):
            passed = passed + 1
    
    assert passed == length, "Not every 'y' attribute value of size tag is same"
    
