# Test field validation on project name field focus loss on pull request no. 284's solution 

## Tags
#pullRequest_284 #status_not_merged_functionality #category_correct_working #component_GUI

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/284 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/284-add-immediate-field-validation-for-the-gui-tool-for-the-following-field--project-name

### Purpose
To test that the project name field undergoes validation not only on input but also when the field loses focus, enhancing user feedback.

### Pre-requisites
- The CodeMetropolis tool GUI is accessible for interaction.
- Java runtime 22.

### Steps
1. Open the CodeMetropolis tool GUI.
2. Enter a project name and immediately switch focus without exiting the field correctly.
3. Check if validation triggers upon focus loss.

### Expected Result
Validation feedback is provided when the project name field loses focus, ensuring users are aware of errors even if they don't exit the field properly.
