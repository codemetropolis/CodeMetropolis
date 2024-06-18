# See if the rendering tool converts the TG_005.TC_027-Placing expected result xml successfully.

## Tags
#component_rendering #testFile-format_XML #category_output

### Purpose
This test's objective is to verify that the rendering tool converted the TG_005.TC_027-Placing expected result xml appropriately.

### Test Project
- A freshly created Maven project to which we added both functions and variables before using the converter, mapping and placing tool to convert it.
- [[Expected Results/Placing.Test6Result.xml]]

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
	java -jar rendering-1.5.0.jar -i Placing.Test6Result.xml -world world
	```
	in rendering-1.5.0.jar command line
2. Open the received world with a Java Edition Minecraft 1.20 launcher

### Expected Result
The result in the received world is a combination of the [[Rendering_Tool_Conversion_Verification_for_Primitive_Variables]] and [[Rendering_Tool_Conversion_Verification_for_Functions]] worlds.