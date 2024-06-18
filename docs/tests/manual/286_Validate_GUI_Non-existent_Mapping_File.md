# Validate Non-existent Mapping.xml File Path on Pull Request No. 286's Solution

## Tags
#pullRequest_286 #status_not_merged_functionality #category_error_handling #component_GUI #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/286 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/286-add-immediate-field-validation-for-the-gui-tool-for-the-following-field--mapping-xml

### Purpose
To verify that the field validation properly flags a non-existent file path for Mapping.xml.

### Pre-requisites
- Java runtime 22.

### Steps
1. Open the CodeMetropolis GUI tool with this tutorial: [[How to Open CodeMetropolis GUI]]
2. In the GUI window which pops up navigate to the **Mapping xml** field. 
3. Type a path to a non-existent Mapping.xml file into the field. For example: `C:\Mapping.xml`
4. Move focus away from the field to trigger the validation.

### Expected Result
The system should display an error message indicating that the file path does not exist.