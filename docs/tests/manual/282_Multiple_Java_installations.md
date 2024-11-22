# Test no. 282 pull request's solution when using multiple Java installations

## Tags
#pullRequest_282 #status_not_merged_functionality #category_correct_working #component_mapping #component_converter #component_placing #component_rendering

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/282 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/282-there-should-be-a-background-check-on-the-java-version-before-the-world-can-be-generated

### Purpose
To verify the tool's behavior in an environment with multiple Java installations. This test ensures the tool selects the correct Java version for operation, particularly in systems where different Java versions might be used for other applications.

### Pre-requisites
- CodeMetropolis' tools have been downloaded to the system.
- Multiple versions of Java are installed on the system, with system paths and JAVA_HOME pointing to different versions.

### Steps
1. Configure the system to recognize multiple Java installations through system path and JAVA_HOME.
2. Execute the CodeMetropolis tool to generate a world, observing which Java version it utilizes.
3. Verify the Java version used matches the intended version as per system configuration.

### Expected Result
The tool correctly identifies and uses the Java version intended for its operation, as specified by the system configuration, without conflict from other installed Java versions.
