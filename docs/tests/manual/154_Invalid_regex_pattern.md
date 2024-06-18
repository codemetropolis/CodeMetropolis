# Test invalid regex pattern on pull request 154

## Tags
#pullRequest_154 #testFile-format_IXML #category_output #status_not_merged_functionality #category_error_handling

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/154 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/154-create-an-ixml-exclude--by-property--tool

### Purpose
To evaluate the tool's robustness and error handling capabilities when confronted with invalid regex patterns. This test specifically aims to verify that the tool provides meaningful feedback or error messages, preventing potential crashes or unexpected behavior. Ensuring the tool can gracefully handle such input is crucial for maintaining usability and reliability, especially in scenarios where users might inadvertently provide incorrect regex syntax.

### Pre-requisites
- An IXML file is available for processing.

### Steps
1. Open a command-line interface.
2. Navigate to the folder containing the tool and the IXML file.
3. Execute the tool with an invalid regex pattern for either the property name or value. Example command: 
	```cmd 
	java -jar ixmlfiltering.jar -i input.ixml -o outputTest.ixml -pn "*" -pv "*(asd)"
	```
4. Observe the tool's response to the invalid pattern.

### Expected Result
The tool reports an error regarding the invalid regex pattern.
