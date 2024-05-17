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

import java.util.HashMap;
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

    protected LinkedList<Primitive> prepareSpawner() {
        LinkedList<Primitive> spawners = new LinkedList<>();
        SingleBlock spawner = new SingleBlock("minecraft:mob_spawner", position.translate(new Point(center.getX(),
                0, center.getZ())), innerBuildable.getAttributeValue("danger"));
        spawners.add(spawner);
        return spawners;
    }

    protected LinkedList<Primitive> prepareChest() {
        LinkedList<Primitive> chests = new LinkedList<>();
        SingleBlock chestNorth = new SingleBlock("minecraft:chest", position.translate(new Point(center.getX() - 1, 0, -1)));
        SingleBlock chestSouth = new SingleBlock("minecraft:chest", position.translate(new Point(center.getX() + 1, 0, 2 * (center.getZ()) + 1)), SingleBlock.Orientation.WEST);
        SingleBlock chestEast = new SingleBlock("minecraft:chest", position.translate(new Point(-1, 0, center.getZ() - 1)), SingleBlock.Orientation.SOUTH);
        SingleBlock chestWest = new SingleBlock("minecraft:chest", position.translate(new Point(2 * center.getX() + 1, 0, center.getZ() + 1)), SingleBlock.Orientation.EAST);
        chests.add(chestNorth);
        chests.add(chestSouth);
        chests.add(chestEast);
        chests.add(chestWest);
        return chests;
    }

    protected LinkedList<Primitive> prepareDoor() {
        LinkedList<Primitive> doors = new LinkedList<>();

        doors.add(new SolidBox(position.translate(new Point(center.getX() - 1, 0, 0)), new Point(3, 4, 1),
                new RepeationPattern(new BasicBlock[][][]{{{BasicBlock.AIR}}}),
                new RepeationPattern(new BasicBlock[][][]{{{BasicBlock.RED_WOOL}, {BasicBlock.REDSTONE_LAMP},
                        {BasicBlock.REDSTONE_BLOCK}, {BasicBlock.RED_WOOL}}}),
                Orientation.NearX));
        doors.add(new SolidBox(position.translate(new Point(center.getX() - 1, 0, size.getZ() - 1)), new Point(3, 4, 1),
                new RepeationPattern(new BasicBlock[][][]{{{BasicBlock.AIR}}}),
                new RepeationPattern(new BasicBlock[][][]{{{BasicBlock.GREEN_WOOL}, {BasicBlock.REDSTONE_LAMP},
                        {BasicBlock.REDSTONE_BLOCK}, {BasicBlock.GREEN_WOOL}}}),
                Orientation.NearX));
        doors.add(new SolidBox(position.translate(new Point(0, 0, center.getZ() - 1)), new Point(1, 4, 3),
                new RepeationPattern(new BasicBlock[][][]{{{BasicBlock.AIR}}}),
                new RepeationPattern(new BasicBlock[][][]{{{BasicBlock.BLUE_WOOL}, {BasicBlock.REDSTONE_LAMP},
                        {BasicBlock.REDSTONE_BLOCK}, {BasicBlock.BLUE_WOOL}}}),
                Orientation.NearX));
        doors.add(new SolidBox(position.translate(new Point(size.getX() - 1, 0, center.getZ() - 1)), new Point(1, 4, 3),
                new RepeationPattern(new BasicBlock[][][]{{{BasicBlock.AIR}}}),
                new RepeationPattern(new BasicBlock[][][]{{{BasicBlock.YELLOW_WOOL}, {BasicBlock.REDSTONE_LAMP},
                        {BasicBlock.REDSTONE_BLOCK}, {BasicBlock.YELLOW_WOOL}}}),
                Orientation.NearX));

        doors.add(new Door(position.getX() + size.getX() / 2, position.getY() + 1, position.getZ(),
                Door.Orientation.SOUTH));
        doors.add(new Door(position.getX() + size.getX() / 2, position.getY() + 1, position.getZ() + size.getZ() - 1,
                Door.Orientation.NORTH));
        doors.add(new Door(position.getX(), position.getY() + 1, position.getZ() + size.getZ() / 2,
                Door.Orientation.EAST));
        doors.add(new Door(position.getX() + size.getX() - 1, position.getY() + 1, position.getZ() + size.getZ() / 2,
                Door.Orientation.WEST));

        return doors;
    }

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

    protected LinkedList<Primitive> prepareWalls() {
        RepeationPattern _bottomFill;
        RepeationPattern _topFill;
        RandomPattern _sideFill;
        RepeationPattern _stroke;
        BasicBlock _sideBlock;
        BasicBlock _strcBlock;
        LinkedList<Primitive> walls = new LinkedList<>();

        if (innerBuildable.hasAttribute("character")) {
            Character character = Character.parse(innerBuildable.getAttributeValue("character"));
            _sideBlock = character.getBlock();
            _topFill = new RepeationPattern(new BasicBlock[][][] { { { character.getTopBlock() } } });
        } else {
            _sideBlock = BasicBlock.MAGENTA_WOOL;
            _topFill = new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.MAGENTA_WOOL } } });
        }

        if (innerBuildable.hasAttribute("external_character")) {
            Character externalCharacter = Character.parse(innerBuildable.getAttributeValue("external_character"));
            _bottomFill = new RepeationPattern(new BasicBlock[][][] { { { externalCharacter.getBlock() } } });
            _strcBlock = externalCharacter.getBlock();
            _stroke = new RepeationPattern(new BasicBlock[][][] { { { externalCharacter.getBlock() } } });
        } else {
            _bottomFill = new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.MAGENTA_WOOL } } });
            _strcBlock = BasicBlock.PURPLE_WOOL;
            _stroke = new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.BLACK_WOOL } } });
        }

        RandomPattern _fallbackPattern = new RandomPattern(
                new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.NON_BLOCK } } }));
        _fallbackPattern.add(new RepeationPattern(new BasicBlock[][][] { { { BasicBlock.FENCE } } }), .5);
        _sideFill = new RandomPattern(_fallbackPattern);
        _sideFill.add(
                new RepeationPattern(
                        new BasicBlock[][][] { { { _sideBlock, _sideBlock, _strcBlock, _sideBlock, _sideBlock },
                                { _sideBlock, _sideBlock, _strcBlock, _sideBlock, _sideBlock },
                                { _strcBlock, _strcBlock, _strcBlock, _strcBlock, _strcBlock },
                                { _sideBlock, _sideBlock, _strcBlock, _sideBlock, _sideBlock },
                                { _sideBlock, _sideBlock, _strcBlock, _sideBlock, _sideBlock } } }),
                innerBuildable.hasAttribute("completeness")
                        ? Double.parseDouble(innerBuildable.getAttributeValue("completeness"))
                        : 1);
        walls.add(new EmptyBox(position, size, _bottomFill, _topFill, _sideFill, _stroke, new Point(1, 1, 1),
                new Point(1, 1, 1)));
        return walls;
    }

    public BasicBlock getBlockFromCharacter(String name){
        return switch (name.toUpperCase()) {
            case "STONE" -> new BasicBlock("minecraft:stone", (short) 1);
            case "COBBLESTONE" -> new BasicBlock("minecraft:cobblestone", (short) 4);
            case "MOSSY_STONE" -> new BasicBlock("minecraft:mossy_cobblestone", (short) 48);
            case "SANDSTONE" -> new BasicBlock("minecraft:sandstone", (short) 24);
            case "OBSIDIAN" -> new BasicBlock("minecraft:obsidian", (short) 49);
            case "WOOD" -> new BasicBlock("minecraft:oak_wood", (short) 17);
            case "DARK_WOOD" -> new BasicBlock("minecraft:dark_oak_wood", (short) 162);
            case "BIRCH_WOOD" -> new BasicBlock("minecraft:birch_wood", (short) 17,
                    new HashMap<>() {{
                        put("valami", "2");
                    }});
            case "PLANKS" -> new BasicBlock("minecraft:oak_planks", (short) 5);
            case "DARK_PLANKS" -> new BasicBlock("minecraft:dark_oak_planks", (short) 5,
                    new HashMap<>() {{
                        put("valami", "5");
                    }});
            case "METAL" -> new BasicBlock("minecraft:iron_block", (short) 42);
            case "DIRT" -> new BasicBlock("minecraft:dirt", (short) 3);
            case "SAND" -> new BasicBlock("minecraft:sand", (short) 12);
            case "RED_SAND" -> new BasicBlock("minecraft:red_sand", (short) 12,
                    new HashMap<>() {{
                        put("valami", "1");
                    }});
            case "BRICK" -> new BasicBlock("minecraft:bricks", (short) 45);
            case "STONE_BRICK", "DARK_BRICK" -> new BasicBlock("minecraft:stone_bricks", (short) 98);
            case "GLASS" -> new BasicBlock("minecraft:glass", (short) 20);
            case "GOLD" -> new BasicBlock("minecraft:gold_block", (short) 41);
            case "DIAMOND" -> new BasicBlock("minecraft:diamond_block", (short) 57);
            case "UNDEFINED" -> new BasicBlock("minecraft:wool", (short) 35);
            default -> null;
        };
    }

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

    protected LinkedList<Primitive> prepareTorches() {
        LinkedList<Primitive> torches = new LinkedList<>();

        if (!innerBuildable.hasAttribute("torches")) return torches;

        int numberOfTorches = Integer.parseInt(innerBuildable.getAttributeValue("torches"));
        BasicBlock[] pattern;

        pattern = createTorchPattern(numberOfTorches, 3);
        torches.add(new Row(
                new Point(position.getX() + size.getX() / 2 + 2, position.getY() + 2, position.getZ() + 1),
                size.getX() / 2 - 2,
                Row.Direction.WEST,
                pattern,
                BlockFacing.NORTH));

        torches.add(new Row(
                new Point(position.getX() + size.getX() / 2 - 2, position.getY() + 2, position.getZ() + 1),
                size.getX() / 2 - 2,
                Row.Direction.EAST,
                pattern,
                BlockFacing.NORTH));

        pattern = createTorchPattern(numberOfTorches, 4);
        torches.add(new Row(
                new Point(position.getX() + size.getX() / 2 + 2, position.getY() + 2, position.getZ() + size.getZ() - 2),
                size.getX() / 2 - 2,
                Row.Direction.WEST,
                pattern,
                BlockFacing.SOUTH));

        torches.add(new Row(
                new Point(position.getX() + size.getX() / 2 - 2, position.getY() + 2, position.getZ() + size.getZ() - 2),
                size.getX() / 2 - 2,
                Row.Direction.EAST,
                pattern,
                BlockFacing.SOUTH));

        pattern = createTorchPattern(numberOfTorches, 1);
        torches.add(new Row(
                new Point(position.getX() + 1, position.getY() + 2, position.getZ() + size.getZ() / 2 + 2),
                size.getZ() / 2 - 2,
                Row.Direction.NORTH,
                pattern,
                BlockFacing.WEST));

        torches.add(new Row(
                new Point(position.getX() + 1, position.getY() + 2, position.getZ() + size.getZ() / 2 - 2),
                size.getZ() / 2 - 2,
                Row.Direction.SOUTH,
                pattern,
                BlockFacing.WEST));

        pattern = createTorchPattern(numberOfTorches, 2);
        torches.add(new Row(
                new Point(position.getX() + size.getX() - 2, position.getY() + 2, position.getZ() + size.getZ() / 2 + 2),
                size.getZ() / 2 - 2,
                Row.Direction.NORTH,
                pattern,
                BlockFacing.EAST));

        torches.add(new Row(
                new Point(position.getX() + size.getX() - 2, position.getY() + 2, position.getZ() + size.getZ() / 2 - 2),
                size.getZ() / 2 - 2,
                Row.Direction.SOUTH,
                pattern,
                BlockFacing.EAST));
        return torches;
    }

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
