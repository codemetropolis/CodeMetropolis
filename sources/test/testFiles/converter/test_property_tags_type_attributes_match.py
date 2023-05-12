import pytest
import xml.etree.ElementTree as ET
from conftest import extract_tags_and_attributes_nested
from conftest import ELEMENT
from conftest import PROPERTIES
from conftest import PROPERTY
from conftest import TYPE

jar = 'converter'

def test_property_tags_type_attributes_match(expected_xml, converter_output_xml):
    result_expected = extract_tags_and_attributes_nested(expected_xml, ELEMENT, PROPERTIES, PROPERTY, TYPE)
    result_output = extract_tags_and_attributes_nested(converter_output_xml, ELEMENT, PROPERTIES, PROPERTY, TYPE)

    # Szülő_útvonal_ell
    assert set(result_expected.keys()) == set(result_output.keys()), "test_property_tags_type_attributes_match: The parent map is different between the expected and generated output"

    #Értékek_ell
    for key in result_expected.keys():
        assert set(result_expected[key]) == set(result_output[key]), f"test_property_tags_type_attributes_match: Different 'property' type attribute value. Path to the different value:\n {key} "

"""
OLD:
import pytest
from conftest import get_tags

jar = 'converter'

def test_property_tags_type_attributes_match(expected_xml, converter_output_xml, property_tag, type_attribute):
    expected_elements = get_tags(expected_xml, property_tag, type_attribute)
    output_elements = get_tags(converter_output_xml, property_tag, type_attribute)

    assert len(expected_elements) == len(output_elements), f"test_property_tags_type_attributes_match: Expected {len(expected_elements)} property tags, but got {len(output_elements)} property tags"
    for depth in expected_elements.keys():
        assert depth in output_elements, f"test_property_tags_type_attributes_match: Expected depth {depth} not found in output"
        
        diff_set_expected = set(expected_elements[depth]) - set(output_elements[depth])
        diff_set_output = set(output_elements[depth]) - set(expected_elements[depth])
        
        assert not diff_set_expected, f"test_property_tags_type_attributes_match: Expected '{diff_set_expected}' type attribute at depth {depth} not found in the output"
        assert not diff_set_output, f"test_property_tags_type_attributes_match: Output '{diff_set_output}' type attribute at depth {depth} not found in the input"
"""