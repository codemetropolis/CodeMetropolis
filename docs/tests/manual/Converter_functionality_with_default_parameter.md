# Test conversion with default output parameter
## Tags
#component_converter #testFile-format_graph #category_input #category_correct_working

### Purpose
This test confirms that the converter tool operates correctly using default parameters, validating its functionality under standard conditions without specific user inputs.

### Pre-requisites
* Java runtime 22
* command line opened in the folder of converter-1.5.0.jar	
* codemetropolis-toolchain-commons.graph SM output exists in the converter-1.5.0.jar's folder and is valid

### Steps
1. Run:
   ```cmd
    java -jar converter-1.5.0.jar -t sourcemeter -i codemetropolis-toolchain-commons.graph 
   ```

### Expected result
The tool finishes without any errors and produces a valid xml file called "converterToMapping.xml" in the converter-1.5.0.jar's folder.