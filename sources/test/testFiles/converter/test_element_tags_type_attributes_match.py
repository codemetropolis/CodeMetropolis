from conftest import extract_tags_and_attributes
from conftest import ELEMENT
from conftest import TYPE

jar = 'converter'

def test_element_tags_type_attributes_match(expected_xml, converter_output_xml):
    result_expected = extract_tags_and_attributes(expected_xml, ELEMENT, TYPE)
    result_output = extract_tags_and_attributes(converter_output_xml, ELEMENT, TYPE)

    assert set(result_expected.keys()) == set(result_output.keys()), f"test_element_tags_type_attributes_match: The parent map is different between the expected and generated output"

    for key in result_expected.keys():
        assert set(result_expected[key]) == set(result_output[key]), f"test_element_tags_type_attributes_match: Different 'element' type attribute value. Path to the different value:\n {key} "
