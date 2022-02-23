import pytest
from xml.dom import minidom

jar = 'converter'
input = 'IO/inputs/'
expected = 'IO/expected/mappingToPlacing.xml'
output = 'IO/output/mappingToPlacing.xml'

def testBuildableNamesMatch():
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(output)
    BuildableNameExpected = excpected_file.getElementsByTagName('buildable')
    BuildableNameZOutput = output_file.getElementsByTagName('buildable')
    
    
    length = len(BuildableNameExpected)
    passed = 0
    
    for i in range(length):
        if(BuildableNameExpected[i].attributes['name'].value == BuildableNameZOutput[i].attributes['name'].value):
            passed = passed + 1
    
    assert passed == length, "Not every name attribute matches with excepted file attributes"
    
