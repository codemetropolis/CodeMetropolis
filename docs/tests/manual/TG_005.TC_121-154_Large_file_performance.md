# Test the performance of pull request 154 on large file

## Tags
#pullRequest_154 #testFile-format_IXML #category_input #status_not_merged_functionality #category_performance

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/154 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/154-create-an-ixml-exclude--by-property--tool

### Purpose
To assess the performance and efficiency of the tool when processing a large IXML file. This test is crucial for understanding how the tool scales with input size and ensures it can handle real-world data sets without significant degradation in performance or stability. Ensuring the tool performs reliably on large files is vital for its applicability in extensive projects where large datasets are common.

### Pre-requisites
- A large IXML file (e.g., several thousands of lines) is available for testing.

### Steps
1. Open a command-line interface.
2. Navigate to the folder containing the tool and the large IXML file.
3. Execute the tool with the large IXML file as input. Example command: 
```cmd
java -jar ixmlfiltering.jar -i largeInputTest.ixml -o outputTest.ixml
```
4. Monitor the tool's performance, noting any significant delays or issues encountered during processing.

### Expected Result
The tool completes the processing of the large IXML file within a reasonable timeframe. The tool does not crash or become unresponsive during the processing of the large file.
