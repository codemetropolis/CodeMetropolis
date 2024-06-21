# Test no. 282 pull request's solution on invalid Java version

## Tags
#pullRequest_282 #status_not_merged_functionality #category_error_handling  #component_mapping #component_converter #component_placing #component_rendering

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/282 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/282-there-should-be-a-background-check-on-the-java-version-before-the-world-can-be-generated

### Purpose
To ensure the tool identifies and halts operation when an incompatible Java version is detected, preventing execution to avoid misleading error messages.

### Pre-requisites
- CodeMetropolis' tools have been downloaded to the system.
- A version of Java other than 8 is installed on the system.

### Steps
1. Verify the installed Java version using `java -version`.
2. Attempt to generate a world using the CodeMetropolis tools with this guide: [[How to generate a default CodeMetropolis Minecraft world from start to finish]]
3. Observe the tools' behavior and output.

### Expected Result
The tool halts world generation and displays an error message indicating the Java version is not compatible.
