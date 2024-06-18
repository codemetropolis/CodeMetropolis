# Check issue no. #195's solution for errors when -l switch is swapped with -i switch

## Tags
#pullRequest_195 #component_placing #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/195 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/195-placing-with-empty-layout-parameter-is-not-handled-correctly

### Purpose
The purpose of this test is to see if the program does not give an error when the layout switch is swapped with the *-i* swtich when running the placing tool.

### Test File
- [[defaultValidCodeMetroMappingToPlacing.xml]]

### Pre-requisites	
- Java runtime 22
- Command line opened in the folder of placing-1.5.0.jar
- A *defaultValidCodeMetroMappingToPlacing.xml* file exists in the placing-1.5.0.jar's folder. (Has to be a valid file, which was produced by running converter-1.5.0.jar with the *converterToMapping.xml* as input)

### Steps
1.  Run the following command in the folder of placing-1.5.0.jar:
   ```cmd
   java -jar placing-1.5.0.jar -l pack -i defaultValidCodeMetroMappingToPlacing.xml
   ```

### Expected Result
The tool runs normally without giving back an error and creates its output file.