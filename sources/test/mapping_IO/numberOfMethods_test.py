import pytest
from xml.dom import minidom

excpected_file = minidom.parse('mappingTest\\expected\\mappingToPlacing.xml')
output_file = minidom.parse('mappingTest\\output\\mappingToPlacing.xml')

def testNumberOfMethods():
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
        
