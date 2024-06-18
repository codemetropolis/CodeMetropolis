# Test placing with non-existent inputFile

## Tags
#component_placing #testFile-format_IXML #category_input #category_error_handling

### Purpose
This test assesses the placing tool's reaction to a non-existent input file, ensuring it warns the user about the file's absence and exits without attempting to process.

### Pre-requisites
* Java runtime 22
* missing.xml should not exist
* command line opened in the folder of placing-1.5.0.jar

### Steps
1. Run:
	```cmd
	java -jar placing-1.5.0.jar -i missing.xml
	```

### Expected result
The tool warns the user that the input file does not exist and exits normally without creating the output file.