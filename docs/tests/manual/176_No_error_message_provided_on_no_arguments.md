# Check issue no. #176's solution that it will not give back error messages in the console when ran without arguments

## Tags
#pullRequest_176 #component_mapping #category_input 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/176 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/176-mapping-without-any-parameters-is-not-handled-correctly

### Purpose
The purpose of this test is to see if the solution committed to issue no. #176 will not write any error messages in the console when the mapping tool is ran without arguments.

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of mapping-1.5.0.jar

### Steps
1. Run the following command in the folder of mapping-1.5.0.jar:
   ```cmd
   java -jar mapping-1.5.0.jar
   ```

### Expected Result
After running the command, no error is given back in the console.