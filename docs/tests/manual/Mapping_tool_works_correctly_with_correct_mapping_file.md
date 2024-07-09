# Verify that the mapping tool works correctly when a correct mapping file is given.

## Tags
#component_mapping #testFile-format_XML #category_output

### Purpose
The purpose of this test is to confirm that the mapping tool works properly with a correct mapping file.

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
 1. Run
	```cmd
	java -jar mapping-1.5.0.jar -i Mapping.TestX.xml -m sourcemeter_mapping_example_2_1.xml
	```
	in mapping-1.5.0.jar command line

### Expected Result
The program runs without any errors and the correct mappingToPlacing.xml file is created.