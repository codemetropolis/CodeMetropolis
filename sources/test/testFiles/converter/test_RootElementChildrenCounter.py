import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def rootTagChildrenWalk(RootTag, List):
    for elements in RootTag:
        List.append(elements.tag)  

def testRootElementChildren(expected, output):

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
                assert expectedListLength == passCounter, f"test_RootElementChildrenCounter: The number of root element children elements not match. There are {passCounter} in the output, but it should be {expectedListLength} based on the expected file."
                break

    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    
    