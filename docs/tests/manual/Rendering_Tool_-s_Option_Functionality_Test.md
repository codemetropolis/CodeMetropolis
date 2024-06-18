# See if the rendering tool -s option works correctly

## Tags
#component_rendering #testFile-format_XML

### Purpose
This test's objective is to verify that the rendering tool -s option enable overwriting existing world.

### Test File
- [[Expected Results/Placing.Test7Result.xml]]
- `TG_005\Test Files\worldTest39`

### Important
- To guarantee correct functionality, use your file paths throughout.  
- Ensure that the necessary Java version is used for each code you use!
- Always keep in mind that "buildable id" will vary.

### Pre-requisites
- Java runtime 22
- Java Edition Minecraft 1.20 launcher
- Command line opened in the folder of rendering-1.5.0.jar (path to the folder `CodeMetropolis\sources\distro\`)
- Test file inserted into `CodeMetropolis\sources\distro\` folder
- "worldTest9" folder inserted into `CodeMetropolis\sources\distro\` folder


### Steps
1. Run
	```cmd
	java -jar rendering-1.5.0.jar -i Placing.Test7Result.xml -world worldTest39 -s
	```
	in rendering-1.5.0.jar command line

### Expected Result
The tool overwrite the existing world folder without asking.