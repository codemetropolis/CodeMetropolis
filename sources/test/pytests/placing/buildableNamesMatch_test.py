import pytest
from xml.dom import minidom

excpected_file = minidom.parse('placingTest\\expected\\placingToRendering.xml')
output_file = minidom.parse('placingTest\\output\\placingToRendering.xml')

def testBuildableNamesMatch():
    BuildableNameExpected = excpected_file.getElementsByTagName('buildable')
    BuildableNameZOutput = output_file.getElementsByTagName('buildable')
    
    
    length = len(BuildableNameExpected)
    passed = 0
    
    for i in range(length):
        if(BuildableNameExpected[i].attributes['name'].value == BuildableNameZOutput[i].attributes['name'].value):
            passed = passed + 1
    
    assert passed == length, "Not every name attribute matches with excepted file attributes"
    
