import pytest
from xml.dom import minidom

jar = 'converter'
input = 'IO/inputs/'
expected = 'IO/expected/mappingToPlacing.xml'
output = 'IO/output/mappingToPlacing.xml'

output_file = minidom.parse('output')

def testPositionCoordinatesZero():
    positionOutput = output_file.getElementsByTagName('position')
    length = len(positionOutput)
    passed = 0
    
    for i in range(length):
        if(positionOutput[i].attributes['x'].value == "0" and positionOutput[i].attributes['y'].value == "0" and positionOutput[i].attributes['z'].value == "0"):
            passed = passed + 1
    
    assert passed == length, "Not every position value is zero"
    
