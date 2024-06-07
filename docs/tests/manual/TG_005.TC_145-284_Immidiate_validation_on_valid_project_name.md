# Test immediate validation for a valid project name on pull request no. 284's solution 

## Tags
#pullRequest_284 #status_not_merged_functionality #category_correct_working #component_GUI 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/284 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/284-add-immediate-field-validation-for-the-gui-tool-for-the-following-field--project-name

### Purpose
To confirm that the project name field is immediately validated as correct when entering a name that meets the validation criteria.

### Pre-requisites
- Access to the CodeMetropolis tool GUI.
- Java runtime 22.

### Steps
1. Open the CodeMetropolis tool GUI.
2. Enter a valid project name (e.g., "CodeMetro") in the project name field.
3. Observe the validation behavior as the field is exited or the input focus changes.

### Expected Result
The field shows a positive validation feedback, indicating the project name is valid.

