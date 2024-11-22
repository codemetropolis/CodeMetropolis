# Test pull request 154 on handling special characters in regex

## Tags
#pullRequest_154 #testFile-format_IXML #category_output #status_not_merged_functionality #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/154 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/154-create-an-ixml-exclude--by-property--tool

### Purpose
To verify the tool's ability to correctly interpret and process regex patterns containing special characters. This test aims to ensure that the tool can handle regex expressions with characters that have specific meanings within regex syntax, such as `.*?[]^$`, ensuring accurate and reliable property matching and exclusion based on complex criteria.

### Pre-requisites
- An IXML file with properties that include special regex characters in their names or values is prepared for testing.

### Steps
1. Open a command-line interface.
2. Navigate to the folder containing the tool and the prepared IXML file.
3. Execute the tool with arguments specifying the input IXML file, the output file name, and regex patterns that include special characters. Example command: 
```cmd
java -jar ixmlexclude.jar -i specialChars.ixml -o outputTest.ixml --exclude-property-name ".*[buildable]^$" --exclude-property-value "[a]*?"
```
4. Observe the tool's ability to process the input and exclude properties based on the provided patterns.

### Expected Result
The tool successfully interprets the special characters in the regex patterns and applies the filters accurately. Properties matching the complex regex patterns are excluded from the output IXML file.
