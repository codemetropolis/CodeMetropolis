import pytest
from xml.dom import minidom

output_file = minidom.parse('mappingTest\\output\\mappingToPlacing.xml')

def testPositionCoordinatesZero():
    positionOutput = output_file.getElementsByTagName('position')
    length = len(positionOutput)
    passed = 0
    
    for i in range(length):
        if(positionOutput[i].attributes['x'].value == "0" and positionOutput[i].attributes['y'].value == "0" and positionOutput[i].attributes['z'].value == "0"):
            passed = passed + 1
    
    assert passed == length, "Not every position value is zero"
    
