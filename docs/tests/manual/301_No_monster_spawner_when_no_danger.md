# The solution committed to issue no. #301 will not create a monster spawner block if no danger attribute is present in the xml file.

## Tags
#pullRequest_301 #component_rendering #testFile-format_XML #category_output #category_input #category_error_handling

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/301 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/301-put-monsters-and-swords-in-the-high-metric-buildings

### Purpose
 The purpose of this test is to see if a danger attribute is not present for the building in the .xml file of the world, the solution won't create a Minecraft monster spawner block.
(Reference to the mentioned Minecraft spawner object: https://minecraft.fandom.com/wiki/Monster_Spawner)

### Test File
- File supplemented by danger attribute for a building: [[defaultPlacingToRenderingTest.xml]]

### Pre-requisites		
- Java runtime 22
- Java Edition Minecraft 1.20 or above
- Test file inserted inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Navigate to `CodeMetropolis\sources\distro\` folder
2. Open the command line in this folder
3. Run the following command in the folder of rendering-1.5.0.jar:
   ```cmd
   java -jar rendering-1.5.0.jar -i defaultPlacingToRenderingTest.xml -world world
	```
4. Go over this Minecraft world opening guide: [[How to open created Minecraft world]]
5. Find three of the highest buildings in the opened Minecraft world and go into the bottom of them

### Expected Result
You should not be able to find a monster spawner block (or multiple blocks) after checking these high buildings.