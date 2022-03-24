import pytest
from xml.dom import minidom

excpected_file = minidom.parse('converterTest\\expected\\converterToMapping.xml')
output_file = minidom.parse('converterTest\\output\\converterToMapping.xml')

def testNumberOfMethods():
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
        
