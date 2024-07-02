package codemetropolis.toolchain.commons.model;

import codemetropolis.toolchain.commons.model.property.ColorBanner;
import codemetropolis.toolchain.commons.model.property.ColorWool;
import codemetropolis.toolchain.commons.model.property.Type;

import java.util.HashSet;

public enum BlockType {

    // Blocks
    AIR("minecraft:air", (short) 0),
    STONE("minecraft:stone", (short) 1),
    COBBLESTONE("minecraft:cobblestone", (short) 4),
    MOSSY_COBBLESTONE("minecraft:mossy_cobblestone", (short) 48),
    OBSIDIAN("minecraft:obsidian", (short) 49),
    OAK_WOOD("minecraft:oak_wood", (short) 17),
    DARK_OAK_WOOD("minecraft:dark_oak_wood", (short) 162, Type.DARK_OAK_WOOD),
    BIRCH_WOOD("minecraft:birch_wood", (short) 17, Type.BIRCH_WOOD),
    OAK_PLANKS("minecraft:oak_planks", (short) 5),
    DARK_OAK_PLANKS("minecraft:dark_oak_planks", (short) 5, Type.DARK_OAK_PLANKS),
    IRON_BLOCK("minecraft:iron_block", (short) 42),
    DIRT("minecraft:dirt", (short) 3),
    CUT_SANDSTONE("minecraft:cut_sandstone", (short) 24, Type.CUT_SANDSTONE),
    RED_SAND("minecraft:red_sand", (short) 12, Type.RED_SAND),
    BRICK_BLOCK("minecraft:bricks", (short) 45),
    GLASS("minecraft:glass", (short) 20),
    GOLD_BLOCK("minecraft:gold_block", (short) 41),
    DIAMOND_BLOCK("minecraft:diamond_block", (short) 57),
    STONE_BRICKS("minecraft:stone_bricks", (short) 98),
    SANDSTONE("minecraft:sandstone", (short) 24),
    GRASS_BLOCK("minecraft:grass_block", (short) 2),
    REDSTONE_BLOCK("minecraft:redstone_block", (short) 152),
    REDSTONE_LAMP("minecraft:lit_redstone_lamp", (short) 124),

    // Items
    FENCE("minecraft:oak_fence", (short) 85),
    TORCH("minecraft:wall_torch", (short) 50),
    DOOR("minecraft:oak_door", (short) 64),
    SIGN("minecraft:oak_sign", (short) 63),
    WALL_SIGN("minecraft:wall_sign", (short) 68),
    CHEST("minecraft:chest", (short) 54),
    MOB_SPAWNER("minecraft:mob_spawner", (short) 52),

    // Plants
    POPPY("minecraft:poppy", (short) 38),
    DANDELION("minecraft:dandelion", (short) 37),
    BROWN_MUSHROOM("minecraft:brown_mushroom", (short) 39),
    OAK_SAPLING("minecraft:oak_sapling", (short) 6),

    // Wools
    WHITE_WOOL("minecraft:white_wool", (short) 35, ColorWool.WHITE),
    ORANGE_WOOL("minecraft:orange_wool", (short) 35, ColorWool.ORANGE),
    MAGENTA_WOOL("minecraft:magenta_wool", (short) 35, ColorWool.MAGENTA),
    LIGHT_BLUE_WOOL("minecraft:light_blue_wool", (short) 35, ColorWool.LIGHT_BLUE),
    YELLOW_WOOL("minecraft:yellow_wool", (short) 35, ColorWool.YELLOW),
    LIME_WOOL("minecraft:lime_wool", (short) 35, ColorWool.LIME),
    PINK_WOOL("minecraft:pink_wool", (short) 35, ColorWool.PINK),
    GRAY_WOOL("minecraft:gray_wool", (short) 35, ColorWool.GRAY),
    LIGHT_GRAY_WOOL("minecraft:light_gray_wool", (short) 35, ColorWool.LIGHT_GRAY),
    CYAN_WOOL("minecraft:cyan_wool", (short) 35, ColorWool.CYAN),
    PURPLE_WOOL("minecraft:purple_wool", (short) 35, ColorWool.PURPLE),
    BLUE_WOOL("minecraft:blue_wool", (short) 35, ColorWool.BLUE),
    BROWN_WOOL("minecraft:brown_wool", (short) 35, ColorWool.BROWN),
    GREEN_WOOL("minecraft:green_wool", (short) 35, ColorWool.GREEN),
    RED_WOOL("minecraft:red_wool", (short) 35, ColorWool.RED),
    BLACK_WOOL("minecraft:black_wool", (short) 35, ColorWool.BLACK),

