from conftest import extract_tags_and_attributes_nested
from conftest import BUILDABLE
from conftest import NONE
from conftest import SIZE
from conftest import Y

jar = 'mapping'

def test_size_tags_y_attributes_match(expected_xml, mapping_output_xml):
    result_expected = extract_tags_and_attributes_nested(expected_xml, BUILDABLE, NONE, SIZE, Y)
    result_output = extract_tags_and_attributes_nested(mapping_output_xml, BUILDABLE, NONE, SIZE, Y)

    assert set(result_expected.keys()) == set(result_output.keys()), f"test_size_tags_y_attributes_match: The parent map is different between the expected and generated output"

    for key in result_expected.keys():
        assert set(result_expected[key]) == set(result_output[key]), f"test_size_tags_y_attributes_match: Different 'size' y attribute value. Path to the different value:\n {key} "
