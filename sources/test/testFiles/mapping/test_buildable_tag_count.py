from conftest import count_tags
from conftest import BUILDABLE

jar = 'mapping'

def test_buildable_tags_count(expected_xml, mapping_output_xml):

    expected_count = count_tags(expected_xml, BUILDABLE)
    output_count = count_tags(mapping_output_xml, BUILDABLE)

    assert expected_count == output_count, f"test_buildable_tags_count: The number of 'buildable' tags does not match. Expected {expected_count}, but got {output_count} tags."

