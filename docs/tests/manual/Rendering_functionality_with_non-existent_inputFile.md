# Test rendering with non-existent inputFile

## Tags
#component_rendering #testFile-format_IXML #category_input #category_error_handling

### Purpose
This test checks the rendering tool's response to a non-existent input file, validating that it warns the user about the missing file and exits without attempting to process.

### Pre-requisites
* Java runtime 22
* missing.xml should not exist
* command line opened in the folder of rendering-1.5.0.jar

### Steps
1. Run:
	```cmd
	java -jar rendering-1.5.0.jar -i missing.xml
	```

### Expected result
The tool warns the user that the input file does not exist and exits normally without creating the output file.