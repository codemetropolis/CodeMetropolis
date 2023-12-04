from conftest import root_tag
from conftest import TO

jar = 'mapping'

def test_root_element_to_attribute_match(expected_xml, mapping_output_xml):

    expected_root_to = root_tag(expected_xml, TO)
    output_root_to = root_tag(mapping_output_xml, TO)

    assert expected_root_to == output_root_to, f"test_root_element_to_attribute_match: The 'to' attribute of the root element does not match. Expected '{expected_root_to}' attribute value, but got '{output_root_to}'"
