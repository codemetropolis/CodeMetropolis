# Test Teleport Command During Combat

## Tags
#pullRequest_261 #category_correct_working #status_not_implemented_functionality 

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/261 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/261-create-a-command-for-spigot-that-navigate-a-player-to-the-top-center-of-the-named-object

### Purpose
To test the teleport command's functionality and safety when used by players engaged in combat scenario.

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
5. Press the *E* key on your keyboard to open inventory.
6. Search for the zombie spawn egg.
7. Place down a couple of zombies into the world.
8. Press **Esc** on your keyboard
9. Press **Open to LAN** option
10. Set the **Allow Cheats** option to **True**
11. Press **Start LAN World**
12.  Type `/gamemode survival`
13. Let the zombies hit you once.
14. Press the *T* key on your keyboard.
15. Type `/tpToBuilding String getOutputFile()`.

### Expected Result
The command works even when the player gives it during combat and is not affected by that player state.