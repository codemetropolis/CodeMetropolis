# Validate Response to Clearing Mapping.xml Field on Pull Request No. 286's Solution

## Tags
#pullRequest_286 #status_not_merged_functionality #category_correct_working #component_GUI #testFile-format_mapping #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/286 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/286-add-immediate-field-validation-for-the-gui-tool-for-the-following-field--mapping-xml

### Purpose
To ensure the tool provides correct feedback when the Mapping.xml field is cleared after previously entering a valid or invalid path.

### Test File
- [[validSourceMeterTestFileForGuiMappingFieldValidationIssue.xml]]

### Pre-requisites
- Java runtime 22.

### Steps
1. Place the *Test File* into the mapping-1.5.0.jar's directory.
2. Open the CodeMetropolis GUI tool with this tutorial: [[How to Open CodeMetropolis GUI]]
3. In the GUI window which pops up navigate to the **Mapping xml** field. 
4. Type the path to the *Test File* manually OR press the browse button and find the *Test File* through the **Open** named window.
5. Move focus away from the field and observe the validation.
6. Clear the field entirely.
7. Observe the validation response to the cleared field.

### Expected Result
An appropriate validation message appears, indicating that the field cannot be left empty.
