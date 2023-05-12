import pytest
import xml.etree.ElementTree as ET
from conftest import extract_tags_and_attributes_nested
from conftest import BUILDABLE
from conftest import ATTRIBUTES
from conftest import ATTRIBUTE
from conftest import NAME

jar = 'placing'

def test_attribute_tags_name_attributes_match(expected_xml, placing_output_xml):
    result_expected = extract_tags_and_attributes_nested(expected_xml, BUILDABLE, ATTRIBUTES, ATTRIBUTE, NAME)
    result_output = extract_tags_and_attributes_nested(placing_output_xml, BUILDABLE, ATTRIBUTES, ATTRIBUTE, NAME)

    assert set(result_expected.keys()) == set(result_output.keys()), f"test_attribute_tags_name_attributes_match: The parent map is different between the expected and generated output"

    for key in result_expected.keys():
        assert set(result_expected[key]) == set(result_output[key]), f"test_attribute_tags_name_attributes_match: Different 'attribute' name attribute value. Path to the different value:\n {key} "
