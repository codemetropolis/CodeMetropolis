# Test button resilience to multiple clicks on pull request no. 239's solution

## Tags
#pullRequest_239 #status_not_merged_functionality  #category_error_handling  #component_converter_SourceMeter #component_GUI

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/239 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/239-direct-download-button-for-sourcemeter-basic

### Purpose
To test the download button's resilience and the system's stability when the button is clicked multiple times in quick succession.

### Pre-requisites
- Through the main function in `CodeMetropolis\sources\gui\src\main\java\codemetropolis\toolchain\gui` the GUI application is open.
- Java runtime 22.

### Steps
1. Open the CodeMetropolis tool.
2. Rapidly click the direct download button for Sourcemeter basic several times.
3. Observe any unusual behavior or crashes.

### Expected Result
The tool remains stable, and multiple clicks do not lead to crashes or multiple instances of browser windows opening.
