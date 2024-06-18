# The solution committed to issue no. #301 places weapons inside the chest blocks around.

## Tags
#pullRequest_301 #component_rendering #testFile-format_XML #category_output #category_input #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/301 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/301-put-monsters-and-swords-in-the-high-metric-buildings

### Purpose
The purpose of this test is to check that solution committed to issue no. #301 will  put Minecraft weapons (https://minecraft.fandom.com/wiki/Weapon) in the chest blocks (https://minecraft.fandom.com/wiki/Chest) around the monster spawner blocks (https://minecraft.fandom.com/wiki/Monster_Spawner) in the Minecraft world's highest buildings' at their lowest floors.

### Test File
- File supplemented by danger attribute for a building: [[threeMonsterSpawnerGeneralTest.xml]]

### Pre-requisites		
- Java runtime 22
- Java Edition Minecraft 1.20 or above
- Test file inserted inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Navigate to `CodeMetropolis\sources\distro\` folder
2. Open the command line in this folder
3. Run the following command in the folder of rendering-1.5.0.jar:
	```cmd
	java -jar rendering-1.5.0.jar -i threeMonsterSpawnerGeneralTest.xml -world world
	```
4. Go over this Minecraft world opening guide: [[How to open created Minecraft world]]
5. In the game press **Esc** on your keyboard
6. Press **Open to LAN** option
7. Set the **Allow Cheats** option to **True**
6. Press **Start LAN World**
8. Press the *T* key on your keyboard
9. Type `/tp x y z`
   In the place of x y z place the following coordiantes:
   - Location 1: `76 18 76`
   - Location 2: `28 18 78`
   - Location 3: `28 18 29`
10. Break 3x3 blocks behind the doors that are placed around the monster spawner and find the chests
11. Open the chests

### Expected Result
The chests that are around the monster spawners contain diamond swords.