# Test usage help message of converter

## Tags
#component_converter #testFile-format_graph #category_input #category_correct_working

### Purpose
This test ensures that the converter tool provides a help message on its usage when prompted. It checks the tool's ability to instruct users on correct operation through command-line help output.

### Pre-requisites
* Java runtime 22
* command line opened in the folder of converter-1.5.0.jar						
### Steps

1. Run:
	```cmd
	java -jar converter-1.5.0.jar 
	```

### Expected Result
The tool provides a usage help with a list of proper command line parameters and their valid values to be used: -i, -t, -o, and -p.