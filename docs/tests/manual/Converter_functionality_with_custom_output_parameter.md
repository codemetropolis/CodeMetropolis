# Test conversion with custom output parameter

### Purpose
This test verifies the converter tool's functionality with a custom output parameter, ensuring it processes and generates files correctly according to user-specified settings.

## Tags
#component_converter #testFile-format_graph #category_output #category_correct_working

### Pre-requisites
* Java runtime 22
* command line opened in the folder of converter-1.5.0.jar	
* codemetropolis-toolchain-commons.graph SM output exists in the converter-1.5.0.jar's folder and is valid

### Steps
1. Run: 
   ```cmd
    java -jar converter-1.5.0.jar -t sourcemeter -i codemetropolis-toolchain-commons.graph -o myMapping.xml 
   ```

### Expected result
The tool finishes without any errors and produces a valid xml file called "myMapping.xml" in the converter-1.5.0.jar's folder.