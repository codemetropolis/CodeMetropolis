import pytest
from xml.dom import minidom

excpected_file = minidom.parse('mappingTest\\expected\\mappingToPlacing.xml')
output_file = minidom.parse('mappingTest\\output\\mappingToPlacing.xml')

def testSizeXCoordinateIsEqual():
    sizeXExpected = excpected_file.getElementsByTagName('size')
    sizeXOutput = output_file.getElementsByTagName('size')
    
    
    length = len(sizeXExpected)
    passed = 0
    
    for i in range(length):
        if(sizeXExpected[i].attributes['x'].value == sizeXOutput[i].attributes['x'].value):
            passed = passed + 1
    
    assert passed == length, "Not every size 'x' coordinate value is same"
    
