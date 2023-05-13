from conftest import count_tags
from conftest import PROPERTIES

jar = 'converter'

def test_properties_tags_count(expected_xml, converter_output_xml):

    expected_count = count_tags(expected_xml, PROPERTIES)
    output_count = count_tags(converter_output_xml, PROPERTIES)

    assert expected_count == output_count, f"test_properties_tags_count: The number of 'properties' tags does not match. Expected {expected_count}, but got {output_count} tags."
