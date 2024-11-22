package codemetropolis.toolchain.rendering.model.building;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.model.BlockType;
import codemetropolis.toolchain.commons.model.property.BlockFacingTorch;
import codemetropolis.toolchain.commons.model.property.Orientation4;
import codemetropolis.toolchain.commons.model.property.OrientationDoor;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.Pattern;
import codemetropolis.toolchain.rendering.model.pattern.RandomPattern;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.*;
import codemetropolis.toolchain.rendering.util.Orientation;

import java.util.LinkedList;

import static codemetropolis.toolchain.commons.model.BlockType.*;

public class Floor extends Building {

    public Floor(Buildable innerBuildable) throws BuildingTypeMismatchException {
        super(innerBuildable);

        if (innerBuildable.getType() != Type.FLOOR && innerBuildable.getType() != Type.CELLAR)
            throw new BuildingTypeMismatchException(innerBuildable.getType(), getClass());

        primitives.addAll(prepareWalls());
        primitives.addAll(prepareStairs());
        primitives.addAll(prepareDoor());
        primitives.addAll(prepareSigns());
        if (!innerBuildable.hasAttribute("danger")) {
            primitives.addAll(prepareTorches());
        } else {
            primitives.addAll(prepareSpawner());
            primitives.addAll(prepareChest());
        }
    }

    /**
     * Prepares a spawner for the buildable structure.
     *
     * This method creates a spawner block with the specified attributes and adds it to the list of spawners.
     * The spawner block is positioned at the center of the structure, with the specified danger attribute.
     *
     * @return A LinkedList containing the prepared spawner as a Primitive object.
     */
    protected LinkedList<Primitive> prepareSpawner() {
        LinkedList<Primitive> spawners = new LinkedList<>();
        SingleBlock spawner = new SingleBlock(MOB_SPAWNER, position.translate(new Point(center.getX(),
                0, center.getZ())), innerBuildable.getAttributeValue("danger"));
        spawners.add(spawner);
        return spawners;
    }

    /**
     * Prepares chests for the buildable structure.
     *
     * This method creates four chests and positions them around the center of the structure.
     * Each chest is positioned in a cardinal direction (north, south, east, west) relative to the center.
     *
     * @return A LinkedList containing the prepared chests as Primitive objects.
     */
    protected LinkedList<Primitive> prepareChest() {
        LinkedList<Primitive> chests = new LinkedList<>();
        chests.add(createSingleBlock(CHEST, center.getX() - 1, -1, Orientation4.NORTH));
        chests.add(createSingleBlock(CHEST, center.getX() + 1, 2 * (center.getZ()) + 1, Orientation4.SOUTH));
        chests.add(createSingleBlock(CHEST, -1, center.getZ() - 1, Orientation4.EAST));
        chests.add(createSingleBlock(CHEST, 2 * center.getX() + 1, center.getZ() + 1, Orientation4.WEST));
        return chests;
    }

    private SingleBlock createSingleBlock(BlockType block, int offsetX, int offsetZ, Orientation4 orientation) {
        return new SingleBlock(block, position.translate(new Point(offsetX, 0, offsetZ)), orientation);
    }

    /**
     * Prepares and creates a set of doors for a structure. This method calculates the positions and dimensions
     * of the doors based on the structure's size and center point, and then creates both solid door frames and
     * individual doors accordingly.
     *
     * <p>The solid door frames are positioned at the edges of the structure, with dimensions to accommodate
     * the placement of individual doors within them. Individual doors are placed at the center of each edge
     * of the structure.</p>
     *
     * @return A LinkedList containing the doors as Primitive objects.
     */
    protected LinkedList<Primitive> prepareDoor() {
        LinkedList<Primitive> doors = new LinkedList<>();

        Point southDoorPos = position.translate(new Point(center.getX() - 1, 0, 0));
        Point northDoorPos = position.translate(new Point(center.getX() - 1, 0, size.getZ() - 1));
        Point eastDoorPos = position.translate(new Point(0, 0, center.getZ() - 1));
        Point westDoorPos = position.translate(new Point(size.getX() - 1, 0, center.getZ() - 1));

        Point doorSize = new Point(1, 4, 3);

        RepeationPattern redDoorMaterial = new RepeationPattern(new BlockType[][][]{{{RED_WOOL}, {REDSTONE_LAMP},
                {REDSTONE_BLOCK}, {RED_WOOL}}});
        RepeationPattern limeDoorMaterial = new RepeationPattern(new BlockType[][][]{{{LIME_WOOL}, {REDSTONE_LAMP},
                {REDSTONE_BLOCK}, {LIME_WOOL}}});
        RepeationPattern lightBlueDoorMaterial = new RepeationPattern(new BlockType[][][]{{{LIGHT_BLUE_WOOL}, {REDSTONE_LAMP},
                {REDSTONE_BLOCK}, {LIGHT_BLUE_WOOL}}});
        RepeationPattern yellowDoorMaterial = new RepeationPattern(new BlockType[][][]{{{YELLOW_WOOL}, {REDSTONE_LAMP},
                {REDSTONE_BLOCK}, {YELLOW_WOOL}}});

        doors.add(createDoorBox(southDoorPos, new Point(3, 4, 1), redDoorMaterial));
        doors.add(createDoorBox(northDoorPos, new Point(3, 4, 1), limeDoorMaterial));
        doors.add(createDoorBox(eastDoorPos, doorSize, lightBlueDoorMaterial));
        doors.add(createDoorBox(westDoorPos, doorSize, yellowDoorMaterial));

        doors.add(new Door(position.getX() + size.getX() / 2, position.getY() + 1, position.getZ(), OrientationDoor.SOUTH));
        doors.add(new Door(position.getX() + size.getX() / 2, position.getY() + 1, position.getZ() + size.getZ() - 1, OrientationDoor.NORTH));
        doors.add(new Door(position.getX(), position.getY() + 1, position.getZ() + size.getZ() / 2, OrientationDoor.EAST));
        doors.add(new Door(position.getX() + size.getX() - 1, position.getY() + 1, position.getZ() + size.getZ() / 2, OrientationDoor.WEST));

        return doors;
    }

