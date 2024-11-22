# Check issue no. #225's solution if it provides detailed information when using -v switch in a different argument order.

## Tags
#pullRequest_225 #component_converter #category_output

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/225 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/225-extending-the-sourcemeter-converter-with-verbose-mode

### Purpose
The purpose of this test is to check if the solution commited to issue no. #225 works as it should be according to the issue's description, thus gives back more information to the user, while the argument order of the switch is swapped *-t* switch.

### Test File
- [[codemetropolis-toolchain-commons-valid.graph]]

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of converter-1.5.0.jar
- A *codemetropolis-toolchain-commons-valid.graph* file exists in the converter-1.5.0.jar's folder. 

### Steps
1. Run the following command in the folder of converter-1.5.0.jar:
	```cmd
	java -jar converter-1.5.0.jar -v -s codemetropolis-toolchain-commons-valid.graph -t sourcemeter
	```

### Expected Result
The tool gives back a more detailed answer about the workings of itself, than when ran without *-v* switch. Example: 
`Verbose mode enabled.`
`Converter: Converting input to CDF format...`
`Creating elements.`
`Creating empty graph.`
. . .