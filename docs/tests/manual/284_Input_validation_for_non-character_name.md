# Test non-character input validation for project name on pull request no. 284's solution 

## Tags
#pullRequest_284 #status_not_merged_functionality #category_error_handling #component_GUI

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/284 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/284-add-immediate-field-validation-for-the-gui-tool-for-the-following-field--project-name

### Purpose
To validate the project name field against non-character inputs, ensuring the field accepts only valid textual input.

### Pre-requisites
- The CodeMetropolis tool GUI is launched and on the screen.
- Java runtime 22.

### Steps
1. Open the CodeMetropolis tool GUI.
2. Try entering non-alphabetic characters (e.g., symbols, numbers) in the project name field.
3. Observe if the input is accepted or if validation feedback is provided.

### Expected Result
The system either prevents non-character input from being entered or displays a validation error, ensuring only appropriate project names are accepted.



