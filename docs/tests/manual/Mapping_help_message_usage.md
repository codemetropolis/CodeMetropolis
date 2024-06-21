# Test usage help message of mapping 

## Tags
#component_mapping #category_correct_working

### Purpose
This test ensures that the mapping tool offers a help message detailing proper command-line parameters when the help option is triggered, aiding users in correct tool operation.

### Pre-requisites
* Java runtime 22
* command line opened in the folder of mapping-1.5.0.jar					

### Steps
1. Run:
	```cmd
	java -jar mapping-1.5.0.jar -h
	```

### Expected result
The tool provides a list of proper command line parameters and their possible values to be used: -h, -i, -o, -m, -s, and -v.