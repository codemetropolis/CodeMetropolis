# Verify that the program successfully create a minecraft world with simple blocks from the csv file

## Tags
#component_rendering #category_output

### Purpose
This test's objective is to verify that the program successfully create a minecraft world with simple blocks from the csv file.

### Pre-requisites
- Java runtime 22
- minecraft 1.20 java edition


### Steps
1. From "CodeMetropolis/docs/tests/manual/Test Files" copy "worldSimple" folder to CodeMetropolis/sources folder.
2. Run testGenerateSimpleBlocks method from [[TestAllBlockTypesPlacement.java]]
3. Copy the received 'worldSimple' folder to your Minecraft saves folder.
4. Open minecraft 1.20 java edition.
5. Check if the simple blocks appear at specified position.

### Expected Result
In the received world, the simple blocks appear in the specified order:

First row: stone, stone bricks, sandstone, grass, fence, poppy, dandelion, brown mushroom, oak sapling. 
Second row: cobblestone, mossy cobblestone, obsidian, oak log, dark oak log, birch log, oak planks, dark oak planks, block of iron.
Third row: dirt, cut sandstone, red sand, bricks, glass, block of gold, block of diamond.