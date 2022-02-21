import pytest
from xml.dom import minidom

jar = 'converter'
input = 'IO/inputs/'
expected = 'IO/expected/converterToMapping.xml'
output = 'IO/output/converterToMapping.xml'

def testNumberOfClasses():
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(output)
    elementExpected = excpected_file.getElementsByTagName('element')
    elementOutput = output_file.getElementsByTagName('element')
        
    numberOfClassesExcepted = 0
    numberOfClassesOutput = 0
        
    for elem in elementExpected:
        if(elem.attributes['type'].value == "class"):
            numberOfClassesExcepted = numberOfClassesExcepted + 1
    for elems in elementOutput:
        if(elems.attributes['type'].value == "class"):
            numberOfClassesOutput = numberOfClassesOutput + 1
                        
             
    assert numberOfClassesExcepted == numberOfClassesOutput, 'The numbers of class types of elements are not the same'
        
