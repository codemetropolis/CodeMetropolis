# Test conversion with invalid graph file

## Tags
#component_converter #testFile-format_graph #category_input #category_correct_working 

### Purpose
This test checks how the converter tool reacts to an invalid graph file, ensuring it warns the user about the file's format and terminates without producing output.

### Pre-requisites
* Java runtime 22
* command line opened in the folder of converter-1.5.0.jar	
* fake.graph is an invalid graph file (e.g. a simple text file with random characters) in the converter-1.5.0.jar's folder

### Steps
1. Run:
	```cmd
	java -jar converter-1.5.0.jar -t sourcemeter -i fake.graph
	``` 

### Expected Result
The tool warns the user that the input graph has an invalid format and exits normally without creating the output file.
