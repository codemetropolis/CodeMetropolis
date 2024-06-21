# Test empty project name validation on pull request no. 284's solution

## Tags
#pullRequest_284 #status_not_merged_functionality #category_error_handling #component_GUI

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/284 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/284-add-immediate-field-validation-for-the-gui-tool-for-the-following-field--project-name

### Purpose
To determine how the project name validation behaves when no name is entered, ensuring users are prompted to provide necessary information.

### Pre-requisites
- CodeMetropolis tool GUI is in use.
- Java runtime 22.

### Steps
1. Attempt to submit a project creation request without entering a name in the project name field.
2. Note the immediate feedback from the validation mechanism.

### Expected Result
An error or warning message is displayed, indicating that the project name field cannot be left blank.
