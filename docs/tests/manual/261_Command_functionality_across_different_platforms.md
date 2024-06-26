# Test Command Functionality Across Different Platforms

## Tags
#pullRequest_261 #category_output #category_correct_working #status_not_implemented_functionality 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/261 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/261-create-a-command-for-spigot-that-navigate-a-player-to-the-top-center-of-the-named-object

### Purpose
To confirm the teleport command's functionality on different operating systems.

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
5. Press the *T* key on your keyboard.
6. Type `/tpToBuilding String getOutputFile()`.
7. Change operating system to a different one you used for the above steps.
8. Repeat the Steps from 1. to 7. on the other operating system. (Minecraft and CodeMetropolis may need to be installed separately on the other system which is not covered in this guide)

### Expected Result
The teleport command works on both operating system you chose to run this plugin on.