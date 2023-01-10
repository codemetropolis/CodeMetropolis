import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

def propertiesWalk(RootTag, List):
    for propertys in RootTag.iter('property'):
        if(propertys.get('type') == 'int'):
            List.append(propertys.get('value')) 

def testPropertyElements_ValueAttributes_Int(expected, output):

    outputFilePath = output + "/converterToMapping.xml"
    expectedFilePath = expected
    expectedPropertyValueAttrList = []
    outputPropertyValueAttrList = []
    expectedRootTag = ""
    outputRootTag = ""
    expectedValueListLength = 0
    errorValue = 0
    correctValue = "None"

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
    outputValueListLength = len(outputPropertyValueAttrList)
    passCounter = expectedValueListLength

    try:
        if (expectedValueListLength == 0 and outputValueListLength != 0):
            passCounter = -1
            errorValue = outputPropertyValueAttrList[0]

        for i in range(expectedValueListLength):
            if(expectedPropertyValueAttrList[i] != outputPropertyValueAttrList[i]):
                correctValue = expectedPropertyValueAttrList[i]
                errorValue = outputPropertyValueAttrList[i]
                passCounter = expectedValueListLength - 1   
                assert expectedValueListLength == passCounter, f"test_PropertyElements_ValueAttributes_Int: The value of the property element value attribute is '{errorValue}' in the output file, but it should be '{correctValue}' based on the expected file."
                break

    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")
    

    
    
    
