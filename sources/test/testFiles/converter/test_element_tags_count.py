import pytest
from conftest import count_tags
from xml.etree import ElementTree as ET
from conftest import ELEMENT

jar = 'converter'

def test_element_tags_count(expected_xml, converter_output_xml):

    expected_count = count_tags(expected_xml, ELEMENT)
    output_count = count_tags(converter_output_xml, ELEMENT)

    assert expected_count == output_count, f"test_element_tags_count: The number of 'element' tags does not match. Expected {expected_count}, but got {output_count} tags."
