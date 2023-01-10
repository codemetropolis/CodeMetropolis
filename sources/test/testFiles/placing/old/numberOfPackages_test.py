import pytest
from xml.dom import minidom

jar = 'placing'

def testNumberOfPackages(expected, output):

    outputFullPath = output + "/placingToRendering.xml"
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(outputFullPath)
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
        
