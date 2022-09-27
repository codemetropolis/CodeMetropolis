import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def rootTagChildrenWalk(RootTag, List):
    for elements in RootTag:
        List.append(elements.tag)  

def testElementTagsChildren(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedRootTagChildrenList = []
    outputRootTagChildrenList = []
    expectedRootTag = ""
    outputRootTag = ""

    try:
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
        
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot() 
        
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")
        
    rootTagChildrenWalk(expectedRootTag, expectedRootTagChildrenList)          

    rootTagChildrenWalk(outputRootTag, outputRootTagChildrenList)
    
    expectedListLength = len(expectedRootTagChildrenList)
    passCounter = expectedListLength
     
    try:
        for i in range(expectedListLength):
            if(expectedRootTagChildrenList[i] != outputRootTagChildrenList[i]):
                passCounter = expectedListLength - 1
                break

    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    try:
        assert expectedListLength == passCounter
    except AssertionError as exception:
        pytest.fail("Children tags of root not match with the expected.")     
    