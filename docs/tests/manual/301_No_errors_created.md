# The solution of issue no. #301's solution does not create errors written on the command line.

## Tags
#pullRequest_301 #component_rendering #category_output #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/301 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/301-put-monsters-and-swords-in-the-high-metric-buildings

### Purpose
 The purpose of this test is to check immaculacy of the code committed to issue no. #301. In other words to check that the modified code won't create errors when ran through command line commands (the errors would appear on the command line and the app would stop).

### Pre-requisites
- Java runtime 22
- Apache Maven 3.8.0 or above
- Git bash with latest version
- Open four command lines, one in each of the following files' folders: <u>converter-1.5.0.jar</u> /  <u>mapping-1.5.0.jar</u> /  <u>placing-1.5.0.jar</u> /  <u>rendering-1.5.0.jar</u>
- Valid test .graph file for converter-1.5.0.jar
- Valid test .xml file for mapping-1.5.0.jar

### Steps
1. Run the following command in the folder of converter-1.5.0.jar:
	```cmd
	java -jar converter-1.5.0.jar -t sourcemeter -s "[CodeMetropolis project folder path]
	```
	[[Legends and other additions]]
	```cmd
	\examples\mapping\sourcemeter_mapping_example_2_1.xml"
	```
2. Run the following command in the folder of mapping-1.5.0.jar:
	```cmd
	java -jar mapping-1.5.0.jar -i converterToMapping.xml -m "[CodeMetropolis project folder path]
	```
	[[Legends and other additions]]
	```cmd
	\examples\mapping\codemetropolis-toolchain-rendering.graph"
	```
3. Run the following command in the folder of placing-1.5.0.jar:
	```cmd
	java -jar placing-1.5.0.jar -i mappingToPlacing.xml
	```
4. Run the following command in the folder of rendering-1.5.0.jar:
	```cmd
	java -jar rendering-1.5.0.jar -i placingToRendering.xml -world world
	```

### Expected Result
Each command line command run gives back a **"Process finished with exit code 0"** message
not a **"Process finished with exit code 1"** message and no errors at all appear on the command line.