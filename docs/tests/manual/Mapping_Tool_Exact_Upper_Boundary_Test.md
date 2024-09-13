# Verify that the mapping tool's scale option functions properly.

## Tags
#component_mapping #testFile-format_XML #category_output

### Purpose
The purpose of this test is to confirm that the mapping tool functions correctly when the scale is 100

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
	java -jar mapping-1.5.0.jar -i Mapping.TestX.xml -m sourcemeter_mapping_example_2_1.xml -s 100
	```
	in mapping-1.5.0.jar command line
2. Compare the resulting file with the [[Expected Results/Mapping.Test13Result.xml]] file

### Expected Result
The [[Expected Results/Mapping.Test13Result.xml]] file and the generated file match expect for the buildable ids as they vary.