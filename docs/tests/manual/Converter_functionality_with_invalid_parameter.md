# Test conversion with invalid parameter

## Tags
#component_converter #category_input #category_error_handling

### Purpose
This test assesses the converter tool's response to unknown parameters, ensuring it issues a warning about the unrecognized parameter and exits without generating output.

### Pre-requisites

* Java runtime 22
* command line opened in the folder of converter-1.5.0.jar
* codemetropolis-toolchain-commons.graph SM output exists in the converter-1.5.0.jar's folder and is valid

### Steps:

1. Run:
	```cmd
	java -jar converter-1.5.0.jar -t sourcemeter -i codemetropolis-toolchain-commons.graph -p a=b
	```

### Expected Result
The tool warns the user that the parameter name and value is unknown and exits normally without creating the output file.