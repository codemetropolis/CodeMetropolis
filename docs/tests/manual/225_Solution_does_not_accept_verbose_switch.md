# Check issue no. #225's solution when using -verbose or -VERBOSE switch reaction.

## Tags
#pullRequest_225 #component_converter #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/225 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/225-extending-the-sourcemeter-converter-with-verbose-mode

### Purpose
 The purpose of this test is to see if the tool can accept *-verbose* and *-VERBOSE* switches and runs as intended and after gives back detailed information

### Test File
- [[codemetropolis-toolchain-commons-valid.graph]]

### Pre-requisites	
- Java runtime 22
- Command line opened in the folder of converter-1.5.0.jar
- A *codemetropolis-toolchain-commons.graph* file exists in the converter-1.5.0.jar's folder. (Has to be a valid file)

**Steps**:
1. Run the following command in the folder of converter-1.5.0.jar:
	```cmd
	java -jar converter-1.5.0.jar -t sourcemeter -s codemetropolis-toolchain-commons-valid.graph -verbose
	``` 
	or
	```cmd 
	java -jar converter-1.5.0.jar -t sourcemeter -s codemetropolis-toolchain-commons-valid.graph -VERBOSE
	``` 
 
### Expected Result
The tool gives back warnings or errors and the program will not finish running, because the -VERBOSE or -verbose switches are not the same as the -v switch.