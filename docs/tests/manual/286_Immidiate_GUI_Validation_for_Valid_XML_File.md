# Validate Correct Mapping.xml File Path on Pull Request No. 286's Solution

## Tags
#pullRequest_286 #status_not_merged_functionality #category_correct_working #component_GUI #testFile-format_mapping #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/286 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/286-add-immediate-field-validation-for-the-gui-tool-for-the-following-field--mapping-xml

### Purpose
To confirm that the field validation recognizes and accepts a correct Mapping.xml file path that exists and ends with ".xml".

### Test File
- [[validSourceMeterTestFileForGuiMappingFieldValidationIssue.xml]]

### Pre-requisites
- Java runtime 22.

### Steps
1. Place the *Test File* into the mapping-1.5.0.jar's directory.
2. Open the CodeMetropolis GUI tool with this tutorial: [[How to Open CodeMetropolis GUI]]
3. In the GUI window which pops up navigate to the **Mapping xml** field. 
4. Type the path to the *Test File* manually OR press the browse button and find the *Test File* through the **Open** named window.
5. Move focus away from the field to trigger the validation.

### Expected Result
The GUI displays a validation success message OR the **Mapping xml** field becomes green, indicating the file path is correct.