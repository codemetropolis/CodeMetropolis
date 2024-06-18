# Test button after sourcemeter installation on pull request no. 239's solution

## Tags
#pullRequest_239 #status_not_merged_functionality #category_correct_working #component_converter_SourceMeter #component_GUI

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/239 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/239-direct-download-button-for-sourcemeter-basic

### Purpose
To assess the direct download button's behavior or visibility after Sourcemeter has been successfully installed via the button.

### Pre-requisites
- CodeMetropolis tool is installed and accessible.
- Sourcemeter is not yet installed.
- Through the main function in `CodeMetropolis\sources\gui\src\main\java\codemetropolis\toolchain\gui` the GUI application is open.
- Java runtime 22.

### Steps
1. Install Sourcemeter using the direct download button within the CodeMetropolis tool.
2. After installation, reopen or refresh the CodeMetropolis tool.
3. Observe any changes in the visibility or functionality of the download button.

### Expected Result
Post-installation, the download button becomes disabled or is replaced with a notification indicating Sourcemeter is already installed, enhancing user experience and avoiding confusion.
