import pytest
from xml.dom import minidom

excpected_file = minidom.parse('placingTest\\expected\\placingToRendering.xml')
output_file = minidom.parse('placingTest\\output\\placingToRendering.xml')

def testPositionZCoordinateIsEqual():
    positionZExpected = excpected_file.getElementsByTagName('position')
    positionZOutput = output_file.getElementsByTagName('position')
    
    
    length = len(positionZExpected)
    passed = 0
    
    for i in range(length):
        if(positionZExpected[i].attributes['z'].value == positionZOutput[i].attributes['z'].value):
            passed = passed + 1
    
    assert passed == length, "Not every 'z' attribute value of position tag is same"
    
