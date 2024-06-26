# The solution committed to issue no. #292 will only place down Minecraft ladder type blocks

## Tags
#pullRequest_292 #component_rendering #category_output #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/292 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/292-place-ladders-in-the-buildings

### Purpose
To test that the solution of issue 292 will place down only Minecraft ladder (https://minecraft.wiki/w/Ladder) type blocks and not anything else to the outside walls of buildings.

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
5. Check the stone building's walls in the Minecraft world.

### Expected Result
On the wall of the stone building a row of ladder type blocks should appear.