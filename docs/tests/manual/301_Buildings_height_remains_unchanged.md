#  The solution committed to issue no. #301 will not change the building's height with danger attribute when adding a a monster spawner block.

## Tags
#pullRequest_301 #component_rendering #testFile-format_XML #category_output #category_input #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/301 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/301-put-monsters-and-swords-in-the-high-metric-buildings

### Purpose
The purpose of this test is to check that solution committed to issue no. #301 will not change the height of a building (which possesses a danger attribute and thus a monster spawner block should be created in it) only creates the necessary block/blocks into the building.
These necessary blocks should include a Minecraft monster spawner block (https://minecraft.fandom.com/wiki/Monster_Spawner) and may include other kinds of blocks, which do not prevent the spawn of hostile entities, if the issue's solvers deem them necessary.

### Test File
1. File without danger attribute for a building: [[defaultPlacingToRenderingTest.xml]] - When the project is ran with this it creates a world *without* a monster spawner block in it.
2. File supplemented by danger attribute for a building: [[oneMonsterSpawnerGeneralTest.xml]] - When the project is ran with this it creates a world *with* a monster spawner block in it.

### Pre-requisites	
- Java runtime 22
- Java Edition Minecraft 1.20 or above
- Test file inserted inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Navigate to `CodeMetropolis\sources\distro\` folder
2. Insert the first test file into the folder
3. Open the command line in this folder
4. Run the following command in the folder of rendering-1.5.0.jar:
	```cmd
	java -jar rendering-1.5.0.jar -i defaultPlacingToRenderingTest.xml -world world
	```
5. Go over this Minecraft world opening guide: [[How to open created Minecraft world]]
6. Go around the world and search for 2 highest buildings
7. Count the height of the buildings in blocks and write them down for yourself somewhere
8. Navigate again to `CodeMetropolis\sources\distro\` folder
9. Remove the first test file then insert the second one into the folder
10. Run the following command in the folder of rendering-1.5.0.jar:
	```cmd
	java -jar rendering-1.5.0.jar -i oneMonsterSpawnerGeneralTest.xml -world world
	```
11. Again open the created Minecraft world using this guide [[How to open created Minecraft world]].
12. Go around the world and search for 2 highest buildings
13. Count the height of the buildings in blocks and write them down for yourself somewhere

### Expected Result
When comparing buildings from the the two Minecraft worlds, the world created from a file with danger attribute in it will not have buildings that are higher or smaller than the ones appearing the Minecraft world created with the file that has no danger attribute.