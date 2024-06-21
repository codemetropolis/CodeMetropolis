# Validate Color Coding for Mapping.xml Field Validation on Pull Request No. 286's Solution

## Tags
#pullRequest_286 #status_not_merged_functionality #category_correct_working #component_GUI #testFile-format_mapping #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/286 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/286-add-immediate-field-validation-for-the-gui-tool-for-the-following-field--mapping-xml

### Purpose
To confirm that the color coding for validation feedback is correctly implemented as per the property file settings.

### Test File
- [[validSourceMeterTestFileForGuiMappingFieldValidationIssue.xml]]

### Pre-requisites
- Java runtime 22.

### Steps
1. Place the *Test File* into the mapping-1.5.0.jar's directory.
2. Open the CodeMetropolis GUI tool with this tutorial: [[How to Open CodeMetropolis GUI]]
3. In the GUI window which pops up navigate to the **Mapping xml** field. 
4. Type the path to the *Test File* manually OR press the browse button and find the *Test File* through the **Open** named window.
5. Move focus away from the field and observe the validation color.
6. Change the path to an invalid one. For example: `C:\Mapping.xml` (in this example no Mapping.xml file exists on the C: drive)
7. Move focus away from the field and observe the validation color.

### Expected Result
The field background changes color appropriately: green for valid input and red for invalid input, with less intense colors as specified.
