# Check issue no. #176's solution if it gives back a detailed message on the missing parameters

## Tags
#pullRequest_176 #component_mapping #category_output 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/176 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/176-mapping-without-any-parameters-is-not-handled-correctly

### Purpose
The purpose of this test is to see if the solution committed to issue no. #176 gives back a detailed message on the missing parameters.

### Pre-requisites
- Java runtime 22
- command line opened in the folder of mapping-1.5.0.jar

### Steps
1. Run the following command in the folder of mapping-1.5.0.jar:
   ```cmd
   java -jar mapping-1.5.0.jar
   ```

### Expected Result
The tool writes a message on the console about what kind of parameters can be used with the mapping tool.