# Check issue no. #225's solution if it provides a start time in a correct format when using -v switch.

## Tags
#pullRequest_225 #component_converter #category_output

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/225 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/225-extending-the-sourcemeter-converter-with-verbose-mode

### Purpose
 The purpose of this test is to check the commited solution to issue no. #225 provides a start time in a correct format (hh:mm:ss), when the program starts running.

### Test File
- [[codemetropolis-toolchain-commons-valid.graph]]

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of converter-1.5.0.jar
- A *codemetropolis-toolchain-commons-valid.graph* file exists in the converter-1.5.0.jar's folder.

### Steps
1. Run the following command in the folder of converter-1.5.0.jar:
	```cmd
	java -jar converter-1.5.0.jar -t sourcemeter -s codemetropolis-toolchain-commons-valid.graph -v
	```

### Expected Result
The messages that appear in the console contain the starting time for running the program in a correct format.