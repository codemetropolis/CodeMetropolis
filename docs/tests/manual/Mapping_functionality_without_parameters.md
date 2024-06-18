# Test mapping without any parameters

## Tags
#component_mapping #testFile-format_XML  #category_input #category_error_handling

### Purpose
This test verifies that the mapping tool provides an informative help message when executed without any parameters, confirming its response to inadequate command-line inputs.

### Pre-requisites
* Java runtime 1. 8
* command line opened in the folder of mapping-1.5.0.jar		

### Steps
1. Run:
	```cmd
	java -jar mapping-1.5.0.jar
	```
   
### Expected result
The tool prints out an informative help message with the usage of the parameters.