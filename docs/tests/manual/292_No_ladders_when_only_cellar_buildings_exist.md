# The solution committed to issue no. #292 will not place down ladders when only cellar buildings exist

## Tags
#pullRequest_292 #component_rendering #category_output #category_error_handling

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/292 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/292-place-ladders-in-the-buildings

### Purpose
To test that the solution of issue 292 will not place down ladders in the Minecraft world anywhere if the world is made up of only cellars.

### Test File
- [[ladderPlacementTestOnCellarOnlyWorld.xml]]

### Pre-requisites
- Java runtime 22
- Java Edition Minecraft 1.20 or above
- Test file inserted inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Navigate to `CodeMetropolis\sources\distro\` folder.
2. Open the command line in this folder.
3. Run the following command in the folder of rendering-1.5.0.jar:
    ```cmd
	java -jar rendering-1.5.0.jar -i ladderPlacementTestOnCellarOnlyWorld.xml -world world 
	```
4. Go over this Minecraft world opening guide: [[How to open created Minecraft world]]
5. Go around in the Minecraft and check if a ladder row or blocks exist.

### Expected Result
No ladders exist around the cellar or anywhere near to it in the Minecraft world.