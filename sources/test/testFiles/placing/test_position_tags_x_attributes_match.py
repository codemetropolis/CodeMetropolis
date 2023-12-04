from conftest import extract_tags_and_attributes_nested
from conftest import BUILDABLE
from conftest import NONE
from conftest import POSITION
from conftest import X

jar = 'placing'

def test_position_tags_x_attributes_match(expected_xml, placing_output_xml):
    result_expected = extract_tags_and_attributes_nested(expected_xml, BUILDABLE, NONE, POSITION, X)
    result_output = extract_tags_and_attributes_nested(placing_output_xml, BUILDABLE, NONE, POSITION, X)

    assert set(result_expected.keys()) == set(result_output.keys()), f"test_position_tags_x_attributes_match: The parent map is different between the expected and generated output"

    for key in result_expected.keys():
        assert set(result_expected[key]) == set(result_output[key]), f"test_position_tags_x_attributes_match: Different 'position' x attribute value. Path to the different value:\n {key} "
