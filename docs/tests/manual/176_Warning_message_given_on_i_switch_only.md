# Check issue no. #176's solution if it still gives back a warning message when i switch is used

## Tags
#pullRequest_176 #component_mapping #category_input 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/176 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/176-mapping-without-any-parameters-is-not-handled-correctly

### Purpose
The purpose of this test is to see when running the mapping tool (from the solution committed to issue no. #176) with only the *-i* switch and it's parameter it gives back a warning message about the arguments that can be used with the mapping tool.

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of mapping-1.5.0.jar
- A *converterToMappin.xml* file exists in the mapping-1.5.0.jar's folder. (Has to be a valid file, which was produced by running converter-1.5.0.jar with a valid graph file as input)

### Steps
1. Run the following command in the folder of mapping-1.5.0.jar:
   ```cmd
   java -jar mapping-1.5.0.jar -i converterToMapping.xml
   ```
  
### Expected Result
The tool writes an error type message on the console about what kind of arguments can be used with the mapping tool.