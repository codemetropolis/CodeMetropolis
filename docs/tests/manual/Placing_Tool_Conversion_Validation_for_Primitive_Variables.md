# See if the placing tool converts the TG_005.TC_011-Mapping expected result xml successfully.

## Tags
#component_placing #testFile-format_XML #category_output

### Purpose
This test's objective is to verify that the placing tool converted the TG_005.TC_011-Mapping expected result xml appropriately.

### Test Project
- A freshly created Maven project, to which we added just primitive variables and used the converter and mapping tool to convert it.
- [[Expected Results/Mapping.Test3Result.xml]]

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
	java -jar placing-1.5.0.jar -i Mapping.Test3Result.xml
	```
	in placing-1.5.0.jar command line
2. Compare the resulting file with the xml [[Expected Results/Placing.Test3Result.xml]] file

### Expected Result
The [[Expected Results/Placing.Test3Result.xml]] file and the generated file match expect for the buildable ids as they vary.