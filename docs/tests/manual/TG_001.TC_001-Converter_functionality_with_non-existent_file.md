# Test conversion with non-existent file

## Tags
#component_converter #testFile-format_graph #category_input #category_correct_working

### Purpose
This test ensures that the converter tool correctly handles a non-existent input file by warning the user and terminating without output, validating the tool's error management under missing file conditions.

### Pre-requisites
* Java runtime 22
* a.graph should not exist
* command line opened in the folder of converter-1.5.0.jar

### Steps
1. Run:
	```cmd
	java -jar converter-1.5.0.jar -t sourcemeter -i a.graph
	```

### Expected Result
The tool warns the user that the input graph file does not exist and exits normally without creating the output file.
