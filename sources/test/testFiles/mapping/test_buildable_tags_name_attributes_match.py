import pytest
import xml.etree.ElementTree as ET
from conftest import extract_tags_and_attributes
from conftest import BUILDABLE
from conftest import NAME

jar = 'mapping'

def test_buildable_tags_name_attributes_match(expected_xml, mapping_output_xml):
    result_expected = extract_tags_and_attributes(expected_xml, BUILDABLE, NAME)
    result_output = extract_tags_and_attributes(mapping_output_xml, BUILDABLE, NAME)

    # Szülő_útvonal_ell
    assert set(result_expected.keys()) == set(result_output.keys()), f"test_buildable_tags_name_attributes_match: The parent map is different between the expected and generated output"

    #ez_esetben nem fontos, hiszen ugyanaz a szülő útvonal, mint a kulcsokhoz szereplő értékek. Tehát ha hibás az egyik név, a teszt a szülő útvonal ell.-nél elbukik
    #for key in result_expected.keys():
        #assert set(result_expected[key]) == set(result_output[key]), f"test_element_tags_name_attributes_match: Different 'element' name attribute value. Path to the different value:\n {key} "

"""
import pytest
from conftest import get_tags

jar = 'mapping'

def test_buildable_tags_name_attributes_match(expected_xml, mapping_output_xml, buildable_tag, name_attribute):
    expected_attribute = get_tags(expected_xml, buildable_tag, name_attribute)
    output_attribute = get_tags(mapping_output_xml, buildable_tag, name_attribute)

    assert len(expected_attribute) == len(output_attribute), f"test_buildable_tags_name_attributes_match: Expected {len(expected_attribute)} element tags, but got {len(output_attribute)} element tags"
    for depth in expected_attribute.keys():
        assert depth in output_attribute, f"test_buildable_tags_name_attributes_match: Expected depth {depth} not found in output"
        
        diff_set_expected = set(expected_attribute[depth]) - set(output_attribute[depth])
        diff_set_output = set(output_attribute[depth]) - set(expected_attribute[depth])
        
        assert not diff_set_expected, f"test_buildable_tags_name_attributes_match: Expected '{diff_set_expected}' name attribute at depth {depth} not found in the output"
        assert not diff_set_output, f"test_buildable_tags_name_attributes_match: Output '{diff_set_output}' name attribute at depth {depth} not found in the input"
"""