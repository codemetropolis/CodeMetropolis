# Check issue no. #163's solution that the provided help message only contains the correct arguments for the tool

## Tags
#pullRequest_163 #component_converter #category_output

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/163 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/163-conversion-without-any-parameters-is-not-handled-correctly

### Purpose
The purpose of this test is to see if the solution committed to issue no. #163 will not give back any not converter related extra arguments or have some missing, when a message is given back after running the converter tool without arguments.

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of converter-1.5.0.jar

### Steps
1. Run the following command in the folder of converter-1.5.0.jar:
   ```cmd
   java -jar converter-1.5.0.jar
   ```
2. Compare the arguments that appear in the message given back with: `-t <type> -s <source> [-o <outputFile>] [-p <parameters>]`

### Expected Result
The arguments that appear in the message are be similar to the ones provided above.