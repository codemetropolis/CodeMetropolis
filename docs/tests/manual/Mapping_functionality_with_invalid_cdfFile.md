# Test mapping with invalid cdfFile

## Tags
#component_mapping #testFile-format_XML  #category_input #category_error_handling

### Purpose
This test verifies how the mapping tool reacts to an invalid cdf file, checking that it notifies the user of the file's invalid content and exits without generating output.

### Pre-requisites
* Java runtime 22
* converterToMapping.xml exists in the mapping-1.5.0.jar's folder and is a valid cdfFile (produced by converter-1.5.0.jar with the input codemetropolis-toolchain-commons.graph)
* sourcemeter_mapping_example_2_1.xml mapping description exists in the mapping-1.5.0.jar's folder and is valid
* command line opened in the folder of mapping-1.5.0.jar

### Steps
1. Open converterToMapping.xml with a text editor
2. Delete content from line 10 to 20 and save the file
3. Run:
	```cmd
	java -jar mapping-1.5.0.jar -i converterToMapping.xml -m sourcemeter_mapping_example_2_1.xml 
	```

### Expected result
The tool warns the user that the input cdfFile content is invalid and exits normally without creating the output file.