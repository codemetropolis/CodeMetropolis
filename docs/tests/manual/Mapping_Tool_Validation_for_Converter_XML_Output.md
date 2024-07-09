# See if the mapping tool converts the TG_005.TC_001-Converter expected result xml successfully.

## Tags
#component_mapping #testFile-format_XML #category_output

### Purpose
This test's objective is to verify that the mapping tool converted the TG_005.TC_001-Converter expected result xml appropriately.

### Test Project
- A freshly created Maven project in which the Main.java file has been removed and converted using a converter tool.
- [[Expected Results/Converter.Test1Result.xml]]

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
	java -jar mapping-1.5.0.jar -i Converter.Test1Result.xml -m sourcemeter_mapping_example_2_1.xml
	```
	in mapping-1.5.0.jar command line
2. Compare the resulting file with the [[Expected Results/Mapping.Test1Result.xml]] file

### Expected Result
The [[Expected Results/Mapping.Test1Result.xml]] file and the generated file match expect for the buildable ids as they vary.