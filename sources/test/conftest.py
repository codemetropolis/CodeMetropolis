import pytest
from xml.etree import ElementTree as ET

#constants
ELEMENT = "element"
PROPERTY = "property"
PROPERTIES = "properties"
CHILDREN = "children"
ATTRIBUTES = "attributes"
ATTRIBUTE = "attribute"
BUILDABLE = "buildable"
POSITION = "position"
SIZE = "size"
NAME = "name"
TYPE = "type"
VALUE = "value"
X = "x"
Y = "y"
Z = "z"
FROM = "from"
TO = "to"
VERSION = "version"
NONE = None

# arguments
def pytest_addoption(parser):
    parser.addoption("--expected")
    parser.addoption("--output")


@pytest.fixture
def expected_xml(request):
    expected = request.config.getoption("--expected")
    tree = ET.parse(expected)
    return tree


@pytest.fixture
def converter_output_xml(request):
    output = request.config.getoption("--output")
    output_full_path = f"{output}/converterToMapping.xml"
    tree = ET.parse(output_full_path)
    return tree


@pytest.fixture
def mapping_output_xml(request):
    output = request.config.getoption("--output")
    output_full_path = f"{output}/mappingToPlacing.xml"
    tree = ET.parse(output_full_path)
    return tree


@pytest.fixture
def placing_output_xml(request):
    output = request.config.getoption("--output")
    output_full_path = f"{output}/placingToRendering.xml"
    tree = ET.parse(output_full_path)
    return tree

@pytest.fixture
def rendering_output_path(request):
    output = request.config.getoption("--output")
    output_full_path = f"{output}/world"
    return output_full_path

def count_tags(xml, tag):
    root = xml.getroot()
    return len(list(root.iter(tag)))

def root_tag(xml, attribute):
    root = xml.getroot()
    return root.attrib.get(attribute)


#Itt még ki lehetne az ismétlődő kódokat szervezni
def extract_tags_and_attributes_nested(tree, parent_path_elem, parent_elem, selected_elem, selected_attr):
    root = tree.getroot()

    # szulo_map
    parent_map = {child: parent for parent in tree.iter() for child in parent}

    #dict
    diction = {}

    for elem in root.iter():
        if elem.tag == parent_path_elem:
            name = elem.attrib.get(NAME)
            if name == "":
                name = None 
            #szülőt és aktuális_tagot tartalmazó tupple
            parents = ()
            parent = parent_map.get(elem)
            while parent is not None:
                parent_name = parent.attrib.get(NAME)
                if parent_name:
                    parents = (parent_name,) + parents
                parent = parent_map.get(parent, None)
            #kulcs-érték párok beállítása
            parents = parents + (name,)

            #property nevek kigyűjtése
            if parent_elem != None:
                properties = elem.find(parent_elem)
                if properties != None:
                    sub_tags = properties.findall(selected_elem)
                    sub_names = [tag.attrib.get(selected_attr) for tag in sub_tags]

            else: 
                sub_tags = elem.find(selected_elem)
                sub_names = [sub_tags.attrib.get(selected_attr)]

            sub_names.sort()
            #property nevek hozzáfűzése
            if parents in diction:
                diction[parents].append(tuple(sub_names))
            else:
                diction[parents] = [tuple(sub_names)]

    return diction



def extract_tags_and_attributes(tree, parent_path_elem, selected_attr):
    root = tree.getroot()

    # szulo_map
    parent_map = {child: parent for parent in tree.iter() for child in parent}

    #dict
    diction = {}

    for elem in root.iter():
        if elem.tag == parent_path_elem:
            name = elem.attrib.get(NAME)
            if name == "":
                name = None  
            parents = ()
            parent = parent_map.get(elem)
            while parent is not None:
                parent_name = parent.attrib.get(NAME)
                if parent_name:
                    parents = (parent_name,) + parents
                parent = parent_map.get(parent, None)
            parents = parents + (name,)
            
            sel_act = elem.attrib.get(selected_attr)

            if parents in diction:
                diction[parents].append((sel_act,))
            else:
                diction[parents] = [(sel_act,)]

    return diction



