# Verify that the program successfully create a minecraft world with all orientation of torch blocks from the csv file

## Tags
#component_rendering #category_output

### Purpose
This test's objective is to verify that the program successfully create a minecraft world with all orientation of torch blocks from the csv file.

### Pre-requisites
- Java runtime 22
- minecraft 1.20 java edition


### Steps
1. From "CodeMetropolis/docs/tests/manual/Test Files" copy "worldTorch" folder to CodeMetropolis/sources folder.
2. Run testGenerateTorchBlocks method from [[TestAllBlockTypesPlacement.java]]
3. Copy the received 'worldTorch' folder to your Minecraft saves folder.
4. Open minecraft 1.20 java edition.
5. Press "F3" button.
6. Check if the torches appear at the specified place with the correct orientation.

### Expected Result
In the received world, torches appear at the specified locations with the appropriate orientations:
- At positions 2,61,1 and 3,61,1, they face west.
- At positions 4,61,1 and 5,61,1, they face east.
- At positions 1,61,2 and 1,61,3, they face north.
- At positions 1,61,4 and 1,61,5, they face south.