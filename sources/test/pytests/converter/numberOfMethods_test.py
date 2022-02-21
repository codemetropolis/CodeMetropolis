import pytest
from xml.dom import minidom

jar = 'converter'
input = 'IO/inputs/'
expected = 'IO/expected/converterToMapping.xml'
output = 'IO/output/converterToMapping.xml'

def testNumberOfMethods():
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(output)
    elementExpected = excpected_file.getElementsByTagName('element')
    elementOutput = output_file.getElementsByTagName('element')
        
    numberOfMethodsExcepted = 0
    numberOfMethodsOutput = 0
        
    for elem in elementExpected:
        if(elem.attributes['type'].value == "method"):
            numberOfMethodsExcepted = numberOfMethodsExcepted + 1
    for elems in elementOutput:
        if(elems.attributes['type'].value == "method"):
            numberOfMethodsOutput = numberOfMethodsOutput + 1
                        
             
    assert numberOfMethodsExcepted == numberOfMethodsOutput, 'The numbers of methods types of elements are not the same'
        
