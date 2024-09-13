# Check issue no. #176's solution if it still gives back a warning message when m switch is used

## Tags
#pullRequest_176 #component_mapping #category_output 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/176 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/176-mapping-without-any-parameters-is-not-handled-correctly

### Purpose
The purpose of this test is to see when running the mapping tool (from the solution committed to issue no. #176) with only the *-m* switch and it's value it still gives back a warning message about the missing arguments.

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of mapping-1.5.0.jar

### Steps
1. Run the following command in the folder of mapping-1.5.0.jar:
   ```cmd
   java -jar mapping-1.5.0.jar -m "CodeMetropolis\examples\mapping\sourcemeter_mapping_example_2_1.xmln"
   ```

### Expected Result
The tool writes an error type message on the console about what kind of arguments can be used with the mapping tool.