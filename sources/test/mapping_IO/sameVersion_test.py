import pytest
from xml.dom import minidom

excpected_file = minidom.parse('mappingTest\\expected\\mappingToPlacing.xml')
output_file = minidom.parse('mappingTest\\output\\mappingToPlacing.xml')

def testSameVersion():
    buildablesExpected = excpected_file.getElementsByTagName('buildables')
    buildablesOutput = output_file.getElementsByTagName('buildables')
    
    assert buildablesExpected[0].attributes['version'].value == buildablesOutput[0].attributes['version'].value, 'Version number is not equal'
        
