# The solution committed to issue no. #292 will not replace blocks under the placed down ladders

## Tags
#pullRequest_292 #component_rendering #category_output #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/292 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/292-place-ladders-in-the-buildings

### Purpose
To test that the solution of issue 292 will not replace the blocks of the building in the row where the ladder is placed.

### Test File
- [[ladderPlacementTestOnStoneBuilding.xml]]

### Pre-requisites
- Java runtime 22
- Java Edition Minecraft 1.20 or above
- Test file inserted inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Navigate to `CodeMetropolis\sources\distro\` folder.
2. Open the command line in this folder.
3. Run the following command in the folder of rendering-1.5.0.jar:
    ```cmd
	java -jar rendering-1.5.0.jar -i ladderPlacementTestOnStoneBuilding.xml -world world 
	```
4. Go over this Minecraft world opening guide: [[How to open created Minecraft world]]
5. Go around the singular stone building in the Minecraft world and examine its outside.
6. Open this world [[292/worldStoneBuilding/level.dat]] in Minecraft the same way is you did in **Step 4**. Disclaimer: not just level.dat the whole worldStoneBuilding directory has to be placed in the saves directory.
7. Repeat **Step 5**.

### Expected Result
The only difference between the buildings in the two worlds should be the ladder placed down by pull request 292's modifications, and the blocks under the ladder are made out of stone.