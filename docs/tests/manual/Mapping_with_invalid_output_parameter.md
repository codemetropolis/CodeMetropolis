# Test mapping with invalid output parameter

## Tags
#component_mapping #testFile-format_XML  #category_output  #category_error_handling

### Purpose
This test evaluates the mapping tool's response to an invalid output file parameter, ensuring it alerts the user to the incorrect file path or device and exits without producing output.

### Pre-requisites
* Windows operating system where drive "W" does not exist
* Java runtime 22
* converterToMapping.xml exists in the mapping-1.5.0.jar's folder and is a valid cdfFile (produced by converter-1.5.0.jar with the input codemetropolis-toolchain-commons.graph)
* sourcemeter_mapping_example_2_1.xml mapping description exists in the mapping-1.5.0.jar's folder and is valid
* command line opened in the folder of mapping-1.5.0.jar

### Steps
1. Run:
	```cmd
	java -jar mapping-1.5.0.jar -i converterToMapping.xml -m sourcemeter_mapping_example_2_1.xml -o W:/out.xml
	```

### Expected result
The tool warns the user that the output file parameter value is invalid and exits normally without creating the output file.