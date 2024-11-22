# The solution committed to issue no. #292 will not place down extra blocks in the Minecraft world near  ladders

## Tags
#pullRequest_292 #component_rendering #category_output #category_error_handling

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/292 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/292-place-ladders-in-the-buildings

### Purpose
To test that the solution of issue 292 will not place down extra blocks for the ladders. Meaning this feature only places down ladders in the Minecraft world onto the walls of buildings and no other blocks are placed near to them.

### Test File
- [[ladderPlacementTestOnBuildingsWithDifferentBlockTypes.xml]]

### Pre-requisites
- Java runtime 22
- Java Edition Minecraft 1.20 or above
- Test file inserted inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Navigate to `CodeMetropolis\sources\distro\` folder.
2. Open the command line in this folder.
3. Run the following command in the folder of rendering-1.5.0.jar:
    ```cmd
	java -jar rendering-1.5.0.jar -i ladderPlacementTestOnBuildingsWithDifferentBlockTypes.xml -world world 
	```
4. Go over this Minecraft world opening guide: [[How to open created Minecraft world]]
5. Go around in the Minecraft and check the outside walls of buildings.

### Expected Result
Besides the ladders on the walls of buildings no other blocks are created near to them.