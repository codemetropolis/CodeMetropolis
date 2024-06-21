# The created monster spawner block by the solution committed to issue no. #301 will only spawn zombies or other enemy mobs not passive ones.

## Tags
#pullRequest_301 #component_rendering #testFile-format_XML #category_output #category_input #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/301 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/301-put-monsters-and-swords-in-the-high-metric-buildings

### Purpose
 The purpose of this test is to see that the created monster spawner block by solution committed to issue no. #301 will only spawn down hostile enemy mobs (List to these hostile mobs: https://minecraft.fandom.com/wiki/Category:Hostile_mobs) and not passive mobs (List to these passive mobs: https://minecraft.fandom.com/wiki/Category:Passive_mobs).

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
8. Press **Start LAN World**
9. Press the *T* key on your keyboard
10. Type `/tp x y z`
   In the place of x y z place the following coordiantes:
   - Location 1: `76 18 76`
   - Location 2: `28 18 78`
   - Location 3: `28 18 29`
11. Press T in Minecraft and type `/set time 19000` and wait a bit. This set the time of the day to night so hostile mobs will spawn faster
12. Repeat the 11th step at each location

### Expected Result
The mobs that appear in these buildings should be zombies or other hostile mobs.