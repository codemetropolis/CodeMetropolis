# The block created by the solution for issue no. #301 is a Minecraft monster spawner block.

## Tags
#pullRequest_301 #component_rendering #testFile-format_XML #category_output #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/301 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/301-put-monsters-and-swords-in-the-high-metric-buildings

### Purpose
 The purpose of this test is to see if building has danger attribute, then the created block in a building is a Minecraft monster spawner block. (Reference to the mentioned Minecraft spawner object: https://minecraft.fandom.com/wiki/Monster_Spawner)

### Test File
- File supplemented by danger attribute for a building: [[oneMonsterSpawnerGeneralTest.xml]]

### Pre-requisites	
- Java runtime 22
- Java Edition Minecraft 1.20 or above
- Test file inserted inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Navigate to `CodeMetropolis\sources\distro\` folder
2. Open the command line in this folder
3. Run the following command in the folder of rendering-1.5.0.jar:
	```cmd
	java -jar rendering-1.5.0.jar -i oneMonsterSpawnerGeneralTest.xml -world world
	```
4. Go over this Minecraft world opening guide: [[How to open created Minecraft world]]
5. In the game press **Esc** on your keyboard
6. Press **Open to LAN** option
7. Set the **Allow Cheats** option to **True**
8. Press **Start LAN World**
9. Press the *T* key on your keyboard
10. Type `/tp 76 18 76`
11. Check that the created block is a Minecraft monster spawner block

### Expected Result
The block that should appear at the bottom of the building is a Minecraft monster spawner block.