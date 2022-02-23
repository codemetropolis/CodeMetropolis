import pytest
from xml.dom import minidom

jar = 'converter'
input = 'IO/inputs/'
expected = 'IO/expected/mappingToPlacing.xml'
output = 'IO/output/mappingToPlacing.xml'

def testNumberOfMethods():
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(output)
    buildableExpected = excpected_file.getElementsByTagName('buildable')
    buildable = output_file.getElementsByTagName('buildable')
        
    numberOfMethodsExpected = 0
    numberOfMethodsOutput = 0
        
    for elem in buildableExpected:
        if(elem.attributes['type'].value == "floor"):
            numberOfMethodsExpected = numberOfMethodsExpected+1
    for elems in buildable:
        if(elems.attributes['type'].value == "floor"):
            numberOfMethodsOutput = numberOfMethodsOutput+1
                        
             
    assert numberOfMethodsExpected == numberOfMethodsOutput, 'The numbers of methods is not equal'
        
