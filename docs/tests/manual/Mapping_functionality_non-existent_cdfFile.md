# Test mapping with non-existent cdfFile

## Tags
#component_mapping #testFile-format_XML  #category_input #category_correct_working

### Purpose
This test checks the mapping tool's response to a non-existent cdf file, ensuring it warns the user and exits without attempting to process the missing file.

### Pre-requisites
* Java runtime 22
* missing.xml should not exist
* sourcemeter_mapping_example_2_1.xml mapping description exists in the mapping-1.5.0.jar's folder and is valid
* command line opened in the folder of mapping-1.5.0.jar

### Steps
1. Run:
	```cmd 
	java -jar mapping-1.5.0.jar -i missing.xml -m sourcemeter_mapping_example_2_1.xml 
	```

### Expected result
The tool warns the user that the input cdfFile does not exist and exits normally without creating the output file.

