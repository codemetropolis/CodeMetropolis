# Test no. 282 pull request's solution for Java environment variable

## Tags
#pullRequest_282 #status_not_merged_functionality #category_correct_working  #component_mapping #component_converter #component_placing #component_rendering

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/282 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/282-there-should-be-a-background-check-on-the-java-version-before-the-world-can-be-generated

### Purpose
To verify that the tool respects the JAVA_HOME environment variable when determining which Java version to use, ensuring it can work in environments with multiple Java installations.

### Pre-requisites
- CodeMetropolis' tools have been downloaded to the system.
- Multiple versions of Java are installed, with JAVA_HOME pointing to Java 8.

### Steps
1. Set the JAVA_HOME environment variable to the path of Java 8.
2. Attempt to generate a world using the CodeMetropolis tools with this guide: [[How to generate a default CodeMetropolis Minecraft world from start to finish]]
3. Observe the tool's version check behavior.

### Expected Result
The tool uses the Java version specified by JAVA_HOME for its version check and world generation.
