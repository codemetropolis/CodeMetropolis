# Test no. 154 pull request's handling for case sensitivity 

## Tags
#pullRequest_154 #testFile-format_IXML #category_output #status_not_merged_functionality #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/154 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/154-create-an-ixml-exclude--by-property--tool

### Purpose
To evaluate the tool's handling of case sensitivity in regex patterns. This test ensures that the tool accurately respects the case sensitivity of regex patterns when filtering properties, allowing for precise control over the exclusion process. It's crucial for scenarios where the distinction between uppercase and lowercase characters is significant for property identification and exclusion.

### Pre-requisites
- An IXML file with properties that vary only in case (e.g., "PropertyName" vs. "propertyname") is prepared for testing.

### Steps
1. Open a command-line interface.
2. Navigate to the folder containing the tool and the test IXML file.
3. Execute the tool with a case-sensitive regex pattern aimed at matching specific property names or values. Example command: 
```cmd
java -jar ixmlfiltering.jar -i caseSensitive.ixml -o outputTest.ixml -pn "buildable[A-Z]+" -pv "name[a-z]+"
```

4. Review the output file to verify that only properties matching the exact case of the regex pattern were excluded.

### Expected Result
The tool applies the regex filters with case sensitivity, excluding only the properties that match the pattern with the specified case. Properties that do not match the case of the regex pattern remain in the output IXML file.
