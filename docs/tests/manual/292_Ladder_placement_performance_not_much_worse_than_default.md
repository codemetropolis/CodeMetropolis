# The solution committed to issue no. #292 wont worsen the performance of rendering tool

## Tags
#pullRequest_292 #component_rendering #category_output #category_correct_working

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/292 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/292-place-ladders-in-the-buildings

### Purpose
To test that the solution of issue 292 will not worsen the performance of the rendering tool by much, or in other words the ladder placing feature does cause the rendering tool to take a lot longer or to take a lot more resources from the PC it is ran on.

### Test Files
- [[ladderPlacementOnDefaultCodeMetropolisWorld.xml]]
- [[rendering_without_ladder_placement-1.5.0.jar]]

### Pre-requisites
- Java runtime 22
- Java Edition Minecraft 1.20 or above
- ladderPlacementOnDefaultCodeMetropolisWorld.xml test file inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Place the *ladderPlacementOnDefaultCodeMetropolisWorld.xml* and *rendering_without_ladder_placement-1.5.0.jar* files in an empty directory.
2. Run the following command in folder:
   ```cmd
	java -jar rendering_without_ladder_placement-1.5.0.jar -i ladderPlacementOnDefaultCodeMetropolisWorld.xml -world world 
	```
3. Observe the performance of the rendering tool which does not place down ladders.
4. Navigate to `CodeMetropolis\sources\distro\` folder
5. Open the command line in this folder
6. Run the following command in the folder of rendering-1.5.0.jar:
    ```cmd
	java -jar rendering-1.5.0.jar -i ladderPlacementOnDefaultCodeMetropolisWorld.xml -world world 
	```
7. Observe the performance of the rendering tool which should place down the ladders.

### Expected Result
If there is a difference in the time the two tool took to run or in the resources taken up from the target system it is very minimal and does not worsen the experience of using the tool where ladders are placed down.