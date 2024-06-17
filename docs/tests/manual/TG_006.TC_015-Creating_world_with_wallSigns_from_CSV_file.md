# Verify that the program successfully create a minecraft world with all orientation of wallSign blocks from the csv file

## Tags
#component_rendering #category_output

### Purpose
This test's objective is to verify that the program successfully create a minecraft world with all orientation of wallSign blocks from the csv file.

### Pre-requisites
- Java runtime 22
- minecraft 1.20 java edition


### Steps
1. From "CodeMetropolis/docs/tests/manual/Test Files" copy "worldWallSign" folder to CodeMetropolis/sources folder.
2. Run testGenerateWallSignBlocks method from [[TestAllBlockTypesPlacement.java]]
3. Copy the received 'worldWallSIgn' folder to your Minecraft saves folder.
4. Open minecraft 1.20 java edition.
5. Check if the wallSigns appear at the specified place with the correct orientation, and text.

### Expected Result
In the received world, the wallsign blocks appear in the correct orientation, accompanied by corresponding text, all in a single row.
