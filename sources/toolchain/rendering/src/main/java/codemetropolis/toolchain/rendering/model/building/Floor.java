package codemetropolis.toolchain.rendering.model.building;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.BuildingTypeMismatchException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.Pattern;
import codemetropolis.toolchain.rendering.model.pattern.RandomPattern;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.*;
import codemetropolis.toolchain.rendering.model.primitive.Row.BlockFacing;
import codemetropolis.toolchain.rendering.util.Character;
import codemetropolis.toolchain.rendering.util.Orientation;

import java.util.LinkedList;

import static codemetropolis.toolchain.rendering.model.BasicBlock.BasicBlockType.*;

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
        SingleBlock spawner = new SingleBlock(MOB_SPAWNER.getBlock(), position.translate(new Point(center.getX(),
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
        chests.add(createSingleBlock(CHEST.getBlock(), center.getX() - 1, -1, SingleBlock.Orientation.NORTH));
        chests.add(createSingleBlock(CHEST.getBlock(), center.getX() + 1, 2 * (center.getZ()) + 1, SingleBlock.Orientation.SOUTH));
        chests.add(createSingleBlock(CHEST.getBlock(), -1, center.getZ() - 1, SingleBlock.Orientation.EAST));
        chests.add(createSingleBlock(CHEST.getBlock(), 2 * center.getX() + 1, center.getZ() + 1, SingleBlock.Orientation.WEST));
        return chests;
    }

    private SingleBlock createSingleBlock(BasicBlock block, int offsetX, int offsetZ, SingleBlock.Orientation orientation) {
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

        RepeationPattern redDoorMaterial = new RepeationPattern(new BasicBlock[][][]{{{RED_WOOL.getBlock()}, {REDSTONE_LAMP.getBlock()},
                {REDSTONE_BLOCK.getBlock()}, {RED_WOOL.getBlock()}}});
        RepeationPattern limeDoorMaterial = new RepeationPattern(new BasicBlock[][][]{{{LIME_WOOL.getBlock()}, {REDSTONE_LAMP.getBlock()},
                {REDSTONE_BLOCK.getBlock()}, {LIME_WOOL.getBlock()}}});
        RepeationPattern lightBlueDoorMaterial = new RepeationPattern(new BasicBlock[][][]{{{LIGHT_BLUE_WOOL.getBlock()}, {REDSTONE_LAMP.getBlock()},
                {REDSTONE_BLOCK.getBlock()}, {LIGHT_BLUE_WOOL.getBlock()}}});
        RepeationPattern yellowDoorMaterial = new RepeationPattern(new BasicBlock[][][]{{{YELLOW_WOOL.getBlock()}, {REDSTONE_LAMP.getBlock()},
                {REDSTONE_BLOCK.getBlock()}, {YELLOW_WOOL.getBlock()}}});

        doors.add(createDoorBox(southDoorPos, new Point(3, 4, 1), redDoorMaterial, Orientation.NearX));
        doors.add(createDoorBox(northDoorPos, new Point(3, 4, 1), limeDoorMaterial, Orientation.NearX));
        doors.add(createDoorBox(eastDoorPos, doorSize, lightBlueDoorMaterial, Orientation.NearX));
        doors.add(createDoorBox(westDoorPos, doorSize, yellowDoorMaterial, Orientation.NearX));

        doors.add(new Door(position.getX() + size.getX() / 2, position.getY() + 1, position.getZ(), Door.Orientation.SOUTH));
        doors.add(new Door(position.getX() + size.getX() / 2, position.getY() + 1, position.getZ() + size.getZ() - 1, Door.Orientation.NORTH));
        doors.add(new Door(position.getX(), position.getY() + 1, position.getZ() + size.getZ() / 2, Door.Orientation.EAST));
        doors.add(new Door(position.getX() + size.getX() - 1, position.getY() + 1, position.getZ() + size.getZ() / 2, Door.Orientation.WEST));

        return doors;
    }

    /**
     * Creates the frame of the door with the appropriate colored wool blocks.
     *
     * @param position the position of the door in the world
     * @param size the size of the door
     * @param material the material pattern for the door, defining the block types and their arrangement
     * @param orientation the orientation of the door
     * @return a SolidBox representing the door frame
     */
    private SolidBox createDoorBox(Point position, Point size, RepeationPattern material, Orientation orientation) {
        return new SolidBox(position, size, new RepeationPattern(new BasicBlock[][][]{{{AIR.getBlock()}}}), material, orientation);
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
                new RepeationPattern(new BasicBlock[][][] { { { FENCE.getBlock() } } }), Orientation.NearY));
        return stairs;
    }

    protected Pattern getStairRepetationPattern() {
        BasicBlock _air = AIR.getBlock();
        BasicBlock _str = STONE.getBlock();
        BasicBlock _cre = FENCE.getBlock();

        return new RepeationPattern(new BasicBlock[][][] {
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
        BasicBlock sideBlock = MAGENTA_WOOL.getBlock();
        BasicBlock strcBlock = PURPLE_WOOL.getBlock();
        RepeationPattern bottomFill = new RepeationPattern(new BasicBlock[][][] { { { MAGENTA_WOOL.getBlock() } } });
        RepeationPattern topFill = new RepeationPattern(new BasicBlock[][][] { { { MAGENTA_WOOL.getBlock() } } });
        RepeationPattern stroke = new RepeationPattern(new BasicBlock[][][] { { { BLACK_WOOL.getBlock() } } });

        if (innerBuildable.hasAttribute("character")) {
            Character character = Character.parse(innerBuildable.getAttributeValue("character"));
            sideBlock = character.getBlock();
            topFill = new RepeationPattern(new BasicBlock[][][] { { { character.getTopBlock() } } });
        }

        if (innerBuildable.hasAttribute("external_character")) {
            Character externalCharacter = Character.parse(innerBuildable.getAttributeValue("external_character"));
            bottomFill = new RepeationPattern(new BasicBlock[][][] { { { externalCharacter.getBlock() } } });
            strcBlock = externalCharacter.getBlock();
            stroke = new RepeationPattern(new BasicBlock[][][] { { { externalCharacter.getBlock() } } });
        }

        RandomPattern fallbackPattern = new RandomPattern(
                new RepeationPattern(new BasicBlock[][][] { { { NON_BLOCK.getBlock() } } }));
        fallbackPattern.add(new RepeationPattern(new BasicBlock[][][] { { { FENCE.getBlock() } } }), .5);

        RandomPattern sideFill = new RandomPattern(fallbackPattern);
        sideFill.add(new RepeationPattern(
                        new BasicBlock[][][] { { { sideBlock, sideBlock, strcBlock, sideBlock, sideBlock },
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
        primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 3, position.getZ() - 1, WallSign.Orientation.NORTH, innerBuildable.getName()));
        primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 3, position.getZ() + size.getZ(), WallSign.Orientation.SOUTH, innerBuildable.getName()));
        primitives.add(new WallSign(position.getX() - 1, position.getY() + 3, position.getZ() + size.getZ() / 2, WallSign.Orientation.WEST, innerBuildable.getName()));
        primitives.add(new WallSign(position.getX() + size.getX(), position.getY() + 3, position.getZ() + size.getZ() / 2, WallSign.Orientation.EAST, innerBuildable.getName()));
        //Wall signs inside
        primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 3, position.getZ() + 1, WallSign.Orientation.SOUTH, innerBuildable.getName()));
        primitives.add(new WallSign(position.getX() + size.getX() / 2, position.getY() + 3, position.getZ() + size.getZ() - 2, WallSign.Orientation.NORTH, innerBuildable.getName()));
        primitives.add(new WallSign(position.getX() + 1, position.getY() + 3, position.getZ() + size.getZ() / 2, WallSign.Orientation.EAST, innerBuildable.getName()));
        primitives.add(new WallSign(position.getX() + size.getX() - 2, position.getY() + 3, position.getZ() + size.getZ() / 2, WallSign.Orientation.WEST, innerBuildable.getName()));
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
        BasicBlock[] pattern;

        pattern = createTorchPattern(numberOfTorches, 3);
        torches.add(createTorchRow(pattern, Row.Direction.WEST, BlockFacing.NORTH,
                position.getX() + size.getX() / 2 + 2,
                position.getY() + 2,
                position.getZ() + 1));

        torches.add(createTorchRow(pattern, Row.Direction.EAST, BlockFacing.NORTH,
                position.getX() + size.getX() / 2 - 2,
                position.getY() + 2,
                position.getZ() + 1));

        pattern = createTorchPattern(numberOfTorches, 4);
        torches.add(createTorchRow(pattern, Row.Direction.WEST, BlockFacing.SOUTH,
                position.getX() + size.getX() / 2 + 2,
                position.getY() + 2,
                position.getZ() + size.getZ() - 2));

        torches.add(createTorchRow(pattern, Row.Direction.EAST, BlockFacing.SOUTH,
                position.getX() + size.getX() / 2 - 2,
                position.getY() + 2,
                position.getZ() + size.getZ() - 2));

        pattern = createTorchPattern(numberOfTorches, 1);
        torches.add(createTorchRow(pattern, Row.Direction.NORTH, BlockFacing.WEST,
                position.getX() + 1,
                position.getY() + 2,
                position.getZ() + size.getZ() / 2 + 2));

        torches.add(createTorchRow(pattern, Row.Direction.SOUTH, BlockFacing.WEST,
                position.getX() + 1,
                position.getY() + 2,
                position.getZ() + size.getZ() / 2 - 2));

        pattern = createTorchPattern(numberOfTorches, 2);
        torches.add(createTorchRow(pattern, Row.Direction.NORTH, BlockFacing.EAST,
                position.getX() + size.getX() - 2,
                position.getY() + 2,
                position.getZ() + size.getZ() / 2 + 2));

        torches.add(createTorchRow(pattern, Row.Direction.SOUTH, BlockFacing.EAST,
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
    private Row createTorchRow(BasicBlock[] pattern, Row.Direction direction, BlockFacing facing, int x, int y, int z) {
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
    protected BasicBlock[] createTorchPattern(int number, int data) {
        BasicBlock[] pattern = null;
        BasicBlock torch = TORCH.getBlock();
        BasicBlock space = NON_BLOCK.getBlock();

        switch (number) {
            case 0:
                pattern = new BasicBlock[]{space};
                break;
            case 1:
                pattern = new BasicBlock[]{torch, space, space, space, space};
                break;
            case 2:
                pattern = new BasicBlock[]{torch, space, space, space};
                break;
            case 3:
                pattern = new BasicBlock[]{torch, space, space};
                break;
            case 4:
                pattern = new BasicBlock[]{torch, space};
                break;
            case 5:
                pattern = new BasicBlock[]{torch};
                break;
        }
        return pattern;
    }
}
