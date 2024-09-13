# Verify that the program successfully create a minecraft world with all color of banner blocks from the csv file

## Tags
#component_rendering #category_output

### Purpose
This test's objective is to verify that the program successfully create a minecraft world with all types of banner blocks from the csv file.

### Pre-requisites
- Java runtime 22
- minecraft 1.20 java edition


### Steps
1. From "CodeMetropolis/docs/tests/manual/Test Files" copy "worldBannerColor" folder to CodeMetropolis/sources folder.
2. Run testGenerateBannerColorBlocks method from [[TestAllBlockTypesPlacement.java]]
3. Copy the received 'worldBannerColor' folder to your Minecraft saves folder.
4. Open minecraft 1.20 java edition.
5. Check if the correct colored banners appear at the specified place.

### Expected Result
In the received world, the standing banner blocks appear in the following sequence of colors: white, orange, magenta, light blue, yellow, lime, pink, gray, light gray, cyan, purple, blue, brown, green, red, black, in the first row. The similar sequence of colors applies to wall banners as well, which are located in the next row.
