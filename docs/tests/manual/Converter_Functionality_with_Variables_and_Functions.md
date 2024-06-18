# Test converter on project which has variables and functions

## Tags
#component_converter_SourceMeter #testFile-format_graph #category_output

### Purpose
This test's objective is to verify that the converter operates correctly in the presence of both variables and functions.

### Test Project
- A freshly created Maven project to which we have added some function and variable.
- [[Test Files/Converter.Test6.graph]]

### Important
- To guarantee correct functionality, use your file paths throughout.  
- Ensure that the necessary Java version is used for each code you use!

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of converter-1.5.0.jar (path to the folder `CodeMetropolis\sources\distro\`)
- Test file inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1.  Run
	```cmd
	java -jar converter-1.5.0.jar -t sourcemeter -s Converter.Test6.graph
	```
	in converter-1.5.0.jar command line
2. Compare the resulting file with the [[Expected Results/Converter.Test6Result.xml]] file

### Expected Result
The [[Expected Results/Converter.Test6Result.xml]] file and the generated file match.