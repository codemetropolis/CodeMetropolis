import pytest
from xml.dom import minidom

jar = 'converter'

def testNumberOfPackages(expected, output):
    outputFullPath = output + "/converterToMapping.xml"
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(outputFullPath)
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
        
