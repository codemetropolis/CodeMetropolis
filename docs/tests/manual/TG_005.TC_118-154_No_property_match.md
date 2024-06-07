# Test no property match on pull request 154

## Tags
#pullRequest_154 #testFile-format_IXML #category_output #status_not_merged_functionality #category_error_handling

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/154 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/154-create-an-ixml-exclude--by-property--tool

### Purpose
To verify that the tool outputs an unchanged IXML file if no properties match the provided regex patterns.

### Pre-requisites
- An IXML file is prepared with properties that do not match any of the regex patterns provided for the test.

### Steps
1. Open a command-line interface.
2. Navigate to the folder containing the tool and the test IXML file.
3. Execute the command to run the tool with regex patterns that do not match any properties in the IXML file. Example command: 
```cmd
java -jar ixmlfiltering.jar -i input.ixml -o outputTest.ixml -pn "asd" -pv "123"
```
5. Verify the output file is generated after the command execution.

### Expected Result
The output IXML file is generated without errors. The output IXML file is identical to the input file, indicating no properties were excluded.
