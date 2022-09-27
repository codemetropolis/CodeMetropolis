import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def childrenWalk(RootTag, Counter):
    for elements in RootTag.iter('children'):
        Counter = Counter + 1
    return Counter

def testChildrenTagCounter(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedChildrenTagCounter = 0
    outputChildrenTagCounter = 0

    try:
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
        
        expectedRoot = expectedFile.getroot()
        outputRoot = outputFile.getroot()
            
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")
        
    expectedChildrenTagCounter = childrenWalk(expectedRoot, expectedChildrenTagCounter)
    
    outputChildrenTagCounter = childrenWalk(outputRoot, outputChildrenTagCounter)
    
    try:
        assert expectedChildrenTagCounter == outputChildrenTagCounter
    except AssertionError as exception:
        pytest.fail("The number of children tags does not match. There are " + str(outputChildrenTagCounter) + " in the output, while the expected number is " + str(expectedChildrenTagCounter) + ".") 
    

    
    
    
    
    
    
