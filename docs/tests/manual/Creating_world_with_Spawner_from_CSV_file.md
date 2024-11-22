# Verify that the program successfully create a minecraft world with a spawner from the csv file

## Tags
#component_rendering #category_output

### Purpose
This test's objective is to verify that the program successfully create a minecraft world with a spawner from the csv file.

### Pre-requisites
- Java runtime 22
- minecraft 1.20 java edition


### Steps
1. From "CodeMetropolis/docs/tests/manual/Test Files" copy "worldSpawner" folder to CodeMetropolis/sources folder.
2. Run testGenerateSpawnerBlocks method from [[TestAllBlockTypesPlacement.java]]
3. Copy the received 'worldSpawner' folder to your Minecraft saves folder.
4. Open minecraft 1.20 java edition.
5. Check if the spawner appears at the specified place.
6. Press "Esc" button to get to the menu.
7. Click "Open to LAN" button.
8. Change "Allow Cheats:" to "ON".
9. Press "T" to start typing.
10. Type `/time set midnight`.
11. Check if the spawner starts to create zombies.

### Expected Result
In the received world, the spawner blocks appears, and at midnight the spawner starts to create zombies.
