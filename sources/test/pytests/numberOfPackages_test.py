import pytest
from xml.dom import minidom

jar = "converter"
input = "converter_IO\inputs\input.graph"
expected = "converter_IO\expected\converterToMapping.xml"
output = "converter_IO\output\converterToMapping.xml"

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
        
