# Test button functionality with no internet connection on pull request no. 239's solution 

## Tags
#pullRequest_239 #status_not_merged_functionality #category_output #category_error_handling #component_converter_SourceMeter #component_GUI

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/239 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/239-direct-download-button-for-sourcemeter-basic

### Purpose
To evaluate the tool's response when the direct download button is clicked without an active internet connection.

### Pre-requisites
- Through the main function in `CodeMetropolis\sources\gui\src\main\java\codemetropolis\toolchain\gui` the GUI application is open
- Ability to disable the internet connection.
- Java runtime 22

### Steps
1. Disable the internet connection on the system.
2. Open the CodeMetropolis tool and click the direct download button for Sourcemeter basic.
3. Note any error messages or notifications presented by the tool.

### Expected Result
The tool provides a clear and understandable notification or error message indicating that an internet connection is required.
