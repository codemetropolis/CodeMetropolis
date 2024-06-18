# Test placing without any parameters

## Tags
#component_placing #testFile-format_IXML #category_input #category_error_handling

### Purpose
This test verifies the placing tool's response when executed without any parameters, confirming that it provides an informative help message on usage.

### Pre-requisites
* Java runtime 22
* command line opened in the folder of placing-1.5.0.jar		

### Steps
1. Run: 
	```cmd
	java -jar placing-1.5.0.jar
	```

### Expected result
The tool prints out an informative help message with the usage of the parameters.