import pytest
import xml.etree.ElementTree as ET
from conftest import extract_tags_and_attributes
from conftest import BUILDABLE
from conftest import NAME

jar = 'placing'

def test_buildable_tags_name_attributes_match(expected_xml, placing_output_xml):
    result_expected = extract_tags_and_attributes(expected_xml, BUILDABLE, NAME)
    result_output = extract_tags_and_attributes(placing_output_xml, BUILDABLE, NAME)

    # Szülő_útvonal_ell
    assert set(result_expected.keys()) == set(result_output.keys()), f"test_buildable_tags_name_attributes_match: The parent map is different between the expected and generated output"

    #ez_esetben nem fontos, hiszen ugyanaz a szülő útvonal, mint a kulcsokhoz szereplő értékek. Tehát ha hibás az egyik név, a teszt a szülő útvonal ell.-nél elbukik
    #for key in result_expected.keys():
        #assert set(result_expected[key]) == set(result_output[key]), f"test_element_tags_name_attributes_match: Different 'element' name attribute value. Path to the different value:\n {key} "

