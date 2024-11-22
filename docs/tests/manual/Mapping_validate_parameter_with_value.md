# Test mapping with validate parameter with value

## Tags
#component_mapping #testFile-format_XML  #category_input #category_correct_working

### Purpose
This test verifies the mapping tool's functionality in validating parameters with values, confirming that it correctly identifies and rejects unexpected values for parameters designed to be value-less.

### Pre-requisites
* Java runtime 22
* converterToMapping.xml exists in the mapping-1.5.0.jar's folder and is a valid cdfFile (produced by converter-1.5.0.jar with the input codemetropolis-toolchain-commons.graph)
* sourcemeter_mapping_example_2_1.xml mapping description exists in the mapping-1.5.0.jar's folder and is valid
* command line opened in the folder of mapping-1.5.0.jar

### Steps
1. Run:
	```cmd
	java -jar mapping-1.5.0.jar -i converterToMapping.xml -m sourcemeter_mapping_example_2_1.xml -v true
	``` 

### Expected result
The tool warns the user that the validate parameter does not take a value and exits normally without creating the output file.