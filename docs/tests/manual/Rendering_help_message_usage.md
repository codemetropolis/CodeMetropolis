# Test usage help message of rendering

## Tags
#component_rendering #category_correct_working 

### Purpose
This test checks the rendering tool's capability to provide a help message detailing proper command-line usage when executed without parameters, aiding users in correct operation.

### Pre-requisites
* Java runtime 22
* command line opened in the folder of rendering-1.5.0.jar					

### Steps
1. Run:
	```cmd
	java -jar rendering-1.5.0.jar -h
	```

### Expected result:
The tool provides a list of proper command line parameters and their possible values to be used: -h, -i, -w, and -s.


