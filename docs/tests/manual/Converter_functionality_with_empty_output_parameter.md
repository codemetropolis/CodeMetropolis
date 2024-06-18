# Test conversion with empty output parameter 

## Tags
#component_converter #testFile-format_graph #category_output #category_correct_working

### Purpose
This test verifies the converter tool's behavior when the output file parameter is empty. It ensures that the tool issues a warning about the missing output file parameter and exits without creating an output file.

### Pre-requisites

* Java runtime 22
* command line opened in the folder of converter-1.5.0.jar
* codemetropolis-toolchain-commons.graph SM output exists in the converter-1.5.0.jar's folder and is valid	

### Steps
		
1. Run:
	```cmd
	java -jar converter-1.5.0.jar -t sourcemeter -i codemetropolis-toolchain-commons.graph -o
	```

### Expected Result
The tool warns the user that the output file parameter value is missing and exits normally without creating the output file.