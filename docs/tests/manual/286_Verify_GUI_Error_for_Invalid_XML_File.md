# Verify Error for Invalid Mapping.xml File Extension on Pull Request No. 286's Solution

## Tags
#pullRequest_286 #status_not_merged_functionality #category_error_handling #component_GUI #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/286 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/286-add-immediate-field-validation-for-the-gui-tool-for-the-following-field--mapping-xml

### Purpose
To test the field validation's response to an incorrect file extension in the Mapping.xml path.

### Test File
- [[invalidSourceMeterTestFileForGuiMappingFieldValidationIssue.txt]]

### Pre-requisites
- Java runtime 22.

### Steps
1. Place the *Test File* into the mapping-1.5.0.jar's directory.
2. Open the CodeMetropolis GUI tool with this tutorial: [[How to Open CodeMetropolis GUI]]
3. In the GUI window which pops up navigate to the **Mapping xml** field. 
4. Type the path to the *Test File* manually OR press the browse button and find the *Test File* through the **Open** named window.
5. Move focus away from the field to trigger the validation.

### Expected Result
The GUI shows an error message stating the file must end with ".xml".