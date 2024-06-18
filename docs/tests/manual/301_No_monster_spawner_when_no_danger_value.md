# The solution committed to issue no. #301 will not create a monster spawner block if a danger attribute is present but no value to it.

## Tags
#pullRequest_301 #component_rendering #testFile-format_XML #category_output #category_input #category_error_handling

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/301 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/301-put-monsters-and-swords-in-the-high-metric-buildings

### Purpose
The purpose of this test is to see if a danger attribute is present for the building in the .xml file of the world **but not** a value to it, the solution will not run because the XML format with danger attribute but no value to it is not accepted by the rendering tool.
(Reference to the mentioned Minecraft spawner object: https://minecraft.fandom.com/wiki/Monster_Spawner)

### Test File
- File supplemented by danger attribute for a building: [[monsterSpawnerWithoutDangerValueTest.xml]]

### Pre-requisites	
- Java runtime 22
- Java Edition Minecraft 1.20 or above
- Test file inserted inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Navigate to `CodeMetropolis\sources\distro\` folder
2. Open the command line in this folder
3. Run the following command in the folder of rendering-1.5.0.jar:
   ```cmd
   java -jar rendering-1.5.0.jar -i monsterSpawnerWithoutDangerValueTest.xml -world world
   ```
4. Go over this Minecraft world opening guide: [[How to open created Minecraft world]]
5. Find three of the highest buildings in the opened Minecraft world and go into the bottom of them

### Expected Result
A message is written on the console saying that the XML is not in the correct format.