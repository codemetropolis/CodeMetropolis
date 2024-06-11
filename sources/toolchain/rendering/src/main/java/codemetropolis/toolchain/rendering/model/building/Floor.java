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
        SingleBlock spawner = new SingleBlock(BasicBlock.MOB_SPAWNER, position.translate(new Point(center.getX(),
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
        chests.add(createSingleBlock(BasicBlock.CHEST, center.getX() - 1, -1, SingleBlock.Orientation.NORTH));
        chests.add(createSingleBlock(BasicBlock.CHEST, center.getX() + 1, 2 * (center.getZ()) + 1, SingleBlock.Orientation.SOUTH));
        chests.add(createSingleBlock(BasicBlock.CHEST, -1, center.getZ() - 1, SingleBlock.Orientation.EAST));
        chests.add(createSingleBlock(BasicBlock.CHEST, 2 * center.getX() + 1, center.getZ() + 1, SingleBlock.Orientation.WEST));
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

        Point door1Pos = position.translate(new Point(center.getX() - 1, 0, 0));
        Point door2Pos = position.translate(new Point(center.getX() - 1, 0, size.getZ() - 1));
        Point door3Pos = position.translate(new Point(0, 0, center.getZ() - 1));
        Point door4Pos = position.translate(new Point(size.getX() - 1, 0, center.getZ() - 1));

        Point doorSize = new Point(1, 4, 3);

        RepeationPattern doorMaterial1 = new RepeationPattern(new BasicBlock[][][]{{{BasicBlock.RED_WOOL}, {BasicBlock.REDSTONE_LAMP},
                {BasicBlock.REDSTONE_BLOCK}, {BasicBlock.RED_WOOL}}});
        RepeationPattern doorMaterial2 = new RepeationPattern(new BasicBlock[][][]{{{BasicBlock.LIME_WOOL}, {BasicBlock.REDSTONE_LAMP},
                {BasicBlock.REDSTONE_BLOCK}, {BasicBlock.LIME_WOOL}}});
        RepeationPattern doorMaterial3 = new RepeationPattern(new BasicBlock[][][]{{{BasicBlock.LIGHT_BLUE_WOOL}, {BasicBlock.REDSTONE_LAMP},
                {BasicBlock.REDSTONE_BLOCK}, {BasicBlock.LIGHT_BLUE_WOOL}}});
        RepeationPattern doorMaterial4 = new RepeationPattern(new BasicBlock[][][]{{{BasicBlock.YELLOW_WOOL}, {BasicBlock.REDSTONE_LAMP},
                {BasicBlock.REDSTONE_BLOCK}, {BasicBlock.YELLOW_WOOL}}});

        doors.add(createDoorBox(door1Pos, new Point(3, 4, 1), doorMaterial1, Orientation.NearX));
        doors.add(createDoorBox(door2Pos, new Point(3, 4, 1), doorMaterial2, Orientation.NearX));
        doors.add(createDoorBox(door3Pos, doorSize, doorMaterial3, Orientation.NearX));
        doors.add(createDoorBox(door4Pos, doorSize, doorMaterial4, Orientation.NearX));

        doors.add(new Door(position.getX() + size.getX() / 2, position.getY() + 1, position.getZ(), Door.Orientation.SOUTH));
        doors.add(new Door(position.getX() + size.getX() / 2, position.getY() + 1, position.getZ() + size.getZ() - 1, Door.Orientation.NORTH));
        doors.add(new Door(position.getX(), position.getY() + 1, position.getZ() + size.getZ() / 2, Door.Orientation.EAST));
        doors.add(new Door(position.getX() + size.getX() - 1, position.getY() + 1, position.getZ() + size.getZ() / 2, Door.Orientation.WEST));

        return doors;
    }

    private SolidBox createDoorBox(Point position, Point size, RepeationPattern material, Orientation orientation) {
        return new SolidBox(position, size, new RepeationPattern(new BasicBlock[][][]{{{BasicBlock.AIR}}}), material, orientation);
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
                new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.FENCE } } }), Orientation.NearY));
        return stairs;
    }

    protected Pattern getStairRepetationPattern() {
        BasicBlock _air = BasicBlock.AIR;
        BasicBlock _str = BasicBlock.STONE;
        BasicBlock _cre = BasicBlock.FENCE;

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
        BasicBlock sideBlock = BasicBlock.MAGENTA_WOOL;
        BasicBlock strcBlock = BasicBlock.PURPLE_WOOL;
        RepeationPattern bottomFill = new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.MAGENTA_WOOL } } });
        RepeationPattern topFill = new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.MAGENTA_WOOL } } });
        RepeationPattern stroke = new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.BLACK_WOOL } } });

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
                new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.NON_BLOCK } } }));
        fallbackPattern.add(new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.FENCE } } }), .5);

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
        BasicBlock torch = BasicBlock.TORCH;
        BasicBlock space = BasicBlock.NON_BLOCK;

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
