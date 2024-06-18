# Check issue no. #195's solution that the placing component exits normally when missing layout parameter is used

## Tags
#pullRequest_195 #component_placing #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/195 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/195-placing-with-empty-layout-parameter-is-not-handled-correctly

### Purpose
The purpose of this test is to see if the solution committed to issue no. #195 will exit the placing tool normally when no layout parameter is given.

### Test File
- [[defaultValidCodeMetroMappingToPlacing.xml]]

### Pre-requisites	
- Java runtime 22
- Command line opened in the folder of placing-1.5.0.jar
- A *defaultValidCodeMetroMappingToPlacing.xml* file exists in the placing-1.5.0.jar's folder. (Has to be a valid file, which was produced by running converter-1.5.0.jar with the *converterToMapping.xml* as input)

### Steps
1.  Run the following command in the folder of placing-1.5.0.jar:
   ```cmd
   java -jar placing-1.5.0.jar -i defaultValidCodeMetroMappingToPlacing.xml -l
   ```

### Expected Result
The placing tool finished running and exited normally when the layout parameter was missing from the command used for running the tool.