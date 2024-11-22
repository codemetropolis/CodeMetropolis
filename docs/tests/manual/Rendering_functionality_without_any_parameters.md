# Test rendering without any parameters

## Tags
#component_rendering #testFile-format_IXML #category_input #category_error_handling

### Purpose
This test verifies the rendering tool's behavior when executed without any parameters, confirming it provides an informative help message detailing proper command-line usage.

### Pre-requisites
* Java runtime 22
* command line opened in the folder of rendering-1.5.0.jar		

### Steps
1. Run:
	```cmd
	java -jar rendering-1.5.0.jar
	```

### Expected result:
The tool prints out an informative help message with the usage of the parameters.