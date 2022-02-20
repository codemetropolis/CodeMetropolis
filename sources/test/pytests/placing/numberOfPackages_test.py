import pytest
from xml.dom import minidom

excpected_file = minidom.parse('placingTest\\expected\\placingToRendering.xml')
output_file = minidom.parse('placingTest\\output\\placingToRendering.xml')

def testNumberOfPackages():
    buildableExpected = excpected_file.getElementsByTagName('buildable')
    buildable = output_file.getElementsByTagName('buildable')
        
    numberOfPackagesExpected = 0
    numberOfPackagesOutput = 0
        
    for elem in buildableExpected:
        if(elem.attributes['type'].value == "ground"):
            numberOfPackagesExpected = numberOfPackagesExpected + 1
    for elems in buildable:
        if(elems.attributes['type'].value == "ground"):
            numberOfPackagesOutput = numberOfPackagesOutput + 1
                        
             
    assert numberOfPackagesExpected == numberOfPackagesOutput, 'The numbers of packages is not equal'
        
