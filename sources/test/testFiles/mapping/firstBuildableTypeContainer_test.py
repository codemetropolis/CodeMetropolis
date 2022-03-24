import pytest
from xml.dom import minidom

jar = 'mapping'
input = 'IO/inputs/'
expected = 'IO/expected/mappingToPlacing.xml'
output = 'IO/output/mappingToPlacing.xml'

def testFirstBuildableTypeContainer():
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(output)
    buildableExpected = excpected_file.getElementsByTagName('buildable')
    buildable = output_file.getElementsByTagName('buildable')
                                 
    assert buildableExpected[0].attributes['type'].value == buildable[0].attributes['type'].value, 'The first buildable type is not container'
        