    /**
     * Creates the frame of the door with the appropriate colored wool blocks.
     *
     * @param position the position of the door in the world
     * @param size the size of the door
     * @param material the material pattern for the door, defining the block types and their arrangement
     * @return a SolidBox representing the door frame
     */
    private SolidBox createDoorBox(Point position, Point size, RepeationPattern material) {
        return new SolidBox(position, size, new RepeationPattern(new BlockType[][][]{{{AIR}}}), material, Orientation.NearX);
    }


    /**
     * Prepares stairs for the buildable structure.
     *
     * This method creates stairs within the structure using solid blocks and fences.
     * The stairs are positioned at a specified location and have a size relative to the structure's dimensions.
     * They are designed to increase in height with each step, allowing for traversal between different levels.
     *
     * @return A LinkedList containing the prepared stairs as Primitive objects.
     */
    protected LinkedList<Primitive> prepareStairs() {

        LinkedList<Primitive> stairs = new LinkedList<>();

        stairs.add(new SolidBox(position.translate(new Point(center.getX() - 2, 0, center.getZ() - 2)),
                new Point(5, size.getY() + 1, 5), getStairRepetationPattern(),
                new RepeationPattern(new BlockType[][][] { { { FENCE } } }), Orientation.NearY));
        return stairs;
    }

    protected Pattern getStairRepetationPattern() {
        BlockType _air = AIR;
        BlockType _str = STONE;
        BlockType _cre = FENCE;

        return new RepeationPattern(new BlockType[][][] {
                {       { _air, _air, _air, _air, _air },
                        { _air, _str, _air, _air, _air },
                        { _air, _air, _cre, _air, _air },
                        { _air, _air, _air, _air, _air },
                        { _air, _air, _air, _air, _air }
                },
                {       { _air, _air, _air, _air, _air },
                        { _air, _air, _str, _air, _air },
                        { _air, _air, _cre, _air, _air },
                        { _air, _air, _air, _air, _air },
                        { _air, _air, _air, _air, _air }
                },
                {       { _air, _air, _air, _air, _air },
                        { _air, _air, _air, _str, _air },
                        { _air, _air, _cre, _air, _air },
                        { _air, _air, _air, _air, _air },
                        { _air, _air, _air, _air, _air }
                },
                {       { _air, _air, _air, _air, _air },
                        { _air, _air, _air, _air, _air },
                        { _air, _air, _cre, _str, _air },
                        { _air, _air, _air, _air, _air },
                        { _air, _air, _air, _air, _air }
                },
                {       { _air, _air, _air, _air, _air },
                        { _air, _air, _air, _air, _air },
                        { _air, _air, _cre, _air, _air },
                        { _air, _air, _air, _str, _air },
                        { _air, _air, _air, _air, _air }
                },
                {       { _air, _air, _air, _air, _air },
                        { _air, _air, _air, _air, _air },
                        { _air, _air, _cre, _air, _air },
                        { _air, _air, _str, _air, _air },
                        { _air, _air, _air, _air, _air }
                },
                {       { _air, _air, _air, _air, _air },
                        { _air, _air, _air, _air, _air },
                        { _air, _air, _cre, _air, _air },
                        { _air, _str, _air, _air, _air },
                        { _air, _air, _air, _air, _air }
                },
                {       { _air, _air, _air, _air, _air },
                        { _air, _air, _air, _air, _air },
                        { _air, _str, _cre, _air, _air },
                        { _air, _air, _air, _air, _air },
                        { _air, _air, _air, _air, _air }
                }
        });
    }

