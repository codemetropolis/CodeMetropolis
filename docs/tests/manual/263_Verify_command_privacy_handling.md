# Test How the Command Handles Player Privacy

## Tags
#pullRequest_263 #category_correct_working #status_not_implemented_functionality 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/263 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/263-create-a-command-that-lists-sub-buildings--names-of-a-current-building-in-the-minecraft-chat

### Purpose
To ensure that the output of the command is only visible to the player who issued the command.

### Test File
- [[subBuildingCommandTestInSimpleWorld.xml]]

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
	java -jar rendering-1.5.0.jar -i subBuildingCommandTestInSimpleWorld.xml -world world 
	```
4. Go over this Minecraft world opening guide: [[How to open created Minecraft world]]
5. Go into any big building in the generated Minecraft world.
6. Press the *T* key on your keyboard
7. Type `/sub-buildings`

### Expected Result
Only the player who executed the command sees the output in their chat.
