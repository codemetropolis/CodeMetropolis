import pytest
from xml.dom import minidom

excpected_file = minidom.parse('placingTest\\expected\\placingToRendering.xml')
output_file = minidom.parse('placingTest\\output\\placingToRendering.xml')

def testSameVersion():
    buildablesExpected = excpected_file.getElementsByTagName('buildables')
    buildablesOutput = output_file.getElementsByTagName('buildables')
    
    assert buildablesExpected[0].attributes['version'].value == buildablesOutput[0].attributes['version'].value, 'The buildables tag version attributes is not equals'
        
