# Test conversion with empty projects parameter 
## Tags
#component_converter #category_input #category_error_handling
### Purpose
This test confirms that the converter tool properly handles cases where the projects parameter is empty, by warning the user and exiting without processing.

### Pre-requisites

* Java runtime 22
* command line opened in the folder of converter-1.5.0.jar
* codemetropolis-toolchain-commons.graph SM output exists in the converter-1.5.0.jar's folder and is valid		

### Steps

 1. Run:
    ```cmd
    java -jar converter-1.5.0.jar -t sourcemeter -i codemetropolis-toolchain-commons.graph -p projects=
    ```

### Expected Result
The tool warns the user that the parameter value is missing and exits normally without creating the output file.