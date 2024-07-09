# Verify that the mapping tool throws NoSuchElementException with the following message: "No such block" when incorrect minecraft string id added to the mapping file.

## Tags
#component_mapping #testFile-format_XML #category_output

### Purpose
The purpose of this test is to confirm that the mapping tool throws the correct error when a wrong minecraft stringId is written to the mapping file.

### Test File
- [[Test Files/Mapping.TestX.xml]]

### Important
- To guarantee correct functionality, use your file paths throughout.  
- Ensure that the necessary Java version is used for each code you use!
- Always keep in mind that "buildable id" will vary.

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of mapping-1.5.0.jar (path to the folder `CodeMetropolis\sources\distro\`)
- Test file inserted into `CodeMetropolis\sources\distro\` folder
- sourcemeter_mapping_example_2_1.xml file inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Replace "minecraft:glass" with "minecraft:nothing" in the `sourcemeter_mapping_example_2_1.xml` file
 2. Run
	```cmd
	java -jar mapping-1.5.0.jar -i Mapping.TestX.xml -m sourcemeter_mapping_example_2_1.xml
	```
	in mapping-1.5.0.jar command line

### Expected Result
The program throws `NoSuchElementException` with the message: "No such block".