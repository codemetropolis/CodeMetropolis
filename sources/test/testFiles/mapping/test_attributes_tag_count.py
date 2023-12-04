from conftest import count_tags
from conftest import ATTRIBUTES

jar = 'mapping'

def test_attributes_tags_count(expected_xml, mapping_output_xml):

    expected_count = count_tags(expected_xml, ATTRIBUTES)
    output_count = count_tags(mapping_output_xml, ATTRIBUTES)

    assert expected_count == output_count, f"test_attributes_tags_count: The number of 'attributes' tags does not match. Expected {expected_count}, but got {output_count} tags."

