import pytest
from xml.dom import minidom

excpected_file = minidom.parse('mappingTest\\expected\\mappingToPlacing.xml')
output_file = minidom.parse('mappingTest\\output\\mappingToPlacing.xml')

def testBuildableTypesMatch():
    BuildableTypeExpected = excpected_file.getElementsByTagName('buildable')
    BuildableTypeZOutput = output_file.getElementsByTagName('buildable')
    
    
    length = len(BuildableTypeExpected)
    passed = 0
    
    for i in range(length):
        if(BuildableTypeExpected[i].attributes['type'].value == BuildableTypeZOutput[i].attributes['type'].value):
            passed = passed + 1
    
    assert passed == length, "Not every type attributes matches with excepted file attributes"
    
