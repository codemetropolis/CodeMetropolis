# Test button functionality after system restart on pull request no. 239's solution

## Tags
#pullRequest_239 #status_not_merged_functionality #category_error_handling #component_converter_SourceMeter #component_GUI

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/239 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/239-direct-download-button-for-sourcemeter-basic

### Purpose
To verify that the direct download button for Sourcemeter basic retains its functionality after the system hosting the CodeMetropolis tool has been restarted.

### Pre-requisites
- Through the main function in `CodeMetropolis\sources\gui\src\main\java\codemetropolis\toolchain\gui`, the GUI application is open.
- Java runtime 22.
- The system has been restarted after initial tool use.

### Steps
1. Restart the system where the CodeMetropolis tool is installed.
2. Reopen the CodeMetropolis tool through the specified path.
3. Click the direct download button for Sourcemeter basic.
4. Observe the button's functionality and ensure it directs to the download page.

### Expected Result
The direct download button operates as expected, directing the user to the Sourcemeter basic download page, regardless of the system restart.
