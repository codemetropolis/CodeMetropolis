import pytest
from conftest import root_tag
from xml.etree import ElementTree as ET
from conftest import NAME

jar = 'converter'

def test_root_element_name_attribute_match(expected_xml, converter_output_xml):

    expected_root_name = root_tag(expected_xml, NAME)
    output_root_name = root_tag(converter_output_xml, NAME)

    assert expected_root_name == output_root_name, f"test_root_element_name_attribute_match: The 'name' attribute of the root element does not match. Expected '{expected_root_name}' attribute value, but got '{output_root_name}'"



"""
import pytest
import xml.etree.ElementTree as ET

jar = 'converter'

@pytest.fixture
def expectedXmlPath(expected):
    return expected

@pytest.fixture
def outputXmlPath(output):
    return f"{output}/converterToMapping.xml"

def getRootNameAttribute(root):
    return root.get('name')

def testRootElementNameAttributeMatch(expectedXmlPath, outputXmlPath):
    with open(expectedXmlPath, 'r') as expectedFile, open(outputXmlPath, 'r') as outputFile:
        expectedRoot = ET.parse(expectedFile).getroot()
        outputRoot = ET.parse(outputFile).getroot()

        expectedName = getRootNameAttribute(expectedRoot)
        outputName = getRootNameAttribute(outputRoot)

        assert expectedName == outputName, f"in testRootElementNameAttributeMatch: \nRoot name attribute value not match: expected='{expectedName}', output='{outputName}'"
"""