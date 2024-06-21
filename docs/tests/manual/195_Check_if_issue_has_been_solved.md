# Issue no. #195's solution if it solved the problem of the test mentioned in the issue

## Tags
#pullRequest_195 #component_placing #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/195 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/195-placing-with-empty-layout-parameter-is-not-handled-correctly

### Purpose
The purpose of this test is to see if the solution committed to issue no. #203 solved the problem of the issue.

### Test File
- [[defaultValidCodeMetroMappingToPlacing.xml]]

### Pre-requisites	
- Java runtime 22
- Command line opened in the folder of placing-1.5.0.jar
- A *defaultValidCodeMetroMappingToPlacing.xml* file exists in the placing-1.5.0.jar's folder. (Has to be a valid file, which was produced by running converter-1.5.0.jar with the *converterToMapping.xml* as input)

### Steps
1. Run the following command in the folder of placing-1.5.0.jar:
   ```cmd
   java -jar placing-1.5.0.jar -i defaultValidCodeMetroMappingToPlacing.xml -l
   ```

### Expected Result
The tool gives back an error that the layout parameter is missing or incorrect.