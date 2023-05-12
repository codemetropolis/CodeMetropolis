import pytest
import xml.etree.ElementTree as ET
from conftest import extract_tags_and_attributes_nested
from conftest import BUILDABLE
from conftest import ATTRIBUTES
from conftest import ATTRIBUTE
from conftest import VALUE

jar = 'mapping'

def test_attribute_tags_value_attributes_match(expected_xml, mapping_output_xml):
    result_expected = extract_tags_and_attributes_nested(expected_xml, BUILDABLE, ATTRIBUTES, ATTRIBUTE, VALUE)
    result_output = extract_tags_and_attributes_nested(mapping_output_xml, BUILDABLE, ATTRIBUTES, ATTRIBUTE, VALUE)

    assert set(result_expected.keys()) == set(result_output.keys()), f"test_attribute_tags_value_attributes_match: The parent map is different between the expected and generated output"

    for key in result_expected.keys():
        assert set(result_expected[key]) == set(result_output[key]), f"test_attribute_tags_value_attributes_match: Different 'attribute' value attribute value. Path to the different value:\n {key} "
