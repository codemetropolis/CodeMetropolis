# Test basic functionality of pull request 154

## Tags
#pullRequest_154 #testFile-format_IXML #category_output #status_not_merged_functionality #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/154 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/154-create-an-ixml-exclude--by-property--tool

### Purpose
The purpose of this test case is to verify that the command-line tool introduced in Issue #154 correctly implements its core functionality. This involves reading an input IXML file, applying regex-based filters to exclude items based on property names and values, and generating a new, filtered IXML file. This test ensures the tool's basic operational efficacy, specifically its ability to filter out defined items, thereby supporting more focused visualization efforts.

### Pre-requisites
- An IXML file with known properties that can be targeted with regex patterns is prepared.

### Steps
1. Open a command-line interface.
2. Navigate to the folder where the tool and the IXML file are located.
3. Execute the tool with arguments specifying the input IXML file, the output file name, and the regex patterns for property name and value. Example command: 
	```cmd 
	java -jar ixmlfiltering.jar -i input.ixml -o outputTest.ixml -pn "buildable" -pv "a*"
	```
4. Wait for the process to complete and verify the output.

### Expected Result
The tool runs without any errors and generates an output IXML file. The output IXML file does not include any items with properties that match the regex patterns provided.