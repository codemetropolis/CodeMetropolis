from conftest import extract_tags_and_attributes
from conftest import BUILDABLE
from conftest import TYPE

jar = 'mapping'

def test_buildable_tags_type_attributes_match(expected_xml, mapping_output_xml):
    result_expected = extract_tags_and_attributes(expected_xml, BUILDABLE, TYPE)
    result_output = extract_tags_and_attributes(mapping_output_xml, BUILDABLE, TYPE)

    assert set(result_expected.keys()) == set(result_output.keys()), f"test_buildable_tags_type_attributes_match: The parent map is different between the expected and generated output"

    for key in result_expected.keys():
        assert set(result_expected[key]) == set(result_output[key]), f"test_buildable_tags_type_attributes_match: Different 'element' type attribute value. Path to the different value:\n {key} "
