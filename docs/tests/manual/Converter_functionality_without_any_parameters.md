# Test conversion without any parameters 

## Tags
#component_converter #category_input #category_correct_working

### Purpose
This test checks if the converter tool provides an informative help message when executed without any parameters. This validates the tool's response to insufficient command-line inputs.

### Pre-requisites

* Java runtime 22
* command line opened in the folder of converter-1.5.0.jar

### Steps

1. Run:
	```cmd
	java -jar converter-1.5.0.jar
	```

### Expected Result
The tool prints out an informative help message with the usage of the parameters.