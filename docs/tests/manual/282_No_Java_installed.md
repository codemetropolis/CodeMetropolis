# Test no. 282 pull request's solution on no Java installed

## Tags
#pullRequest_282 #status_not_merged_functionality #category_error_handling  #component_mapping #component_converter #component_placing #component_rendering

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/282 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/282-there-should-be-a-background-check-on-the-java-version-before-the-world-can-be-generated

### Purpose
To confirm that the tool appropriately detects the absence of a Java installation and provides a clear error message, guiding the user to resolve the issue.

### Pre-requisites
- CodeMetropolis' tools have been downloaded to the system.
- Java is not installed on the system.

### Steps
1. Ensure Java is not installed by checking with `java -version`.
2. Attempt to generate a world using the CodeMetropolis tools with this guide: [[How to generate a default CodeMetropolis Minecraft world from start to finish]]
3. Note the error message provided by the tool.

### Expected Result
The tool detects that Java is not installed and issues an informative error message about the missing Java installation.
