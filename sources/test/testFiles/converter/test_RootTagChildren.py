import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def test_RootTagChildren(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    
    expectedRootTagChildren = []
    outputRootTagChildren = []

    try:
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
        
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()     
        
        for elements in expectedRootTag.iter('element'):
            for i in range(2):
                expectedRootTagChildren.append(elements[i].tag)            

        for elements in outputRootTag.iter('element'):
            for i in range(2):
                outputRootTagChildren.append(elements[i].tag) 
                
        
              
        assert expectedRootTagChildren == outputRootTagChildren, "One or more children of the root Element tag are not same!"
        
    except ET.ParseError as exception:
        pytest.fail("Missing or mystyped tag or tags!")
    