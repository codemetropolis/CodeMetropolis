# Verify that the program successfully create a minecraft world with all orientation of banner blocks from the csv file

## Tags
#component_rendering #category_output

### Purpose
This test's objective is to verify that the program successfully create a minecraft world with all orientation of banner blocks from the csv file.

### Pre-requisites
- Java runtime 22
- minecraft 1.20 java edition


### Steps
1. From "CodeMetropolis/docs/tests/manual/Test Files" copy "worldBannerOrientation" folder to CodeMetropolis/sources folder.
2. Run testGenerateBannerOrientationBlocks method from [[TestAllBlockTypesPlacement.java]]
3. Copy the received 'worldBannerOrientation' folder to your Minecraft saves folder.
4. Open minecraft 1.20 java edition.
5. Check if the correct orientated white banners appear at the specified place.

### Expected Result
In the received world, the standing banner blocks appear in the following sequence of orientation: 
South, SouthWest, West, NorthWest, North, NorthEast, East, SouthEast.
