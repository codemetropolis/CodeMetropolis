# The solution committed to issue no. #304 will speed up the converting process when multiple files given to converter.jar.

## Tags
#pullRequest_304 #component_converter

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/304 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/304-use-parallelization-for-multiple-project-conversion

### Purpose
 The purpose of this test is to see if the solution committed to issue no. #304 will speed up the process for project conversion in the converter file.

### Test File
- Graph file for the converter: [[codemetropolis-toolchain-commons-valid.graph]]
- Second graph file for the converter to test multi thread process: [[codemetropolis-toolchain-rendering-valid-test.graph]]

### Pre-requisites	
- Java runtime 22

### Steps
1. Navigate to git develop branch using command line with `git checkout develop`
2. Insert the test files into the converter-1.5.0.jar's folder
3. Open a command line in the folder of converter-1.5.0.jar
4. Run the following command in the folder of converter-1.5.0.jar:
   ```cmd
   java -jar converter-1.5.0.jar -t sourcemeter -s codemetropolis-toolchain-commons.graph codemetropolis-toolchain-commons-valid.graph
   ``` 
5. Navigate to the issue's branch using command line with `git checkout 304-use-parallelization-for-multiple-project-conversion`
6.  Insert the test files into the converter-1.5.0.jar's folder
7. Open a command line in the folder of converter-1.5.0.jar
8. Run the following command in the folder of converter-1.5.0.jar:
   ```cmd
   java -jar converter-1.5.0.jar -t sourcemeter -s codemetropolis-toolchain-commons.graph codemetropolis-toolchain-rendering-valid-test.graph
   ``` 

### Expected Result
The process is sped up on the issue's branch compared to the run on the main branch.
