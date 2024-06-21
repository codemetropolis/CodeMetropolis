# The solution committed to issue no. #301 will only create monster spawner blocks on the inside of the buildings and nowhere else outside.

## Tags
#pullRequest_301 #component_rendering #testFile-format_XML #category_output #category_input #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/301 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/301-put-monsters-and-swords-in-the-high-metric-buildings

### Purpose
 The purpose of this test is to check that solution committed to issue no. #301 will not create monster spawner blocks anywhere outside of the buildings (including in the gardens, top of buildings, etc.), only inside of them. (Reference to the mentioned Minecraft spawner block: https://minecraft.fandom.com/wiki/Monster_Spawner)

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
5. Roam around the city and check the outsides of buildings, including gardens.
6. Check the highest buildings' lowest floors .

### Expected Result
No monster spawner should appear anywhere besides the inside of high buildings.