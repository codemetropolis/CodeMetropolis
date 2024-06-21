# Check issue no. #195's solution if it returns an error when an invalid layout parameter is used

## Tags
#pullRequest_195 #component_placing #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/195 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/195-placing-with-empty-layout-parameter-is-not-handled-correctly

### Purpose
The purpose of this test is to check if the issue's solution still prevents the use of invalid layout parameters and gives back an error message.

### Test File
- [[defaultValidCodeMetroMappingToPlacing.xml]]

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of placing-1.5.0.jar
- A *defaultValidCodeMetroMappingToPlacing.xml* file exists in the placing-1.5.0.jar's folder. (Has to be a valid file, which was produced by running converter-1.5.0.jar with the *converterToMapping.xml* as input)

### Steps
1.  Run the following command in the folder of placing-1.5.0.jar:
   ```cmd
   java -jar placing-1.5.0.jar -i defaultValidCodeMetroMappingToPlacing.xml -l ASD
   ```

### Expected Result
The placing tool finishes running and an error message is given back regarding the invalid layout parameter.