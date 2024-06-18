# Test no. 154 pull request's validation of command line arguments

## Tags
#pullRequest_154 #testFile-format_IXML #category_input #status_not_merged_functionality #category_error_handling 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/154 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/154-create-an-ixml-exclude--by-property--tool

### Purpose
To verify that the tool correctly handles and validates command-line arguments. This test checks for the tool's response to missing, extra, or incorrectly formatted command-line arguments, ensuring robust error handling and user guidance. Proper validation is key to preventing execution errors and guiding users in the correct use of the tool.

### Pre-requisites
- An IXML file with known properties that can be targeted with regex patterns is prepared.

### Steps
1. Open a command-line interface.
2. Navigate to the folder containing the tool.
3. Execute the tool with various combinations of incorrect or incomplete command-line arguments. 
	```cmd 
	java -jar ixmlfiltering.jar -o outputTest.ixml -pn "buildable" -pv "*"
	```
	and
	```cmd 
	java -jar ixmlfiltering.jar -i input.ixml -o outputTest.ixml -pn "buildable" -pv "*" -t type
	```
	and
	```cmd 
	java -jar ixmlfiltering.jar -i input.ixml -o outputTest.ixml -pn buildable -pv *
	```
4. Observe and note the tool's response to each type of incorrect argument.

### Expected Result
The tool provides clear, understandable error messages or usage guidance when command-line arguments are missing, extra, or incorrectly formatted. The tool does not proceed with execution when the command-line arguments are invalid, preventing potential misuse or confusion.
