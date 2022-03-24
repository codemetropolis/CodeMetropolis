import pytest
from xml.dom import minidom

jar = 'mapping'
input = 'IO/inputs/'
expected = 'IO/expected/mappingToPlacing.xml'
output = 'IO/output/mappingToPlacing.xml'

def testBuildableTypesMatch():
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(output)
    BuildableTypeExpected = excpected_file.getElementsByTagName('buildable')
    BuildableTypeZOutput = output_file.getElementsByTagName('buildable')
    
    
    length = len(BuildableTypeExpected)
    passed = 0
    
    for i in range(length):
        if(BuildableTypeExpected[i].attributes['type'].value == BuildableTypeZOutput[i].attributes['type'].value):
            passed = passed + 1
    
    assert passed == length, "Not every type attributes matches with excepted file attributes"
    
