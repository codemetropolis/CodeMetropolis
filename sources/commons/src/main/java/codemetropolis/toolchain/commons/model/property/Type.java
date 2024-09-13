package codemetropolis.toolchain.commons.model.property;

public enum Type {
    DARK_OAK_WOOD(1),
    BIRCH_WOOD(2),
    CUT_SANDSTONE(2),
    RED_SAND(4),
    DARK_OAK_PLANKS(5);

    private int value;

    private Type(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
