import pytest
from xml.dom import minidom

excpected_file = minidom.parse('converterTest\\expected\\converterToMapping.xml')
output_file = minidom.parse('converterTest\\output\\converterToMapping.xml')

def testNumberOfPackages():
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
        
