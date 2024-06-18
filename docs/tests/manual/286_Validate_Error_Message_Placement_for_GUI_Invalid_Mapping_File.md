# Validate Error Message Placement for Invalid Mapping.xml Path on Pull Request No. 286's Solution

## Tags
#pullRequest_286 #status_not_merged_functionality #category_correct_working #component_GUI #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/286 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/286-add-immediate-field-validation-for-the-gui-tool-for-the-following-field--mapping-xml

### Purpose
To ensure that the validation message for an invalid Mapping.xml path is correctly placed under the input field.

### Pre-requisites
- Java runtime 22.

### Steps
1. Open the CodeMetropolis GUI tool with this tutorial: [[How to Open CodeMetropolis GUI]]
2. In the GUI window which pops up navigate to the **Mapping xml** field. 
3. Type a path to a non-existent Mapping.xml file into the field. For example: `C:\Mapping.xml`
4. Move focus away from the field to trigger the validation.

### Expected Result
The validation message appears directly below the Mapping.xml input field.