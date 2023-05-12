import pytest
import xml.etree.ElementTree as ET
from conftest import extract_tags_and_attributes
from conftest import ELEMENT
from conftest import NAME

jar = 'converter'

def test_element_tags_name_attributes_match(expected_xml, converter_output_xml):
    result_expected = extract_tags_and_attributes(expected_xml, ELEMENT, NAME)
    result_output = extract_tags_and_attributes(converter_output_xml, ELEMENT, NAME)

    # Szülő_útvonal_ell
    assert set(result_expected.keys()) == set(result_output.keys()), f"test_element_tags_name_attributes_match: The parent map is different between the expected and generated output"

    #ez_esetben nem fontos, hiszen ugyanaz a szülő útvonal, mint a kulcsokhoz szereplő értékek. Tehát ha hibás az egyik név, a teszt a szülő útvonal ell.-nél elbukik
    #for key in result_expected.keys():
        #assert set(result_expected[key]) == set(result_output[key]), f"test_element_tags_name_attributes_match: Different 'element' name attribute value. Path to the different value:\n {key} "


"""
#NA, EZ MOST MÁR JÓ
import xml.etree.ElementTree as ET

def extract_element_tags_and_attributes(xml_path):

    # Parse the XML file and get the root element
    tree = ET.parse(xml_path)
    root = tree.getroot()

    # Initialize a dictionary to keep track of the parent-child relationships
    parent_map = {c: p for p in tree.iter() for c in p}

    result = {}

    for elem in root.iter():
        if elem.tag == "element":
            # Extract the name attribute value of the current element
            name = elem.attrib.get("name")
            if name:
                # Create a tuple containing the name attribute values of the current element and all its ancestors
                parents = ()
                parent = parent_map.get(elem)
                while parent is not None:
                    parent_name = parent.attrib.get("name")
                    if parent_name:
                        parents = (parent_name,) + parents
                    parent = parent_map.get(parent, None)
                parents = parents + (name,)
                # Add the tuple as a key and the element as a value to the dictionary
                if parents in result:
                    # If the key is already in the dictionary, append the name attribute value to the list
                    result[parents].append((elem.find("properties/property").attrib.get("name"),))
                else:
                    result[parents] = [(elem.find("properties/property").attrib.get("name"),)]

    # Convert the lists in the dictionary values to tuples
    result = {k: tuple(v) for k, v in result.items()}

    return result


result1 = extract_element_tags_and_attributes("C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/test/egyszeru_elvart_xml_teszt.xml")
result2 = extract_element_tags_and_attributes("C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/test/egyszeru_output_xml_teszt.xml")

# Check if the keys of the dictionaries are the same
if result1.keys() == result2.keys():
    print("Keys are the same!")
else:
    print("Keys are different!")

# Check if the values of the dictionaries are the same
if result1 == result2:
    print("Values are the same!")
else:
    print("Values are different!")





Okésnak tűnik
import xml.etree.ElementTree as ET

def extract_element_tags_and_attributes(xml_path):
    # Parse the XML file and get the root element
    tree = ET.parse(xml_path)
    root = tree.getroot()

    # Initialize a dictionary to keep track of the parent-child relationships
    parent_map = {c: p for p in tree.iter() for c in p}

    result = {}

    for elem in root.iter():
        if elem.tag == "element":
            # Extract the name attribute value of the current element
            name = elem.attrib.get("name")
            if name:
                # Create a tuple containing the name attribute values of the current element and all its ancestors
                parents = ()
                parent = parent_map.get(elem)
                while parent is not None:
                    parent_name = parent.attrib.get("name")
                    if parent_name:
                        parents = (parent_name,) + parents
                    parent = parent_map.get(parent, None)
                parents = parents + (name,)
                # Add the tuple as a key and the element as a value to the dictionary
                if parents in result:
                    # If the key is already in the dictionary, append the name attribute value to the list
                    result[parents].append(elem.attrib.get("name"))
                else:
                    result[parents] = [elem.attrib.get("name")]

    # Convert the lists in the dictionary values to tuples
    result = {k: tuple(v) for k, v in result.items()}

    return result

result1 = extract_element_tags_and_attributes("C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/test/converterToMapping.xml")
result2 = extract_element_tags_and_attributes("C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/test/converterToMapping_output.xml")

# Check if the keys of the dictionaries are the same
if result1.keys() == result2.keys():
    print("Keys are the same!")
else:
    print("Keys are different!")

# Check if the values of the dictionaries are the same
if result1 == result2:
    print("Values are the same!")
else:
    print("Values are different!")

#result1 = extract_element_tags_and_attributes("C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/test/converterToMapping.xml")
#result2 = extract_element_tags_and_attributes("C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/test/converterToMapping_output.xml")

# Get the set difference between the two dictionaries
#diff = set(result1.items()) - set(result2.items())
#if diff:
#    print("Diff is not empty!\n\n", diff)
#else:
#    print("Diff empty!")


EZ NAGYON NAGYON JÓ
import xml.etree.ElementTree as ET

def extract_element_tags_and_attributes(xml_path):
    # Parse the XML file and get the root element
    tree = ET.parse(xml_path)
    root = tree.getroot()

    # Initialize a dictionary to keep track of the parent-child relationships
    parent_map = {c: p for p in tree.iter() for c in p}

    result = {}

    for elem in root.iter():
        if elem.tag == "element":
            # Extract the name attribute value of the current element
            name = elem.attrib.get("name")
            if name:
                # Create a tuple containing the name attribute values of the current element and all its ancestors
                parents = ()
                parent = parent_map.get(elem)
                while parent is not None:
                    parent_name = parent.attrib.get("name")
                    if parent_name:
                        parents = (parent_name,) + parents
                    parent = parent_map.get(parent, None)
                parents = parents + (name,)
                # Add the tuple as a key and the element as a value to the dictionary
                if parents in result:
                    # If the key is already in the dictionary, raise an error
                    raise ValueError(f"Duplicate key '{parents}' found in XML file.")
                else:
                    result[parents] = [tuple(elem.attrib.items())]

    # Convert the lists in the dictionary values to tuples
    result = {k: tuple(v) for k, v in result.items()}

    return result

result1 = extract_element_tags_and_attributes("C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/test/egyszeru_elvart_xml_teszt.xml")
result2 = extract_element_tags_and_attributes("C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/test/egyszeru_output_xml_teszt.xml")

# Get the set difference between the two dictionaries
diff = set(result1.items()) - set(result2.items())
if diff:
    print("Diff is not empty!\n\n", diff)
else:
    print("Diff empty!")


import xml.etree.ElementTree as ET
from bidict import bidict

def get_element_hierarchy(xml_path):
    tree = ET.parse(xml_path)
    root = tree.getroot()

    parent_map = {c: p for p in tree.iter() for c in p}

    result = bidict()

    for elem in root.iter():
        if elem.tag == "element":
            name = elem.attrib.get("name")
            if name:
                parents = []
                parent = parent_map.get(elem)
                while parent is not None:
                    parent_name = parent.attrib.get("name")
                    if parent_name:
                        parents.insert(0, parent_name)
                    parent = parent_map.get(parent)
                parents.append(name)
                parents_tuple = tuple(parents)
                result[parents_tuple] = elem

    return result

def compare_element_hierarchies(xml1_path, xml2_path):
    hierarchy1 = get_element_hierarchy(xml1_path)
    hierarchy2 = get_element_hierarchy(xml2_path)

    # Compare each element in hierarchy1 to its counterpart in hierarchy2
    for key, elem1 in hierarchy1.items():
        elem2 = hierarchy2.get(key)
        if elem2 is None:
            print("Element not found in hierarchy2:", key)
        elif elem1.attrib.get("name") != elem2.attrib.get("name"):
            print("Element names do not match for key:", key)

    # Check if hierarchy2 has any elements that are not in hierarchy1
    extra_elements = set(hierarchy2.keys()) - set(hierarchy1.keys())
    # Check if hierarchy1 has any elements that are not in hierarchy2
    extra_elements2 = set(hierarchy1.keys()) - set(hierarchy2.keys())    
    if extra_elements or extra_elements2:
        print("The two XML files do not have the same element hierarchy.")
    else:
        print("The two XML files have the same element hierarchy.\n\n", hierarchy1, "\n\n", hierarchy2)

xml1_path = "C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/distro/converterToMapping.xml"
xml2_path = "C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/distro/converterToMapping_output.xml"
compare_element_hierarchies(xml1_path, xml2_path)


#ez mégjobb, használja a bidict()-et
import xml.etree.ElementTree as ET
from bidict import bidict

def get_element_hierarchy(xml_path):
    tree = ET.parse(xml_path)
    root = tree.getroot()

    parent_map = {c: p for p in tree.iter() for c in p}

    result = bidict()

    for elem in root.iter():
        if elem.tag == "element":
            name = elem.attrib.get("name")
            if name:
                parents = []
                parent = parent_map.get(elem)
                while parent is not None:
                    parent_name = parent.attrib.get("name")
                    if parent_name:
                        parents.insert(0, parent_name)
                    parent = parent_map.get(parent)
                parents.append(name)
                parents_tuple = tuple(parents)
                result[parents_tuple] = elem

    return result

def compare_element_hierarchies(xml1_path, xml2_path):
    hierarchy1 = get_element_hierarchy(xml1_path)
    hierarchy2 = get_element_hierarchy(xml2_path)
    print("\n\n", hierarchy1, "\n\n", hierarchy2)

xml1_path = "C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/distro/converterToMapping.xml"
xml2_path = "C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/distro/converterToMapping_output.xml"
compare_element_hierarchies(xml1_path, xml2_path)




#ez már elég jó
import xml.etree.ElementTree as ET

def get_element_hierarchy(xml_path):
    tree_exp = ET.parse(xml_path)
    root_exp = tree_exp.getroot()

    # Initialize a dictionary to keep track of the parent-child relationships
    parent_map = {c: p for p in tree_exp.iter() for c in p}

    result = {}

    for elem in root_exp.iter():
        if elem.tag == "element":
            # Extract the name attribute value of the current element
            name = elem.attrib.get("name")
            if name:
                # Create a list containing the name attribute values of the current element and all its ancestors
                parents = []
                parent = parent_map.get(elem)
                while parent is not None:
                    parent_name = parent.attrib.get("name")
                    if parent_name:
                        parents.insert(0, parent_name)
                    parent = parent_map.get(parent, None)
                parents.append(name)
                parents_string = " > ".join(parents)
                # Add the string as a key and the element as a value to the dictionary
                if parents_string in result:
                    # If the key is already in the dictionary, append the element to the list of values
                    result[parents_string] = [result[parents_string], elem]
                else:
                    result[parents_string] = elem

    return result

# Get the hierarchy for xml_exp1
result1 = get_element_hierarchy("C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/distro/converterToMapping.xml")
print("\n\n", result1, "\n\n")

# Get the hierarchy for xml_exp2
result2 = get_element_hierarchy("C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/distro/converterToMapping_output.xml")
print("\n\n", result2, "\n\n")


#EZ A LEGJOBB EDDIG
import xml.etree.ElementTree as ET

tree_exp = ET.parse("C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/distro/converterToMapping.xml")
root_exp = tree_exp.getroot()

# Initialize a dictionary to keep track of the parent-child relationships
parent_map = {c: p for p in tree_exp.iter() for c in p}

result = {}

for elem in root_exp.iter():
    if elem.tag == "element":
        # Extract the name attribute value of the current element
        name = elem.attrib.get("name")
        if name:
            # Create a list containing the name attribute values of the current element and all its ancestors
            parents = []
            parent = parent_map.get(elem)
            while parent is not None:
                parent_name = parent.attrib.get("name")
                if parent_name:
                    parents.insert(0, parent_name)
                parent = parent_map.get(parent, None)
            parents.append(name)
            parents_string = " > ".join(parents)
            # Add the string as a key and the element as a value to the dictionary
            if parents_string in result:
                # If the key is already in the dictionary, append the element to the list of values
                result[parents_string] = [result[parents_string], elem]
            else:
                result[parents_string] = elem

print(result)



EZ JÓ, DE NEM KEZELI, HA KÉT AZONOS NEVŐ VAN 1 szinten
import xml.etree.ElementTree as ET
from bidict import bidict

tree = ET.parse("C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/distro/converterToMapping.xml")
root = tree.getroot()

parent_map = {c: p for p in tree.iter() for c in p}

result = bidict()

for elem in root.iter():
    if elem.tag == "element":
        # Extract the name attribute value of the current element
        name = elem.attrib.get("name")
        if name:
            # Create a tuple containing the name attribute values of the current element and all its ancestors
            parents = []
            parent = parent_map.get(elem)
            while parent is not None:
                parent_name = parent.attrib.get("name")
                if parent_name:
                    parents.insert(0, parent_name)
                parent = parent_map.get(parent)
            parents.append(name)
            parents_tuple = tuple(parents)
            # Add the tuple as a key and the element as a value to the bidict
            result[parents_tuple] = elem

print("\n\n",result)




import xml.etree.ElementTree as ET
from bidict import bidict

tree = ET.parse("C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/distro/converterToMapping.xml")
root = tree.getroot()

# create a bidict to hold the paths and corresponding elements
path_map = bidict()

# create a dictionary to map each element to its parent
parent_map = {c: p for p in tree.iter() for c in p}

# recursively add elements and their paths to the bidict
def add_element_path(element, path):
    path_map[path] = element
    for child in element:
        add_element_path(child, path + (child.get('name'),))

# add the root element and its path to the bidict
add_element_path(root, (root.get('name'),))

# print the bidict
print("\n\n",path_map)


import xml.etree.ElementTree as ET

tree = ET.parse('C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/distro/converterToMapping.xml')
root = tree.getroot()

parent_map = {}
for elem in tree.iter():
    for child in elem:
        parent_map[child] = elem

properties = {}
for prop in root.iter('property'):
    path = []
    parent = parent_map[prop]
    while parent is not None:
        path.append(parent.tag)
        parent = parent_map.get(parent)
    path.reverse()
    path.append(prop.attrib['name'])
    properties[tuple(path)] = prop.text

    print(properties , "\n")




import xml.etree.ElementTree as ET

tree = ET.parse('C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/distro/converterToMapping.xml')
root = tree.getroot()

parent_map = {}
for elem in tree.iter():
    for child in elem:
        parent_map[child] = elem

for prop in root.iter('property'):
    path = []
    parent = parent_map[prop]
    while parent is not None:
        path.append(parent.tag)
        parent = parent_map.get(parent)
    path.reverse()
    path.append('property')
    print(path)


#expected_file = "C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/distro/converterToMapping.xml"
#output_file = "C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/distro/converterToMapping_output.xml"
path_dict = extract_elements('C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/distro/converterToMapping.xml', 'property', 'name')
print(path_dict)


import pytest
from conftest import get_tags

jar = 'converter'

def test_element_tags_name_attributes_match(expected_xml, converter_output_xml, element_tag, name_attribute):
    expected_elements = get_tags(expected_xml, element_tag, name_attribute)
    output_elements = get_tags(converter_output_xml, element_tag, name_attribute)

    assert len(expected_elements) == len(output_elements), f"test_element_tags_name_attributes_match: Expected {len(expected_elements)} element tags, but got {len(output_elements)} element tags"
    for depth in expected_elements.keys():
        assert depth in output_elements, f"test_element_tags_name_attributes_match: Expected depth {depth} not found in output"
        
        #assert len(expected_elements[depth]) == len(output_elements[depth]), f"Expected {len(expected_elements[depth])} name attribute at depth {depth}, but got {len(output_elements[depth])} name attribute"

        diff_set_expected = set(expected_elements[depth]) - set(output_elements[depth])
        diff_set_output = set(output_elements[depth]) - set(expected_elements[depth])
        
        assert not diff_set_expected, f"test_element_tags_name_attributes_match: Expected '{diff_set_expected}' name attribute at depth {depth} not found in the output"
        assert not diff_set_output, f"test_element_tags_name_attributes_match: Output '{diff_set_output}' name attribute at depth {depth} not found in the input"

#pszeudo:
#path: bidict[tuple[str, ...], XElement]
"""



