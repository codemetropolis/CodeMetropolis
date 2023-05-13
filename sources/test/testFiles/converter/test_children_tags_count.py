from conftest import count_tags
from conftest import CHILDREN

jar = 'converter'

def test_children_tags_count(expected_xml, converter_output_xml):

    expected_count = count_tags(expected_xml, CHILDREN)
    output_count = count_tags(converter_output_xml, CHILDREN)

    assert expected_count == output_count, f"test_children_tags_count: The number of 'children' tags does not match. Expected {expected_count}, but got {output_count} tags."
