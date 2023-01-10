import pytest
import xml.etree.ElementTree as ET

jar = 'placing'

def nameAttributesWalk(RootTag, List):
    for elements in RootTag.iter('attribute'):
        List.append(elements.get('name')) 
            
def testAttributeElements_NameAttributes(expected, output):

    outputFilePath = output + "/placingToRendering.xml"
    expectedFilePath = expected
    expectedNameAttributesList = []
    outputNameAttributesList = []
    expectedRootTag = ""
    outputRootTag = ""
    correctName = "None" 
    errorName = 0
    
    try:    
        expectedFile = ET.parse(expectedFilePath)
        outputFile = ET.parse(outputFilePath)
    
        expectedRootTag = expectedFile.getroot()
        outputRootTag = outputFile.getroot()
    
    except ET.ParseError as exception:
        pytest.fail("Missing or mistyped tag or tags.")    

    nameAttributesWalk(expectedRootTag, expectedNameAttributesList)
    
    nameAttributesWalk(outputRootTag, outputNameAttributesList)   
    
    expectedListLength = len(expectedNameAttributesList)
    outputListLength = len(outputNameAttributesList)
    passCounter = expectedListLength
    
    try:
        if (expectedListLength == 0 and outputListLength != 0):
            passCounter = -1
            errorName = outputNameAttributesList[0]

        for i in range(expectedListLength):
            if(expectedNameAttributesList[i] != outputNameAttributesList[i]):
                correctName = expectedNameAttributesList[i]
                errorName = outputNameAttributesList[i]
                passCounter = expectedListLength - 1
                break
        
        assert expectedListLength == passCounter, f"test_AttributeElement_NameAttributeValues: The value of the attribute element name attribute is '{errorName}' in the output file, but it should be '{correctName}' based on the expected file."
           
    except IndexError as exception:
        pytest.fail("The structure of the xml tree does not match.")

          
          
    
    
    
    
