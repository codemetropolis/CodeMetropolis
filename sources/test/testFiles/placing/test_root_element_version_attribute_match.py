from conftest import root_tag
from conftest import VERSION

jar = 'placing'

def test_root_element_version_attribute_match(expected_xml, placing_output_xml):

    expected_root_version = root_tag(expected_xml, VERSION)
    output_root_version = root_tag(placing_output_xml, VERSION)

    assert expected_root_version == output_root_version, f"test_root_element_version_attribute_match: The 'version' attribute of the root element does not match. Expected '{expected_root_version}' attribute value, but got '{output_root_version}'"
