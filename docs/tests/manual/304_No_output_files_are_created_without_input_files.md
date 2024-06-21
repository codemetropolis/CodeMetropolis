# Check the solution committed to issue no. #304 if output files were created when no input files given

## Tags
#pullRequest_304 #component_converter #category_output

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/304 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/304-use-parallelization-for-multiple-project-conversion

### Purpose
The purpose of this test is to see that the solution committed to issue no. #304 will not create any kind of output file when no input file is given to converter.

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
4. Check `Codemetropolis/sources` folder

### Expected Result
No output xml files are created when the converter is used without any input files.