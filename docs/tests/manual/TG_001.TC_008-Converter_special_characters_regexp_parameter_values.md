# Test special characters in regexp parameter values

## Tags
#component_converter #testFile-format_graph #category_input #category_correct_working

### Purpose
This test assesses the converter tool's handling of special characters in regular expression parameter values, ensuring it warns the user about invalid characters in such parameters.

### Pre-requisites

*Java runtime 22
*command line opened in the folder of converter-1.5.0.jar
*codemetropolis-toolchain-commons.graph SM output exists in the converter-1.5.0.jar's folder and is valid	

### Steps

1. Run
	```cmd 
	java -jar converter-1.5.0.jar -t sourcemeter -s codemetropolis-toolchain-commons.graph -p projects=^a.*=, 
	```

### Expected Result
The tool warns the user that the projects value with a regular expression cannot contain special characters like "=" or ",".

