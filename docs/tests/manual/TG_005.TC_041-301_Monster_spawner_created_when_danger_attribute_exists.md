# The solution for issue no. #301 creates a monster spawner if a building has danger attribute.

## Tags
#pullRequest_301 #component_rendering #testFile-format_XML #category_output #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/301 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/301-put-monsters-and-swords-in-the-high-metric-buildings

### Purpose
 The purpose of this test is to see if the committed solution to issue no. #301 creates a monster spawner into the **Minecraft world** if a danger attribute is present in **threeMonsterSpawnerGeneralTest.xml**, in other words danger attribute is provided to the building. (Attribute example: `<attribute name="danger" value="7"/>` - This has already been put into the Test file, it is just here to demonstrate how the attribute looks like)

### Test File
- File supplemented by danger attribute for a building: [[threeMonsterSpawnerGeneralTest.xml]]

### Pre-requisites
- Java runtime 22
- Java Edition Minecraft 1.20 or above
- Command line opened in the folder of rendering-1.5.0.jar (path to the folder `CodeMetropolis\sources\distro\`)
- Test file inserted inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Run the following command in the folder of rendering-1.5.0.jar:
	```cmd
	java -jar rendering-1.5.0.jar -i threeMonsterSpawnerGeneralTest.xml -world world
	```
2. Go over this Minecraft world opening guide: [[How to open created Minecraft world]]
3. In the game press **Esc** on your keyboard
4. Press **Open to LAN** option
5. Set the **Allow Cheats** option to **True**
6. Press **Start LAN World**
7. Press the *T* key on your keyboard
8. Type `/tp x y z`
   In the place of x y z place the following coordiantes:
   - Location 1: `76 18 76`
   - Location 2: `28 18 78`
   - Location 3: `28 18 29`
9. Check that there is a monster spawner on the floor

### Expected Result
A monster spawner block appears in all of those three building's lowest floor (in the cellar).