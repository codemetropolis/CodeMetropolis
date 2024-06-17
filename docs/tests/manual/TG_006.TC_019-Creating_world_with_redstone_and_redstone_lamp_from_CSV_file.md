# Verify that the program successfully create a minecraft world with a redstone block and the a redstone lamp on it from the csv file

## Tags
#component_rendering #category_output

### Purpose
This test's objective is to verify that the program successfully create a minecraft world with a redstone block and the a redstone lamp on it from the csv file.

### Pre-requisites
- Java runtime 22
- minecraft 1.20 java edition


### Steps
1. From "CodeMetropolis/docs/tests/manual/Test Files" copy "worldRedstone" folder to CodeMetropolis/sources folder.
2. Run testGenerateRedstoneLampBlocks method from [[TestAllBlockTypesPlacement.java]]
3. Copy the received 'worldRedstone' folder to your Minecraft saves folder.
4. Open minecraft 1.20 java edition.
5. Check if the redstone and the redstone lamp appears at the specified place.

### Expected Result
In the received world, a redstone lamp is placed on top of a redstone block, emitting light.