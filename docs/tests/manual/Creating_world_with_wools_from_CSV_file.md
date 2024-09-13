# Verify that the program successfully create a minecraft world with all types of wool blocks from the csv file

## Tags
#component_rendering #category_output

### Purpose
This test's objective is to verify that the program successfully create a minecraft world with all types of wool blocks from the csv file.

### Pre-requisites
- Java runtime 22
- minecraft 1.20 java edition


### Steps
1. From "CodeMetropolis/docs/tests/manual/Test Files" copy "worldWool" folder to CodeMetropolis/sources folder.
2. Run testGenerateWoolBlocks method from [[TestAllBlockTypesPlacement.java]]
3. Copy the received 'worldWool' folder to your Minecraft saves folder.
4. Open minecraft 1.20 java edition.
5. Check if the correct colored wools appear at the specified place.

### Expected Result
In the received world, the wool blocks appear in the following sequence of colors:
white, orange, magenta, light blue, yellow, lime, pink, gray, light gray in first row, and
cyan, purple, blue, brown, green, red, black.