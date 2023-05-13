from conftest import extract_tags_and_attributes
from conftest import BUILDABLE
from conftest import NAME

jar = 'placing'

def test_buildable_tags_name_attributes_match(expected_xml, placing_output_xml):
    result_expected = extract_tags_and_attributes(expected_xml, BUILDABLE, NAME)
    result_output = extract_tags_and_attributes(placing_output_xml, BUILDABLE, NAME)

    assert set(result_expected.keys()) == set(result_output.keys()), f"test_buildable_tags_name_attributes_match: The parent map is different between the expected and generated output"
