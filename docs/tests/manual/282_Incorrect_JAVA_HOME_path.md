# Test no. 282 pull request's solution on incorrect JAVA_HOME path

## Tags
#pullRequest_282 #status_not_merged_functionality #category_error_handling   #component_mapping #component_converter #component_placing #component_rendering

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/282 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/282-there-should-be-a-background-check-on-the-java-version-before-the-world-can-be-generated

### Purpose
To assess the tool's error handling when the JAVA_HOME environment variable is set incorrectly, ensuring it provides useful feedback for troubleshooting.

### Pre-requisites
- CodeMetropolis' tools have been downloaded to the system.
- JAVA_HOME is set to an incorrect or non-existent Java installation path.

### Steps
1. Set JAVA_HOME to a path that does not contain a valid Java installation.
2. Attempt to generate a world using the CodeMetropolis tool.
3. Note any error messages or feedback provided by the tool.

### Expected Result
The tool fails to proceed with world generation and provides a clear error message indicating an issue with the JAVA_HOME configuration.
