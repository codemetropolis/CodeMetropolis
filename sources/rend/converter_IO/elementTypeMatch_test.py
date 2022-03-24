import pytest
from xml.dom import minidom

excpected_file = minidom.parse('converterTest\\expected\\converterToMapping.xml')
output_file = minidom.parse('converterTest\\output\\converterToMapping.xml')

def testElementTypeMatch():
    elementTypeExpected = excpected_file.getElementsByTagName('element')
    elementTypeOutput = output_file.getElementsByTagName('element')
    
    
    length = len(elementTypeExpected)
    passed = 0
    
    for i in range(length):
        if(elementTypeExpected[i].attributes['type'].value == elementTypeOutput[i].attributes['type'].value):
            passed = passed + 1
    
    assert passed == length, "Not every type attribute matches with the excepted file attributes"
    
