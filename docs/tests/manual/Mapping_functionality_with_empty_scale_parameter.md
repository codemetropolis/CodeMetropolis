# Test mapping with empty scale parameter

## Tags
#component_mapping #testFile-format_XML  #category_input #category_error_handling

### Purpose
This test evaluates the mapping tool's handling of an empty scale parameter, checking that it notifies the user about the incomplete parameter entry and terminates without processing.

### Pre-requisites
* Java runtime 22
* converterToMapping.xml exists in the mapping-1.5.0.jar's folder and is a valid cdfFile (produced by converter-1.5.0.jar with the input codemetropolis-toolchain-commons.graph)
* sourcemeter_mapping_example_2_1.xml mapping description exists in the mapping-1.5.0.jar's folder and is valid
* command line opened in the folder of mapping-1.5.0.jar

### Steps
1. Run:
	```cmd 
	java -jar mapping-1.5.0.jar -i converterToMapping.xml -m sourcemeter_mapping_example_2_1.xml -s 
	```

### Expected result
The tool warns the user that the scale parameter value is missing and exits normally without creating the output file.