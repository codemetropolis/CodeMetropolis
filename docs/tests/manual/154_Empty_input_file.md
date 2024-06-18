# Test output on pull request 154 with empty input file

## Tags
#pullRequest_154 #testFile-format_IXML #category_output #status_not_merged_functionality #category_correct_working 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/154 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/154-create-an-ixml-exclude--by-property--tool

### Purpose
To validate the tool's behavior and output when provided with an empty IXML file. This test aims to ensure that the tool can handle empty input gracefully, either by outputting an empty IXML file or by providing a meaningful message to the user. Handling empty input files correctly is important for maintaining the tool's robustness and ensuring reliability across a range of input scenarios.

### Pre-requisites
- An empty IXML file is prepared and available for testing.

### Steps
1. Open a command-line interface.
2. Navigate to the folder containing the tool and the empty IXML file.
3. Execute the tool with the empty IXML file as input. Example command: 
```cmd
java -jar ixmlexclude.jar -i emptyTest.ixml -o outputTest.ixml
```
4. Observe and record the tool's output or response to the empty input.

### Expected Result
The tool either outputs an empty IXML file or provides a clear message indicating the input was empty.
