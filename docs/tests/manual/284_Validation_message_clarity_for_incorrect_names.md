# Test validation message clarity for incorrect project names on pull request no. 284's solution

## Tags
#pullRequest_284 #status_not_merged_functionality #category_correct_working #component_GUI

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/284 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/284-add-immediate-field-validation-for-the-gui-tool-for-the-following-field--project-name

### Purpose
To evaluate the clarity and usefulness of validation messages provided for project names that do not meet the specified criteria.

### Pre-requisites
- CodeMetropolis GUI tool launched.
- Java runtime 22.

### Steps
1. Enter various incorrect project names to trigger validation messages (e.g., too short, contains invalid characters).
2. Review the specificity and clarity of the messages provided.

### Expected Result
The validation messages are clear and informative, guiding the user to correct the issue with the project name effectively.
