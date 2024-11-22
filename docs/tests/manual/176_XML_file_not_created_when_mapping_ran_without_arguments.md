# Check issue no. #176's solution that it does not create an xml file when running mapping without arguments

## Tags
#pullRequest_176 #component_mapping #category_output

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/176 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/176-mapping-without-any-parameters-is-not-handled-correctly

### Purpose
The purpose of this test is to check that when running the mapping tool without any arguments it does not create any xml file as output of the command.

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of mapping-1.5.0.jar

### Steps
1. Run the following command in the folder of mapping-1.5.0.jar:
   ```cmd
   java -jar mapping-1.5.0.jar
   ```
2. Check the folder of `\CodeMetropolis\sources`

### Expected Result
No xml file was created as a result when running the mapping tool without any arguments.