from conftest import count_tags
from conftest import PROPERTY

jar = 'converter'

def test_property_tags_count(expected_xml, converter_output_xml):

    expected_count = count_tags(expected_xml, PROPERTY)
    output_count = count_tags(converter_output_xml, PROPERTY)

    assert expected_count == output_count, f"test_property_tags_count: The number of 'property' tags does not match. Expected {expected_count}, but got {output_count} tags."
