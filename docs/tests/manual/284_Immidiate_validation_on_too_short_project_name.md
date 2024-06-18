# Test immediate validation with a project name too short on pull request no. 284's solution 

## Tags
#pullRequest_284 #status_not_merged_functionality #category_correct_working #component_GUI 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/284 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/284-add-immediate-field-validation-for-the-gui-tool-for-the-following-field--project-name

### Purpose
To verify that an immediate validation error is displayed when entering a project name shorter than the required minimum length.

### Pre-requisites
- The CodeMetropolis tool GUI is open and ready for input.
- Java runtime 22.

### Steps
1. Open the CodeMetropolis tool GUI.
2. Enter a project name shorter than three characters.
3. Move the cursor away from the project name field to trigger validation.

### Expected Result
An error message appears below the project name field, indicating that the name is too short.
