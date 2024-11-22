# Test converter on project which only has collections

## Tags
#component_converter_SourceMeter #testFile-format_graph #category_output

### Purpose
This test's objective is to verify that the converter operates correctly when the project only contains collections.

### Test Project
- A recently created Maven project, for which we have some collection added.
- [[Test Files/Converter.Test4.graph]]

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
	java -jar converter-1.5.0.jar -t sourcemeter -s Converter.Test4.graph
	```
	in converter-1.5.0.jar command line
2. Compare the resulting file with the [[Expected Results/Converter.Test4Result.xml]] file

### Expected Result
The [[Expected Results/Converter.Test4Result.xml]] file and the generated file match.