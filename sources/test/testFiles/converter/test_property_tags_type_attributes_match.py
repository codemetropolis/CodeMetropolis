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

