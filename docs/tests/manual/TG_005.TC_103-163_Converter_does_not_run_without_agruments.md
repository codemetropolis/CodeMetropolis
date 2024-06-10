# Check issue no. #163's solution that it does not run the converter tool without any arguments

## Tags
#pullRequest_163 #component_converter #category_input 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/163 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/163-conversion-without-any-parameters-is-not-handled-correctly

### Purpose
The purpose of this test is to see the converter tool does not run at all when using the tool without any arguments give in the console.

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of converter-1.5.0.jar

### Steps
1. Run the following command in the folder of converter-1.5.0.jar:
   ```cmd
   java -jar converter-1.5.0.jar
   ```

### Expected Result
The converter tool does not run and does not complete any specified operations.