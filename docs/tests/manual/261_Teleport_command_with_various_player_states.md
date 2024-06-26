# Test Teleport Command with Various Player States

## Tags
#pullRequest_261 #category_correct_working #status_not_implemented_functionality 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/261 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/261-create-a-command-for-spigot-that-navigate-a-player-to-the-top-center-of-the-named-object

### Purpose
To validate that the teleport command effectively handles players in different states (e.g., jumping, running, or crouching).

### Test File
- [[teleportCommandTestInSimpleWorld.xml]]

### Pre-requisites
- Spigot server and plugin installed. Follow this guide if needed: [[How to download spigot server and connect it with Minecraft]]
- Java runtime 22
- Java Edition Minecraft 1.20 or above
- Test file inserted inserted into `CodeMetropolis\sources\distro\` folder

### Steps
1. Navigate to `CodeMetropolis\sources\distro\` folder.
2. Open the command line in this folder.
3. Run the following command in the folder of rendering-1.5.0.jar:
   ```cmd
	java -jar rendering-1.5.0.jar -i teleportCommandTestInSimpleWorld.xml -world world 
	```
4. Go over this Minecraft world opening guide: [[How to open created Minecraft world]]
5. Fly on the top of very high building.
6. Start falling down from it.
7. Quickly press the *T* key on your keyboard.
8. Type `/tpToBuilding String getOutputFile()`.

### Expected Result
The teleport command consistently navigates players to the desired location regardless of their action at the time of execution, without causing glitches or unexpected behaviors.