import pytest
from conftest import count_tags
from xml.etree import ElementTree as ET
from conftest import ATTRIBUTE

jar = 'mapping'

def test_attribute_tags_count(expected_xml, mapping_output_xml):

    expected_count = count_tags(expected_xml, ATTRIBUTE)
    output_count = count_tags(mapping_output_xml, ATTRIBUTE)

    assert expected_count == output_count, f"test_attribute_tags_count: The number of 'attribute' tags does not match. Expected {expected_count}, but got {output_count} tags."

