import pytest
from xml.dom import minidom

jar = 'placing'

def testSizeXCoordinateIsEqual(expected, output):
    outputFullPath = output + "/placingToRendering.xml"
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(outputFullPath)
    sizeXExpected = excpected_file.getElementsByTagName('size')
    sizeXOutput = output_file.getElementsByTagName('size')
    
    
    length = len(sizeXExpected)
    passed = 0
    
    for i in range(length):
        if(sizeXExpected[i].attributes['x'].value == sizeXOutput[i].attributes['x'].value):
            passed = passed + 1
    
    assert passed == length, "Not every 'x' attribute value of size tag is same"
    
