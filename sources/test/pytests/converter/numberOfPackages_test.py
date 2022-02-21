import pytest
from xml.dom import minidom

jar = 'converter'
input = 'IO/inputs/'
expected = 'IO/expected/converterToMapping.xml'
output = 'IO/output/converterToMapping.xml'

def testNumberOfPackages():
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(output)
    elementExpected = excpected_file.getElementsByTagName('element')
    elementOutput = output_file.getElementsByTagName('element')
        
    numberOfPackagesExcepted = 0
    numberOfPackagesOutput = 0
        
    for elem in elementExpected:
        if(elem.attributes['type'].value == "package"):
            numberOfPackagesExcepted = numberOfPackagesExcepted + 1
    for elems in elementOutput:
        if(elems.attributes['type'].value == "package"):
            numberOfPackagesOutput = numberOfPackagesOutput + 1
                        
             
    assert numberOfPackagesExcepted == numberOfPackagesOutput, 'The numbers of package types of elements are not the same'
        
