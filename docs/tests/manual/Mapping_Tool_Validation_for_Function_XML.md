# See if the mapping tool converts the TG_005.TC_005-Converter expected result xml successfully.

## Tags
#component_mapping #testFile-format_XML #category_output

### Purpose
This test's objective is to verify that the mapping tool converted the TG_005.TC_005-Converter expected result xml appropriately.

### Test Project
- A freshly created Maven project to which we have added a function and used the converter tool to convert.
- [[Expected Results/Converter.Test5Result.xml]]

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
1.  Run
	```cmd
	java -jar mapping-1.5.0.jar -i Converter.Test5Result.xml -m sourcemeter_mapping_example_2_1.xml
	```
	in mapping-1.5.0.jar command line
2. Compare the resulting file with the [[Expected Results/Mapping.Test5Result.xml]] file

### Expected Result
The [[Expected Results/Mapping.Test5Result.xml]] file and the generated file match expect for the buildable ids as they vary.