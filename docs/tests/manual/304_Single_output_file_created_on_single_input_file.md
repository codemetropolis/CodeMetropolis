# Check the solution committed to issue no. #304 if it creates a single output file when converter is used with a single input file

## Tags
#pullRequest_304 #component_converter #category_output

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/304 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/304-use-parallelization-for-multiple-project-conversion

### Purpose
The purpose of this test is to see that the solution committed to issue no. #304 will only create a single output file when converter is run with a single input file as argument.

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
   ```cmd
   java -jar converter-1.5.0.jar -t sourcemeter -s codemetropolis-toolchain-commons.graph
   ```

### Expected Result
The program runs and a single output file is created in the sources folder when it is used with a single input file.