"""
OLD: REMOVE
import os
import xml.etree.ElementTree as ET
import pytest

jar = 'converter'

@pytest.fixture
def expected_xml_path(expected):
    return expected

@pytest.fixture
def output_xml_path(output):
    return f"{output}/converterToMapping.xml"

def get_elements(xml):
    root = ET.fromstring(xml)
    elements = {}

    def walk(node, depth):
        if node.tag == 'element':
            name = node.attrib['name']
            if depth not in elements:
                elements[depth] = []
            elements[depth].append(name)

        for child in node:
            walk(child, depth + 1)

    walk(root, 0)

    for depth, names in elements.items():
        elements[depth] = sorted(names)

    return elements

def test_xml():
    output_file_path = 'C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/test/output/converterToMapping.xml'
    expected_file_path = 'C:/Users/Zoli/CodeMetropolis_105_updated_to11/sources/test/others/expected/converterToMapping.xml'
    with open(expected_file_path, 'r') as expected_xml_file, open(output_file_path, 'r') as output_xml_file:
        
        expected_xml = expected_xml_file.read()
        output_xml = output_xml_file.read()

        expected_elements = get_elements(expected_xml)
        output_elements = get_elements(output_xml)

        assert len(expected_elements) == len(output_elements), f"Expected {len(expected_elements)} elements, but got {len(output_elements)} elements"
        for depth in expected_elements.keys():
            assert depth in output_elements, f"Expected depth {depth} not found in output"
            
            assert len(expected_elements[depth]) == len(output_elements[depth]), f"Expected {len(expected_elements[depth])} elements at depth {depth}, but got {len(output_elements[depth])} elements"

            diff_set = set(expected_elements[depth]) - set(output_elements[depth])
            diff_set2 = set(output_elements[depth]) - set(expected_elements[depth])
            assert not diff_set, f"Expected element {diff_set} at depth {depth} were not found in the output"
            assert not diff_set2, f"Output element {diff_set2} at depth {depth} were not found in the input"
"""