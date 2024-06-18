# Test no. 282 pull request's solution when updating Java version

## Tags
#pullRequest_282 #status_not_merged_functionality #category_correct_working  #component_mapping #component_converter #component_placing #component_rendering

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/282 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/282-there-should-be-a-background-check-on-the-java-version-before-the-world-can-be-generated

### Purpose
To test the tool's behavior when the Java version is updated while the tool is running, ensuring it dynamically recognizes changes in the Java environment.

### Pre-requisites
- CodeMetropolis' tools have been downloaded to the system.
- Two versions of Java are installed, initially using an older version.

### Steps
1. Start the world generation process with an older Java version. ([[How to generate a default CodeMetropolis Minecraft world from start to finish]])
2. Update the JAVA_HOME environment variable to a newer Java version during generation.
3. Restart the tool and observe behavior.

### Expected Result
The tool recognizes the updated Java version upon restart and proceeds based on the new version's compatibility.
