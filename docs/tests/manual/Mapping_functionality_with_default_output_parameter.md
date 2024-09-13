# Test mapping with default output parameter

## Tags
#component_mapping #testFile-format_XML  #category_output #category_correct_working

### Purpose
This test evaluates the mapping tool's capability to function with a default output parameter, ensuring it correctly generates a valid output file without user-specified output settings.

### Pre-requisites
* Java runtime 22
* converterToMapping.xml exists in the mapping-1.5.0.jar's folder and is a valid cdfFile (produced by converter-1.5.0.jar with the input codemetropolis-toolchain-commons.graph)
* sourcemeter_mapping_example_2_1.xml mapping description exists in the mapping-1.5.0.jar's folder and is valid
* command line opened in the folder of mapping-1.5.0.jar

### Steps
1. Run:
	```cmd
	java -jar mapping-1.5.0.jar -i converterToMapping.xml -m sourcemeter_mapping_example_2_1.xml
	``` 

### Expected result
The tool finishes without any errors and produces a valid xml file called "mappingToPlacing.xml" in the mapping-1.5.0.jar's folder.