import pytest
from xml.dom import minidom

jar = 'placing'

def testBuildableNamesMatch(expected, output):
    outputFullPath = output + "/placingToRendering.xml"
    excpected_file = minidom.parse(expected)
    output_file = minidom.parse(outputFullPath)
    BuildableNameExpected = excpected_file.getElementsByTagName('buildable')
    BuildableNameZOutput = output_file.getElementsByTagName('buildable')
    
    
    length = len(BuildableNameExpected)
    passed = 0
    
    for i in range(length):
        if(BuildableNameExpected[i].attributes['name'].value == BuildableNameZOutput[i].attributes['name'].value):
            passed = passed + 1
    
    assert passed == length, "Not every name attribute matches with excepted file attributes"
    