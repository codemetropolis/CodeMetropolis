import pytest
from xml.dom import minidom

excpected_file = minidom.parse('placingTest\\expected\\placingToRendering.xml')
output_file = minidom.parse('placingTest\\output\\placingToRendering.xml')

def testPositionYCoordinateIsEqual():
    positionYExpected = excpected_file.getElementsByTagName('position')
    positionYOutput = output_file.getElementsByTagName('position')
    
    
    length = len(positionYExpected)
    passed = 0
    
    for i in range(length):
        if(positionYExpected[i].attributes['y'].value == positionYOutput[i].attributes['y'].value):
            passed = passed + 1
    
    assert passed == length, "Not every 'y' attribute value of position tag matches"
    
