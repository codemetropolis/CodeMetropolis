# The solution for issue no. #301 creates a monster spawner in the highest buildings.

## Tags
#pullRequest_301 #component_rendering #testFile-format_XML #category_output #category_input #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/301 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/301-put-monsters-and-swords-in-the-high-metric-buildings

### Purpose
 The purpose of this test is to check if a monster spawner is created in the generated Minecraft world's highest buildings' according to the committed solution to issue no. #301 (Attribute example which is required for the spawner to be made: `<attribute name="danger" value="5"/>` - This has already been put into the Test file, it is just here to demonstrate how the attribute looks like)

### Test File
- File supplemented by danger attribute for a building: [[threeMonsterSpawnerGeneralTest.xml]]

### Pre-requisites	
- Java runtime 22
- Java Edition Minecraft 1.20 or above
- Command line opened in the folder of rendering-1.5.0.jar
- Test file inserted inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Run the following command in the folder of rendering-1.5.0.jar:
	```cmd
	java -jar rendering-1.5.0.jar -i threeMonsterSpawnerGeneralTest.xml -world world
	```
2. Copy the created world
3. Go over this Minecraft world opening guide - [[How to open created Minecraft world]]
4. Find the three highest buildings in the opened world and go into the bottom of them
5. Find three of the smallest buildings in the opened world and go into the bottom of them

### Expected Result
Only the highest buildings' bottom floors' should contain monster spawner blocks.