# The solution committed to issue no. #301 will not change the difficulty of the minecraft world to peacful preventing to spawn monsters.

## Tags
#pullRequest_301 #component_rendering #testFile-format_XML #category_output #category_input #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/301 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/301-put-monsters-and-swords-in-the-high-metric-buildings

### Purpose
 The purpose of this test is to check that solution committed to issue no. #301 will not change the created Minecraft world's difficulty option to *Peaceful* thus preventing monsters to spawn in the highest buildings. (Minecraft's difficulty options: Peaceful, Easy, Normal, Hard; only Peaceful prevents monster spawn)

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
5. Press **Esc** on the keyboard
6. Select the **Options...** menu item
7. Under the **Difficulty** menu item, check the set option

### Expected Result
The set difficulty options should be Easy, Normal, Hard but NOT Peaceful.