    /**
     * Prepares the walls for a buildable structure.
     *
     * This method constructs the walls for a buildable structure based on various attributes.
     * It initializes default patterns for side blocks, bottom fills, top fills, and strokes.
     * Depending on the presence of certain attributes, such as "character" and "external_character",
     * the patterns are adjusted accordingly. It then creates a random pattern for side fills,
     * considering a fallback pattern and potential completeness attribute.
     * Finally, it assembles the walls using the constructed patterns and returns them as a list of primitives.
     *
     * @return A LinkedList containing the prepared walls as Primitive objects.
     */
    protected LinkedList<Primitive> prepareWalls() {
        BlockType sideBlock = MAGENTA_WOOL;
        BlockType strcBlock = PURPLE_WOOL;
        RepeationPattern bottomFill = new RepeationPattern(new BlockType[][][] { { { MAGENTA_WOOL } } });
        RepeationPattern topFill = new RepeationPattern(new BlockType[][][] { { { MAGENTA_WOOL } } });
        RepeationPattern stroke = new RepeationPattern(new BlockType[][][] { { { BLACK_WOOL } } });

        if (innerBuildable.hasAttribute("character")) {
            for (var block : BlockType.values()){
                if (block.toString().toUpperCase().equals(innerBuildable.getAttributeValue("character"))){
                    sideBlock = block;
                    if(block.toString().toUpperCase().contains("WOOD") ||
                            block.toString().toUpperCase().contains("PLANKS")){
                        topFill = new RepeationPattern(new BlockType[][][] { { { FENCE } } });
                    }else{
                        topFill = new RepeationPattern(new BlockType[][][] { { { block } } });
                    }
                    break;
                }
            }
        }

        if (innerBuildable.hasAttribute("external_character")) {
            for (var block : BlockType.values()){
                if (block.toString().toUpperCase().equals(innerBuildable.getAttributeValue("external_character"))){
                    bottomFill = new RepeationPattern(new BlockType[][][] { { { block } } });
                    strcBlock = block;
                    stroke = new RepeationPattern(new BlockType[][][] { { { block } } });
                    break;
                }
            }
        }

        RandomPattern fallbackPattern = new RandomPattern(
                new RepeationPattern(new BlockType[][][] { { { AIR } } }));
        fallbackPattern.add(new RepeationPattern(new BlockType[][][] { { { FENCE } } }), .5);

        RandomPattern sideFill = new RandomPattern(fallbackPattern);
        sideFill.add(new RepeationPattern(
                        new BlockType[][][] { { { sideBlock, sideBlock, strcBlock, sideBlock, sideBlock },
                                { sideBlock, sideBlock, strcBlock, sideBlock, sideBlock },
                                { strcBlock, strcBlock, strcBlock, strcBlock, strcBlock },
                                { sideBlock, sideBlock, strcBlock, sideBlock, sideBlock },
                                { sideBlock, sideBlock, strcBlock, sideBlock, sideBlock } } }),
                innerBuildable.hasAttribute("completeness") ? Double.parseDouble(innerBuildable.getAttributeValue("completeness")) : 1);

        LinkedList<Primitive> walls = new LinkedList<>();
        walls.add(new EmptyBox(position, size, bottomFill, topFill, sideFill, stroke, new Point(1, 1, 1), new Point(1, 1, 1)));

        return walls;
    }

