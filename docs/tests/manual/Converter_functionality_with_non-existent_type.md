# Test conversion with non-existent type

## Tags
#component_converter #testFile-format_graph #category_input #category_correct_working

### Purpose
This test evaluates the converter tool's response when a non-existent conversion type is specified, ensuring it warns the user and exits without attempting to process.

### Pre-requisites
* Java runtime 22
* command line opened in the folder of converter-1.5.0.jar	
* codemetropolis-toolchain-commons.graph SM output exists in the converter-1.5.0.jar's folder and is valid

### Steps
1. Run:
	```cmd
	java -jar converter-1.5.0.jar -t none -i codemetropolis-toolchain-commons.graph
	``` 

### Expected Result
The tool warns the user that the conversion type does not exist and exits normally without creating the output file.
