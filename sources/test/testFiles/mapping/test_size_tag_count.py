from conftest import count_tags
from conftest import SIZE

jar = 'mapping'

def test_size_tags_count(expected_xml, mapping_output_xml):

    expected_count = count_tags(expected_xml, SIZE)
    output_count = count_tags(mapping_output_xml, SIZE)

    assert expected_count == output_count, f"test_size_tags_count: The number of 'size' tags does not match. Expected {expected_count}, but got {output_count} tags."

