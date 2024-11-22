# Test conversion with invalid output parameter

## Tags
#component_converter #testFile-format_graph #category_output #category_error_handling 

### Purpose
This test aims to confirm that the converter tool properly handles invalid output file parameters by warning the user and exiting without performing any actions.

### Pre-requisites
* Windows operating system where drive "W" does not exist
* Java runtime 22
* command line opened in the folder of converter-1.5.0.jar
* codemetropolis-toolchain-commons.graph SM output exists in the converter-1.5.0.jar's folder and is valid

### Steps
1. Run:
	```cmd
	java -jar converter-1.5.0.jar -t sourcemeter -i codemetropolis-toolchain-commons.graph -o W:/out.xml 
	```

### Expected Result
The tool warns the user that the output file parameter value is invalid and exits normally without creating the output file.