from conftest import extract_tags_and_attributes
from conftest import ELEMENT
from conftest import NAME

jar = 'converter'

def test_element_tags_name_attributes_match(expected_xml, converter_output_xml):
    result_expected = extract_tags_and_attributes(expected_xml, ELEMENT, NAME)
    result_output = extract_tags_and_attributes(converter_output_xml, ELEMENT, NAME)

    # Szülő_útvonal_ell
    assert set(result_expected.keys()) == set(result_output.keys()), f"test_element_tags_name_attributes_match: The parent map is different between the expected and generated output"
