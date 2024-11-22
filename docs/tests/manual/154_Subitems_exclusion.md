# Test no. 154 pull request's exclusion for subitems

## Tags
#pullRequest_154 #testFile-format_IXML #category_output #status_not_merged_functionality #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/154 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/154-create-an-ixml-exclude--by-property--tool

### Purpose
To confirm that the tool not only excludes specified items based on regex patterns but also correctly handles and excludes any subitems of these items. This ensures comprehensive filtering, as users might expect that specifying an item for exclusion would also remove its related subitems from the output, maintaining consistency and integrity of the filtered data.

### Pre-requisites
- An IXML file with hierarchical properties (items and subitems) that match the specified regex patterns for exclusion is prepared.

### Steps
1. Open a command-line interface.
2. Navigate to the folder containing the tool and the test IXML file.
3. Execute the tool, specifying regex patterns that match items known to have subitems. Example command: 
	```cmd 
	java -jar ixmlfiltering.jar -i input.ixml -o outputTest.ixml -pn "buildable" -pv "5"
	```
4. Examine the output file to verify that both the matched items and their subitems are excluded.

### Expected Result
The tool successfully excludes both the specified items and all their associated subitems from the output IXML file, demonstrating the tool's ability to perform comprehensive filtering based on the provided criteria.
