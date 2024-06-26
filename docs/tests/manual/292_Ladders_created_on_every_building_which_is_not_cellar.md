# The solution committed to issue no. #292 will place down ladders on buildings that have floors and not only cellars

## Tags
#pullRequest_292 #component_rendering #category_output #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/292 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/292-place-ladders-in-the-buildings

### Purpose
To test that the solution of issue 292 will place down ladders on the outside walls of the buildings that are not only made of cellars and have floors built starting from the garden.

### Test File
- [[ladderPlacementTestOnSimpleWorld.xml]]

### Pre-requisites
- Java runtime 22
- Java Edition Minecraft 1.20 or above
- Test file inserted inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Navigate to `CodeMetropolis\sources\distro\` folder.
2. Open the command line in this folder.
3. Run the following command in the folder of rendering-1.5.0.jar:
    ```cmd
	java -jar rendering-1.5.0.jar -i ladderPlacementTestOnSimpleWorld.xml -world world 
	```
4. Go over this Minecraft world opening guide: [[How to open created Minecraft world]]
5. Go around in the Minecraft world and find the 3 cellar only buildings.
6. Go around in the Minecraft world and find ladders on the outside corners' of buildings which are not cellar only.

### Expected Result
Ladders placed down should only exist for buildings which have upper parts meaning they are not cellar only but have a single or multiple floors built from the garden.