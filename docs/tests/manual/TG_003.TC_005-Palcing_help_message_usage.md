# Test usage help message of placing 

## Tags
#component_placing #category_correct_working 

### Purpose
This test checks the placing tool's ability to offer a help message detailing proper command-line parameters when prompted, assisting users in correct tool operation.

### Pre-requisites
* Java runtime 22
* command line opened in the folder of placing-1.5.0.jar					

### Steps

1. Run:
   ```cmd
   java -jar placing-1.5.0.jar -h
   ```

### Expected result
The tool provides a list of proper command line parameters and their possible values to be used: -h, -i, -o, -l, and -m.