# Test validation for project name with spaces on pull request no. 284's solution

## Tags
#pullRequest_284 #status_not_merged_functionality #category_error_handling #component_GUI

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/284 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/284-add-immediate-field-validation-for-the-gui-tool-for-the-following-field--project-name

### Purpose
To ensure the project name field correctly handles input containing spaces, validating names that include spaces without errors.

### Pre-requisites
- CodeMetropolis tool GUI is open for input.
- Java runtime 22.

### Steps
1. Open the CodeMetropolis tool GUI.
2. Enter a project name that includes spaces (e.g., "My Project").
3. Observe the validation response to the input.

### Expected Result
The field accepts project names with spaces, showing no error message if other validation criteria are met.

