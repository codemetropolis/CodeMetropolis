# Check issue no. #163's solution that it does not create an xml file when running converter without arguments

## Tags
#pullRequest_163 #component_converter #category_output 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/163 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/163-conversion-without-any-parameters-is-not-handled-correctly

### Purpose
The purpose of this test is to check that when running the converter tool without any arguments it does not create any xml file as output of the command.

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of converter-1.5.0.jar

### Steps
1. Run the following command in the folder of converter-1.5.0.jar:
   ```cmd
   java -jar converter-1.5.0.jar
   ```
2. Check the folder of `\CodeMetropolis\sources`

### Expected Result
No xml file was created as a result when running the converter tool without any arguments.