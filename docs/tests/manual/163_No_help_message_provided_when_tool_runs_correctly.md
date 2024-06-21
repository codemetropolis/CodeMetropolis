# Check issue no. #163's solution that there will be no helping message about arguments given back when tool runs correctly

## Tags
#pullRequest_163 #component_converter #category_output 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/163 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/163-conversion-without-any-parameters-is-not-handled-correctly

### Purpose
The purpose of this test is to see if the solution committed to issue no. #163 will not write any argument help messages in the console, when the converter tool is ran correctly with the correct arguments.

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of converter-1.5.0.jar

### Steps
1. Run the following command in the folder of converter-1.5.0.jar:
   ```cmd
   java -jar converter-1.5.0.jar -t sourcemeter -s "CodeMetropolis\examples\sourcemeter graph\codemetropolis-toolchain-rendering.graph"
   ```

### Expected Result
No argument help message is given back in the console and the tool finishes running normally.