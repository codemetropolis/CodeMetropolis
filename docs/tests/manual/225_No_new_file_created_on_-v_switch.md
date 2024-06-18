# Check issue no. #225's solution that no new file is created when running converter in verbose mode

## Tags
#pullRequest_225 #component_converter #category_output

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/225 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/225-extending-the-sourcemeter-converter-with-verbose-mode

### Purpose
The purpose of this test is to check if the provided messages that appear in the console (when running the solution to issue no. #225) won't create any new files related to the verbose mode and the only file created by the tool will be the output file something similar to `converterToMapping`, which is a file used by the mapping tool.

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
2. Check the folder of the converter tool if any new file appeared
(path to the tool: `CodeMetropolis\sources\toolchain\converter`) 

### Expected Result
No new file is created after running the converter tool in verbose related to this mode and only the tool's default output file is created.