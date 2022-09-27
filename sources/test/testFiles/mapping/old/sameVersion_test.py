import pytest
from xml.dom import minidom

jar = 'mapping'

def testSameVersion(expected, output):
    outputFullPath = output + "/mappingToPlacing.xml"
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(outputFullPath)
    buildablesExpected = excpected_file.getElementsByTagName('buildables')
    buildablesOutput = output_file.getElementsByTagName('buildables')
    
    assert buildablesExpected[0].attributes['version'].value == buildablesOutput[0].attributes['version'].value, 'Version number is not equal'
        
