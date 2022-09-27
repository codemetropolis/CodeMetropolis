import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def propertyWalk(RootTag, Counter):
    for elements in RootTag.iter('property'):
        Counter = Counter + 1
    return Counter

def testPropertyTagCounter(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedPropertyTagCounter = 0
    outputPropertyTagCounter = 0

    try:
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
        
        expectedRoot = expectedFile.getroot()
        outputRoot = outputFile.getroot()

    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")

    expectedPropertyTagCounter = propertyWalk(expectedRoot, expectedPropertyTagCounter)
    
    outputPropertyTagCounter = propertyWalk(outputRoot, outputPropertyTagCounter)
    
    try:
        assert expectedPropertyTagCounter == outputPropertyTagCounter

    except AssertionError as exception:
        pytest.fail("The number of property tags does not match. There are " + str(outputPropertyTagCounter) + " in the output, while the expected number is " + str(expectedPropertyTagCounter) + ".") 
    
    
    
    
    
