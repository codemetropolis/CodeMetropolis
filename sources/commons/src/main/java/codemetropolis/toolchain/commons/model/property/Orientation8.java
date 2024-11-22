package codemetropolis.toolchain.commons.model.property;

public enum Orientation8 {
    SOUTH(0),
    SOUTHWEST(2),
    WEST(4),
    NORTHWEST(6),
    NORTH(8),
    NORTHEAST(10),
    EAST(12),
    SOUTHEAST(14);

    private int value;

    Orientation8(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
