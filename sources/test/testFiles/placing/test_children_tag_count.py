import pytest
from conftest import count_tags
from xml.etree import ElementTree as ET
from conftest import CHILDREN

jar = 'placing'

def test_children_tags_count(expected_xml, placing_output_xml):

    expected_count = count_tags(expected_xml, CHILDREN)
    output_count = count_tags(placing_output_xml, CHILDREN)

    assert expected_count == output_count, f"test_children_tags_count: The number of 'children' tags does not match. Expected {expected_count}, but got {output_count} tags."

