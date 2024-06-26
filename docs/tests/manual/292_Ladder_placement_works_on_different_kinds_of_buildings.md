# The solution committed to issue no. #292 creates ladders on buildings made of different kinds of material

## Tags
#pullRequest_292 #component_rendering #category_output #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/292 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/292-place-ladders-in-the-buildings

### Purpose
To test that the solution for the issue 292 will not just create ladders on the outside of buildings made up of one kind of material (material is meant for the block's type which makes up the majority of the building, for example obsidian, metal, wood, etc.) but rather it works on buildings made up of any kind of block types.

### Test File
- [[ladderPlacementTestOnBuildingsWithDifferentBlockTypes.xml]]

### Pre-requisites
- Java runtime 22
- Java Edition Minecraft 1.20 or above
- Test file inserted inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Navigate to `CodeMetropolis\sources\distro\` folder
2. Open the command line in this folder
3. Run the following command in the folder of rendering-1.5.0.jar:
    ```cmd
	java -jar rendering-1.5.0.jar -i ladderPlacementTestOnBuildingsWithDifferentBlockTypes.xml -world world 
	```
4. Go over this Minecraft world opening guide: [[How to open created Minecraft world]]
5. Go around in the Minecraft world and observe the buildings walls outside

### Expected Result
One row ladder should be placed on every building from its bottom in the garden to the top of it, no matter the material the building is made out of.