import pytest
from conftest import root_tag
from xml.etree import ElementTree as ET
from conftest import FROM

jar = 'placing'

def test_root_element_from_attribute_match(expected_xml, placing_output_xml):

    expected_root_from = root_tag(expected_xml, FROM)
    output_root_from = root_tag(placing_output_xml, FROM)

    assert expected_root_from == output_root_from, f"test_root_element_from_attribute_match: The 'from' attribute of the root element does not match. Expected '{expected_root_from}' attribute value, but got '{output_root_from}'"
