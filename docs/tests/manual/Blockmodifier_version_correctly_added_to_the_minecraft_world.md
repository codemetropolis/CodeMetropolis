# See if the correct version is shown when you want to open the minecraft world.

## Tags
#component_block-modifier #category_correct_working #category_output 

### Purpose
This test's objective is to verify that the Blockmodifier correctly adds the version to the minecraft world.

### Test Project
- [[Expected Results/Placing.Test2Result.xml]]

### Important
- To guarantee correct functionality, use your file paths throughout.  
- Ensure that the necessary Java version is used for each code you use!
- Always keep in mind that "buildable id" will vary.

### Pre-requisites
- Java runtime 22
- Java Edition Minecraft 1.20 launcher
- Command line opened in the folder of rendering-1.5.0.jar (path to the folder `CodeMetropolis\sources\distro\`)
- Test file inserted into `CodeMetropolis\sources\distro\` folder


### Steps
1. Run
	```cmd
	java -jar rendering-1.5.0.jar -i Placing.Test2Result.xml -world world
	```
	in rendering-1.5.0.jar command line
2. Open the Java Edition Minecraft 1.20 launcher
3. Check if the received minecraft world shows the version is 1.20

### Expected Result
The received minecraft world shows that the supported minecraft version is 1.20.