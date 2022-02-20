import pytest
from xml.dom import minidom

excpected_file = minidom.parse('mappingTest\\expected\\mappingToPlacing.xml')
output_file = minidom.parse('mappingTest\\output\\mappingToPlacing.xml')

def testFirstBuildableTypeContainer():
    buildableExpected = excpected_file.getElementsByTagName('buildable')
    buildable = output_file.getElementsByTagName('buildable')
                                 
    assert buildableExpected[0].attributes['type'].value == buildable[0].attributes['type'].value, 'The first buildable type is not container'
        
