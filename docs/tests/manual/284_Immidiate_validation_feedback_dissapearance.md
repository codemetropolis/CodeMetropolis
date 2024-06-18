# Test immediate validation feedback disappears after correction on pull request no. 284's solution 

## Tags
#pullRequest_284 #status_not_merged_functionality #category_correct_working #component_GUI

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/284 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/284-add-immediate-field-validation-for-the-gui-tool-for-the-following-field--project-name

### Purpose
To ensure that the validation error message disappears immediately after the project name is corrected to meet the validation criteria.

### Pre-requisites
- The CodeMetropolis GUI tool is actively running.
- Java runtime 22.

### Steps
1. Open the CodeMetropolis tool GUI.
2. Enter an invalid project name to trigger a validation error.
3. Correct the project name to meet the minimum length requirement.
4. Observe the behavior of the validation message.

### Expected Result
The error message disappears as soon as the project name meets the validation criteria.

