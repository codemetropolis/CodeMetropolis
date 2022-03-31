import pytest
from xml.dom import minidom

jar = 'mapping'

def testNumberOfMethods(expected, output):
    outputFullPath = output + "/mappingToPlacing.xml"
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(outputFullPath)
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
        
