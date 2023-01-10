import pytest
from xml.dom import minidom

jar = 'placing'


def testSameVersion(expected, output):
    outputFullPath = output + "/placingToRendering.xml"
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(outputFullPath)
    buildablesExpected = excpected_file.getElementsByTagName('buildables')
    buildablesOutput = output_file.getElementsByTagName('buildables')
    
    assert buildablesExpected[0].attributes['version'].value == buildablesOutput[0].attributes['version'].value, 'The buildables tag version attributes is not equals'
        
