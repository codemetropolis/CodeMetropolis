# Test validation for project name with special characters on pull request no. 284's solution

## Tags
#pullRequest_284 #status_not_merged_functionality #category_error_handling #component_GUI

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/284 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/284-add-immediate-field-validation-for-the-gui-tool-for-the-following-field--project-name

### Purpose
To verify the project name field's response to special characters, ensuring names with acceptable special characters pass validation.

### Pre-requisites
- The CodeMetropolis tool GUI ready for testing.
- Java runtime 22.

### Steps
1. Input a project name containing special characters (e.g., "Project_#1") in the tool GUI.
2. Check the immediate validation feedback.

### Expected Result
The field either validates names with certain special characters without issue or provides specific feedback on unacceptable characters.
