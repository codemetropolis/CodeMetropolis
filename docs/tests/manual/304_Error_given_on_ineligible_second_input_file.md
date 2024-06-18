# Check the solution committed to issue no. #304 if it returns an error when ineligible second input file is used with converter

## Tags
#pullRequest_304 #component_converter #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/304 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/304-use-parallelization-for-multiple-project-conversion

### Purpose
The purpose of this test is to see that the solution committed to issue no. #304 did not ruin the normal workings of the converter tool and thus an error is given back when trying to convert multiple files and one of them is not fit for conversion.

### Test File
- Graph file for the converter: [[codemetropolis-toolchain-commons-valid.graph]]
- Second file ineligible to convert: [[uneligible-to-convert.txt]]

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
	java -jar converter-1.5.0.jar -t sourcemeter -s codemetropolis-toolchain-commons.graph uneligilbe-to-convert.txt
	```

### Expected Result
An error is given back that one of the input files is ineligible for conversion.