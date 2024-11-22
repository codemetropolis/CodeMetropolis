# The solution committed to issue no. #292 will place down ladders on the outside corners of buildings' walls

## Tags
#pullRequest_292 #component_rendering #category_output #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/292 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/292-place-ladders-in-the-buildings

### Purpose
To test that the solution of issue 292 will place down ladders on the outside walls of the buildings, specifically at one of the building's corner and no other supporting block will be placed down for the ladder or it wont be floating in the air.

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
5. Go around in the Minecraft world and find ladders on the outside corners' of buildings.
6. Check the ladders placement on the buildings.

### Expected Result
The ladders placed down for each building are located on the outer walls of buildings, specifically at one of its corner, as seen in the example image in the issue description.