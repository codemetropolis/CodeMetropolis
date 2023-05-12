import pytest
from conftest import count_tags
from xml.etree import ElementTree as ET
from conftest import POSITION

jar = 'placing'

def test_position_tags_count(expected_xml, placing_output_xml):

    expected_count = count_tags(expected_xml, POSITION)
    output_count = count_tags(placing_output_xml, POSITION)

    assert expected_count == output_count, f"test_position_tags_count: The number of 'position' tags does not match. Expected {expected_count}, but got {output_count} tags."

