# The monster spawner block created by the solution for issue no. #301 will correspond to the danger attribute's value.

## Tags
#pullRequest_301 #component_rendering #testFile-format_XML #category_output #category_input #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/301 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/301-put-monsters-and-swords-in-the-high-metric-buildings

### Purpose
 The purpose of this test is to see if building that has a danger attribute and a value, will have monster spawners created for it, and its numbers will correspond to the attribute's value. (Reference to the mentioned Minecraft spawner object: https://minecraft.fandom.com/wiki/Monster_Spawner)

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
4. Download and install [NBTExplorer application](https://github.com/jaquadro/NBTExplorer) from here: https://github.com/jaquadro/NBTExplorer/releases
5. Go over this Minecraft world opening guide: [[How to open created Minecraft world]]
6. In the game press **Esc** on your keyboard
7. Press **Open to LAN** option
8. Set the **Allow Cheats** option to **True**
9. Press **Start LAN World**
10. Press the *T* key on your keyboard
11. Type `/tp x y z`
   In the place of x y z place the following coordinates:
   - Location 1: `76 18 76`
   - Location 2: `28 18 78`
   - Location 3: `28 18 29`
12. Check that a spawner exists on each coordinate
13. Open **NBTExplorer**
14. Click once on the world which the rendering module previously created
15. Press **Ctrl + F**
16. In the **Value:** field type `minecraft:mob_spawner` and press Find
17. Check the X, Y, Z coordinates of the found spawner if it matches one of the above location
18. Check the value of the **MaxNearbyEntities:**
19. Open **threeMonsterSpawnerGeneralTest.xml** in a text editor and search for the word **danger**

### Expected Result
The value of the danger attribute in the XML file should correspond to the max spawnable entities by the spawner (MaxNearbyEntities value).