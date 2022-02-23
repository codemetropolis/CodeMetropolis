import pytest
from xml.dom import minidom

jar = 'converter'
input = 'IO/inputs/'
expected = 'IO/expected/mappingToPlacing.xml'
output = 'IO/output/mappingToPlacing.xml'

def testSameVersion():
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(output)
    buildablesExpected = excpected_file.getElementsByTagName('buildables')
    buildablesOutput = output_file.getElementsByTagName('buildables')
    
    assert buildablesExpected[0].attributes['version'].value == buildablesOutput[0].attributes['version'].value, 'Version number is not equal'
        
