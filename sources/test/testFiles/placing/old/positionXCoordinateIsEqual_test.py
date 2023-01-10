import pytest
from xml.dom import minidom

jar = 'placing'

def testPositionXCoordinateIsEqual(expected, output):
    outputFullPath = output + "/placingToRendering.xml"
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(outputFullPath)
    positionXExpected = excpected_file.getElementsByTagName('position')
    positionXOutput = output_file.getElementsByTagName('position')
    
    length = len(positionXExpected)
    passed = 0
    
    for i in range(length):
        if(positionXExpected[i].attributes['x'].value == positionXOutput[i].attributes['x'].value):
            passed = passed + 1
    
    assert passed == length, "Not every 'x' attribute value of position tag matches"
    
