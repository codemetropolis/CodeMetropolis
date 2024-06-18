# Test mapping with non-existent mapping file

## Tags
#component_mapping #testFile-format_XML  #category_input #category_error_handling

### Purpose
This test assesses the mapping tool's behavior when provided with a non-existent mapping file, confirming it alerts the user about the absence and exits without processing.

### Pre-requisites
* Java runtime 22
* converterToMapping.xml exists in the mapping-1.5.0.jar's folder and is a valid cdfFile
* missing.xml should not exist
* command line opened in the folder of mapping-1.5.0.jar

### Steps
1. Run:
   ```cmd
   java -jar mapping-1.5.0.jar -i converterToMapping.xml -m missing.xml 
   ```

### Expected result
The tool warns the user that the mapping file does not exist and exits normally without creating the output file.