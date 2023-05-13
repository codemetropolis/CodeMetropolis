from conftest import root_tag
from conftest import NAME

jar = 'converter'

def test_root_element_name_attribute_match(expected_xml, converter_output_xml):

    expected_root_name = root_tag(expected_xml, NAME)
    output_root_name = root_tag(converter_output_xml, NAME)

    assert expected_root_name == output_root_name, f"test_root_element_name_attribute_match: The 'name' attribute of the root element does not match. Expected '{expected_root_name}' attribute value, but got '{output_root_name}'"
