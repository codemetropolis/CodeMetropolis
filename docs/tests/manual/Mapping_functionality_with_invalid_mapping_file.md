# Test mapping with invalid mapping file

## Tags
#component_mapping #testFile-format_XML  #category_input #category_error_handling

### Purpose
This test evaluates the mapping tool's response to an invalid mapping file, ensuring it alerts the user about the file's incorrect format and exits without proceeding with processing.

### Pre-requisites
* Java runtime 22
* converterToMapping.xml exists in the mapping-1.5.0.jar's folder and is a valid cdfFile (produced by converter-1.5.0.jar with the input codemetropolis-toolchain-commons.graph)
* sourcemeter_mapping_example_2_1.xml mapping description exists in the mapping-1.5.0.jar's folder and is valid
* command line opened in the folder of mapping-1.5.0.jar

### Steps
1. Open sourcemeter_mapping_example_2_1.xml with a text editor
2. Delete content from line 10 to 20 and save the file
3. Run:
   ```cmd
   java -jar mapping-1.5.0.jar -i converterToMapping.xml -m sourcemeter_mapping_example_2_1.xml 
   ```

### Expected result 
The tool warns the user that the input mapping file content is invalid and exits normally without creating the output file.