# Check issue no. #195's solution if it prevents the placing tool's normal working mode

## Tags
#pullRequest_195 #component_placing #category_output 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/195 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/195-placing-with-empty-layout-parameter-is-not-handled-correctly

### Purpose
The purpose of this test is to see if the solution committed to issue no. #195 will not change the placing tool's default working mode and thus will not prevent the creation of an xml file.

### Test File
- [[defaultValidCodeMetroMappingToPlacing.xml]]

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of placing-1.5.0.jar
- A *defaultValidCodeMetroMappingToPlacing.xml* file exists in the placing-1.5.0.jar's folder. (Has to be a valid file, which was produced by running converter-1.5.0.jar with the *converterToMapping.xml* as input)

### Steps
1. Run the following command in the folder of placing-1.5.0.jar:
   ```cmd
   java -jar placing-1.5.0.jar -i defaultValidCodeMetroMappingToPlacing.xml -l pack
   ```
2. Check the folder of `\CodeMetropolis\sources` and see if a new xml file was created

### Expected Result
An xml output file has been created according to the tool's specification and the program finished running.