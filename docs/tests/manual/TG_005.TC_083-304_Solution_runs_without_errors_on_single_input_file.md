# Check the solution committed to issue no. #304 if it runs without errors when single file conversion is used

## Tags
#pullRequest_304 #component_converter #category_output

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/304 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/304-use-parallelization-for-multiple-project-conversion

### Purpose
 The purpose of this test is to see that the solution committed to issue no. #304 will not return errors when single file conversion is used with the converter tool.

### Test File
- Graph file for the converter: [[codemetropolis-toolchain-commons-valid.graph]]

### Pre-requisites		
- Java runtime 22

### Steps
1. Navigate to the issue's branch using command line with 
   ```cmd
   git checkout 304-use-parallelization-for-multiple-project-conversion
   ```
2. Insert the test file into the converter-1.5.0.jar's folder
3. Run the following command in the folder of converter-1.5.0.jar:
   ```
   java -jar converter-1.5.0.jar -t sourcemeter -s codemetropolis-toolchain-commons.graph
   ```

### Expected Result
The program runs and no error is given back when the converter tool is used with a single file.