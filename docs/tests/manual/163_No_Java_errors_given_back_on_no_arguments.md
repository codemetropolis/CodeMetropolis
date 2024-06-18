# Check issue no. #163's solution that it will not give back Java error messages in the console when ran without arguments

## Tags
#pullRequest_163 #component_converter #category_input 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/163 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/163-conversion-without-any-parameters-is-not-handled-correctly

### Purpose
The purpose of this test is to see if the solution committed to issue no. #163 will not write any Java error messages in the console when the converter tool is ran without arguments.

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of converter-1.5.0.jar

### Steps
1. Run the following command in the folder of converter-1.5.0.jar:
   ```cmd
   java -jar converter-1.5.0.jar
   ```

### Expected Result
After running the command, no Java error is given back in the console.
