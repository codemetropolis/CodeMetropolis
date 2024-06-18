# Test no. 282 pull request's solution on Java version check enforcement

## Tags
#pullRequest_282 #status_not_merged_functionality #category_correct_working  #component_mapping #component_converter #component_placing #component_rendering

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/282 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/282-there-should-be-a-background-check-on-the-java-version-before-the-world-can-be-generated

### Purpose
To ensure there is no flag or option available that allows users to bypass the mandatory Java version check, maintaining the integrity and reliability of the tool's execution environment.

### Pre-requisites
- CodeMetropolis' tools have been downloaded to the system.
- Java 8 is installed on the system.

### Steps
1. Review the CodeMetropolis tools' documentation and available command-line arguments to verify the absence of any flag that would bypass the Java version check.
2. Attempt to generate a world using the CodeMetropolis tools with this guide: [[How to generate a default CodeMetropolis Minecraft world from start to finish]]
3. Document any findings related to the capability of bypassing the Java version check.

### Expected Result
There are no flags, switches, or options available that allow the bypassing of the Java version check and the default run of the tools' is done without errors.