    /**
     * Prepares signs for the buildable structure.
     *
     * This method creates signs positioned both outside and inside the structure.
     * Outside signs are placed on the walls surrounding the structure, while inside signs are placed within the structure.
     * Each sign is associated with the name of the inner buildable structure.
     *
     * @return A LinkedList containing the prepared signs as Primitive objects.
     */
    protected LinkedList<Primitive> prepareSigns() {
        LinkedList<Primitive> signs = new LinkedList<>();
        //Wall signs outside
        primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 3, position.getZ() - 1, Orientation4.NORTH, innerBuildable.getName()));
        primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 3, position.getZ() + size.getZ(), Orientation4.SOUTH, innerBuildable.getName()));
        primitives.add(new WallSign(position.getX() - 1, position.getY() + 3, position.getZ() + size.getZ() / 2, Orientation4.WEST, innerBuildable.getName()));
        primitives.add(new WallSign(position.getX() + size.getX(), position.getY() + 3, position.getZ() + size.getZ() / 2, Orientation4.EAST, innerBuildable.getName()));
        //Wall signs inside
        primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 3, position.getZ() + 1, Orientation4.SOUTH, innerBuildable.getName()));
        primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 3, position.getZ() + size.getZ() - 2, Orientation4.NORTH, innerBuildable.getName()));
        primitives.add(new WallSign(position.getX() + 1, position.getY() + 3, position.getZ() + size.getZ() / 2, Orientation4.EAST, innerBuildable.getName()));
        primitives.add(new WallSign(position.getX() + size.getX() - 2, position.getY() + 3, position.getZ() + size.getZ() / 2, Orientation4.WEST, innerBuildable.getName()));
        return signs;
    }

    /**
     * Prepares a list of torches based on the configuration of the inner buildable.
     * If the inner buildable does not have the "torches" attribute, an empty list is returned.
     *
     * @return A linked list of {@link Primitive} objects representing torches. If the inner buildable
     *         does not have the "torches" attribute, the returned list is empty.
     */
    protected LinkedList<Primitive> prepareTorches() {
        LinkedList<Primitive> torches = new LinkedList<>();

        if (!innerBuildable.hasAttribute("torches")) return torches;

        int numberOfTorches = Integer.parseInt(innerBuildable.getAttributeValue("torches"));
        BlockType[] pattern;

        pattern = createTorchPattern(numberOfTorches, 3);
        torches.add(createTorchRow(pattern, Row.Direction.WEST, BlockFacingTorch.NORTH,
                position.getX() + size.getX() / 2 + 2,
                position.getY() + 2,
                position.getZ() + 1));

        torches.add(createTorchRow(pattern, Row.Direction.EAST, BlockFacingTorch.NORTH,
                position.getX() + size.getX() / 2 - 2,
                position.getY() + 2,
                position.getZ() + 1));

        pattern = createTorchPattern(numberOfTorches, 4);
        torches.add(createTorchRow(pattern, Row.Direction.WEST, BlockFacingTorch.SOUTH,
                position.getX() + size.getX() / 2 + 2,
                position.getY() + 2,
                position.getZ() + size.getZ() - 2));

        torches.add(createTorchRow(pattern, Row.Direction.EAST, BlockFacingTorch.SOUTH,
                position.getX() + size.getX() / 2 - 2,
                position.getY() + 2,
                position.getZ() + size.getZ() - 2));

        pattern = createTorchPattern(numberOfTorches, 1);
        torches.add(createTorchRow(pattern, Row.Direction.NORTH, BlockFacingTorch.WEST,
                position.getX() + 1,
                position.getY() + 2,
                position.getZ() + size.getZ() / 2 + 2));

        torches.add(createTorchRow(pattern, Row.Direction.SOUTH, BlockFacingTorch.WEST,
                position.getX() + 1,
                position.getY() + 2,
                position.getZ() + size.getZ() / 2 - 2));

        pattern = createTorchPattern(numberOfTorches, 2);
        torches.add(createTorchRow(pattern, Row.Direction.NORTH, BlockFacingTorch.EAST,
                position.getX() + size.getX() - 2,
                position.getY() + 2,
                position.getZ() + size.getZ() / 2 + 2));

        torches.add(createTorchRow(pattern, Row.Direction.SOUTH, BlockFacingTorch.EAST,
                position.getX() + size.getX() - 2,
                position.getY() + 2,
                position.getZ() + size.getZ() / 2 - 2));
        return torches;
    }

    /**
     * Creates a torch row based on the provided parameters.
     *
     * @param pattern   The pattern of the torch row as an array of {@link BasicBlock}.
     * @param direction The direction of the row.
     * @param facing    The facing direction of the torches.
     * @param x         The x-coordinate of the starting point of the row.
     * @param y         The y-coordinate of the starting point of the row.
     * @param z         The z-coordinate of the starting point of the row.
     * @return A {@link Row} object representing the torch row.
     */
    private Row createTorchRow(BlockType[] pattern, Row.Direction direction, BlockFacingTorch facing, int x, int y, int z) {
        return (new Row(
                new Point(x, y, z),
                (direction == Row.Direction.EAST || direction == Row.Direction.WEST) ?
                        size.getX() / 2 - 2 : size.getZ() / 2 - 2,
                direction,
                pattern,
                facing));
    }

    /**
     * Creates a torch pattern based on the specified number and data.
     *
     * @param number The number of torches in the pattern.
     * @param data   The data representing the torch pattern.
     * @return An array of {@link BasicBlock} representing the torch pattern.
     */
    protected BlockType[] createTorchPattern(int number, int data) {
        BlockType[] pattern = null;
        BlockType torch = TORCH;
        BlockType space = AIR;

        switch (number) {
            case 0:
                pattern = new BlockType[]{space};
                break;
            case 1:
                pattern = new BlockType[]{torch, space, space, space, space};
                break;
            case 2:
                pattern = new BlockType[]{torch, space, space, space};
                break;
            case 3:
                pattern = new BlockType[]{torch, space, space};
                break;
            case 4:
                pattern = new BlockType[]{torch, space};
                break;
            case 5:
                pattern = new BlockType[]{torch};
                break;
        }
        return pattern;
    }
}
