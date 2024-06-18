# Check issue no. #176's solution that it does not run the mapping tool without arguments

## Tags
#pullRequest_176 #component_mapping #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/176 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/176-mapping-without-any-parameters-is-not-handled-correctly

### Purpose
The purpose of this test is to see the mapping tool does not run at all when using the tool without any arguments give in the console.

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of mapping-1.5.0.jar

### Steps
1. Run the following command in the folder of mapping-1.5.0.jar:
   ```cmd
   java -jar mapping-1.5.0.jar
   ```

### Expected Result
The mapping tool does not run and complete any specified operations.