# Check the solution committed to issue no. #304 if an error is given back on converter run without input files

## Tags
#pullRequest_304 #component_converter #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/304 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/304-use-parallelization-for-multiple-project-conversion

### Purpose
The purpose of this test is to check that the solution committed to issue no. #304 will give back an error when trying to run the converter tool without input file(s) as it normally would.

### Pre-requisites
- Java runtime 22

### Steps
1. Navigate to the issue's branch using command line with 
   ```cmd
   git checkout 304-use-parallelization-for-multiple-project-conversion
   ```
2. Insert the test files into the converter-1.5.0.jar's folder
3. Run the following command in the folder of converter-1.5.0.jar:
   ```cmd
   java -jar converter-1.5.0.jar -t sourcemeter -s
   ```

### Expected Result
An error is given back when trying to run the converter tool without input files.