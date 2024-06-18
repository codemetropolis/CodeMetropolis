# See if with -m the popup window appear

## Tags
#component_placing #testFile-format_XML #category_output

### Purpose
This test's objective is to verify that the placing tool with -m option a popup window appear

### Test File
- [[Expected Results/Mapping.Test7Result.xml]]

### Important
- To guarantee correct functionality, use your file paths throughout.  
- Ensure that the necessary Java version is used for each code you use!
- Always keep in mind that "buildable id" will vary.

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of placing-1.5.0.jar (path to the folder `CodeMetropolis\sources\distro\`)
- Test file inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Run
	```cmd
	java -jar placing-1.5.0.jar -i Mapping.Test7Result.xml -m
	```
	in placing-1.5.0.jar command line
2. Check if the pop up window appear

### Expected Result
The popup window "Map" and [[Expected Results/Placing.Test7ResultPng.png]] match.