    // Banners
    WHITE_BANNER("minecraft:white_banner", (short) 176, ColorBanner.WHITE),
    ORANGE_BANNER("minecraft:orange_banner", (short) 176, ColorBanner.ORANGE),
    MAGENTA_BANNER("minecraft:magenta_banner", (short) 176, ColorBanner.MAGENTA),
    LIGHT_BLUE_BANNER("minecraft:light_blue_banner", (short) 176, ColorBanner.LIGHT_BLUE),
    YELLOW_BANNER("minecraft:yellow_banner", (short) 176, ColorBanner.YELLOW),
    LIME_BANNER("minecraft:lime_banner", (short) 176, ColorBanner.LIME),
    PINK_BANNER("minecraft:pink_banner", (short) 176, ColorBanner.PINK),
    GRAY_BANNER("minecraft:gray_banner", (short) 176, ColorBanner.GRAY),
    LIGHT_GRAY_BANNER("minecraft:light_gray_banner", (short) 176, ColorBanner.LIGHT_GRAY),
    CYAN_BANNER("minecraft:cyan_banner", (short) 176, ColorBanner.CYAN),
    PURPLE_BANNER("minecraft:purple_banner", (short) 176, ColorBanner.PURPLE),
    BLUE_BANNER("minecraft:blue_banner", (short) 176, ColorBanner.BLUE),
    BROWN_BANNER("minecraft:brown_banner", (short) 176, ColorBanner.BROWN),
    GREEN_BANNER("minecraft:green_banner", (short) 176, ColorBanner.GREEN),
    RED_BANNER("minecraft:red_banner", (short) 176, ColorBanner.RED),
    BLACK_BANNER("minecraft:black_banner", (short) 176, ColorBanner.BLACK),
    WHITE_WALL_BANNER("minecraft:white_wall_banner", (short) 177, ColorBanner.WHITE),
    ORANGE_WALL_BANNER("minecraft:orange_wall_banner", (short) 177, ColorBanner.ORANGE),
    MAGENTA_WALL_BANNER("minecraft:magenta_wall_banner", (short) 177, ColorBanner.MAGENTA),
    LIGHT_BLUE_WALL_BANNER("minecraft:light_blue_wall_banner", (short) 177, ColorBanner.LIGHT_BLUE),
    YELLOW_WALL_BANNER("minecraft:yellow_wall_banner", (short) 177, ColorBanner.YELLOW),
    LIME_WALL_BANNER("minecraft:lime_wall_banner", (short) 177, ColorBanner.LIME),
    PINK_WALL_BANNER("minecraft:pink_wall_banner", (short) 177, ColorBanner.PINK),
    GRAY_WALL_BANNER("minecraft:gray_wall_banner", (short) 177, ColorBanner.GRAY),
    LIGHT_GRAY_WALL_BANNER("minecraft:light_gray_wall_banner", (short) 177, ColorBanner.LIGHT_GRAY),
    CYAN_WALL_BANNER("minecraft:cyan_wall_banner", (short) 177, ColorBanner.CYAN),
    PURPLE_WALL_BANNER("minecraft:purple_wall_banner", (short) 177, ColorBanner.PURPLE),
    BLUE_WALL_BANNER("minecraft:blue_wall_banner", (short) 177, ColorBanner.BLUE),
    BROWN_WALL_BANNER("minecraft:brown_wall_banner", (short) 177, ColorBanner.BROWN),
    GREEN_WALL_BANNER("minecraft:green_wall_banner", (short) 177, ColorBanner.GREEN),
    RED_WALL_BANNER("minecraft:red_wall_banner", (short) 177, ColorBanner.RED),
    BLACK_WALL_BANNER("minecraft:black_wall_banner", (short) 177, ColorBanner.BLACK);


    private String stringId;
    private short shortId;
    private HashSet<Enum<?>> properties;

    BlockType(String stringId, short shortId) {
        this.stringId = stringId;
        this.shortId = shortId;
        this.properties = new HashSet<>();
    }

    BlockType(String stringId, short shortId, Enum<?> properties) {
        this.stringId = stringId;
        this.shortId = shortId;
        this.properties = new HashSet<>();
        this.properties.add(properties);
    }

    BlockType(BlockType original) {
        this.stringId = original.stringId;
        this.shortId = original.shortId;
        this.properties = new HashSet<>(original.properties);
    }

    public String getStringId() {
        return stringId;
    }

    public short getShortId() {
        return shortId;
    }

    public HashSet<Enum<?>> getProperties() {
        return properties;
    }

    public void setProperties(HashSet<Enum<?>> properties) {
        this.properties = properties;
    }

    public void addProperty(Enum<?> prop){
        this.properties.add(prop);
    }
}
