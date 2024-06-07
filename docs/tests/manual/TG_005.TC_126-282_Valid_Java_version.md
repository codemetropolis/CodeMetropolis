# Test no. 282 pull request's solution on valid Java version

## Tags
#pullRequest_282 #status_not_merged_functionality #category_correct_working #component_mapping #component_converter #component_placing #component_rendering

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/282 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/282-there-should-be-a-background-check-on-the-java-version-before-the-world-can-be-generated

### Purpose
To verify that the tool correctly proceeds with world generation when the Java version matches the required version (Java 8), ensuring compatibility and avoiding errors related to version mismatch.

### Pre-requisites
- CodeMetropolis' tools have been downloaded to the system.
- Java 8 is installed on the system.

### Steps
1. Verify the installed Java version using `java -version`.
2. Attempt to generate a world using the CodeMetropolis tools with this guide: [[How to generate a default CodeMetropolis Minecraft world from start to finish]]
3. Observe the tools' behavior and output.

### Expected Result
The tools successfully recognizes the valid Java version and proceeds with the world generation without any version-related errors.

