# The solution committed to issue no. #304 accepts multiple projects when running the converter tool.

## Tags
#pullRequest_304 #component_converter #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/304 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/304-use-parallelization-for-multiple-project-conversion

### Purpose
 The purpose of this test is to see if the solution committed to issue no. #304 will accept more files as input and converts them according to the purpose of the *Converter* tool. This test is to check if the provided solution won't prevent multiple file acceptance

### Test File
- Graph file for the converter: [[codemetropolis-toolchain-commons-valid.graph]]
- Second graph file for the converter to test multi thread process: [[codemetropolis-toolchain-rendering-valid-test.graph]]

### Pre-requisites	
- Java runtime 22

### Steps
1. Navigate to the issue's branch using command line with 
   ```cmd
   git checkout 304-use-parallelization-for-multiple-project-conversion
   ```
2. Insert the test files into the converter-1.5.0.jar's folder
3. Open a command line in the folder of converter-1.5.0.jar
4. Run the following command in the folder of converter-1.5.0.jar:
   ```cmd
   java -jar converter-1.5.0.jar -t sourcemeter -s codemetropolis-toolchain-commons.graph codemetropolis-toolchain-rendering.graph
   ```

### Expected Result
The program accepts multiple files and then creates multiple output files according to the number of input files.
