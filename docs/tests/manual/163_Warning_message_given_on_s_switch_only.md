# Check issue no. #163's solution if it still gives back a warning message when s switch is used

## Tags
#pullRequest_163 #component_converter #category_output 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/163 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/163-conversion-without-any-parameters-is-not-handled-correctly

### Purpose
The purpose of this test is to see when running the converter tool (from the solution committed to issue no. #163) with only the *-s* switch and it's value it still gives back a warning message about the missing arguments.

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of converter-1.5.0.jar

### Steps
1. Run the following command in the folder of converter-1.5.0.jar:
   ```cmd
   java -jar converter-1.5.0.jar -s "CodeMetropolis\examples\sourcemeter graph\codemetropolis-toolchain-rendering.graph"
   ```

### Expected Result
The tool writes a message on the console about what kind of arguments can be used with the mapping tool.