import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def propertiesWalk(RootTag, List):
    for propertys in RootTag.iter('property'):
        if(propertys.get('type') == 'int'):
            List.append(propertys.get('value')) 

def testPropertyTagsIntValueAttributes(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedPropertyValueAttrList = []
    outputPropertyValueAttrList = []
    expectedRootTag = ""
    outputRootTag = ""
    expectedValueListLength = 0
    errorValue = 0
    correctValue = 0 

    try:
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
        
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()     
                                 
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")

    propertiesWalk(expectedRootTag, expectedPropertyValueAttrList)

    propertiesWalk(outputRootTag, outputPropertyValueAttrList)

    expectedValueListLength = len(expectedPropertyValueAttrList)
    passCounter = expectedValueListLength

    try:
        for i in range(expectedValueListLength):
            if(expectedPropertyValueAttrList[i] != outputPropertyValueAttrList[i]):
                correctValue = expectedPropertyValueAttrList[i]
                errorValue = outputPropertyValueAttrList[i]
                passCounter = expectedValueListLength - 1               
                break

    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")

    try:
        assert expectedValueListLength == passCounter
    except AssertionError as exception:
        pytest.fail("Mismatched int values. One value is " + str(errorValue) + " but the correct value should be " + str(correctValue) + ".")        
           
    
    
    
