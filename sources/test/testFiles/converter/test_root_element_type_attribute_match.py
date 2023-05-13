from conftest import root_tag
from conftest import TYPE

jar = 'converter'

def test_root_element_type_attribute_match(expected_xml, converter_output_xml):

    expected_root_type = root_tag(expected_xml, TYPE)
    output_root_type = root_tag(converter_output_xml, TYPE)

    assert expected_root_type == output_root_type, f"test_root_element_type_attribute_match: The 'type' attribute of the root element does not match. Expected '{expected_root_type}' attribute value, but got '{output_root_type}'"
