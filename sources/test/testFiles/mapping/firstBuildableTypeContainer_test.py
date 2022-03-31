import pytest
from xml.dom import minidom

jar = 'mapping'

def testFirstBuildableTypeContainer(expected, output):
    outputFullPath = output + "/mappingToPlacing.xml"
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(outputFullPath)
    buildableExpected = excpected_file.getElementsByTagName('buildable')
    buildable = output_file.getElementsByTagName('buildable')
                                 
    assert buildableExpected[0].attributes['type'].value == buildable[0].attributes['type'].value, 'The first buildable type is not container'
        
