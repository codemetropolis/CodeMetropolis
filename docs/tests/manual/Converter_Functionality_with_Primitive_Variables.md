# Test converter on project which only has variables

## Tags
#component_converter_SourceMeter #testFile-format_graph #category_output

### Purpose
This test's goal is to verify that the converter operates as intended in situations where the project only contains primitive variables.

### Test Project
- A freshly created Maven project with some variables.
- [[Test Files/Converter.Test3.graph]]

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
	java -jar converter-1.5.0.jar -t sourcemeter -s Converter.Test3.graph
	```
	in converter-1.5.0.jar command line
2. Compare the resulting file with the [[Expected Results/Converter.Test3Result.xml]] file

### Expected Result
The [[Expected Results/Converter.Test3Result.xml]] file and the generated file match.