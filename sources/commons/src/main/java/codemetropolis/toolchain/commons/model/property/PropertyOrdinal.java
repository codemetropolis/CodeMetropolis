package codemetropolis.toolchain.commons.model.property;

public enum PropertyOrdinal {
    BLOCKFACINGTORCH(0),
    COLORBANNER(1),
    COLORWOOL(2),
    ORIENTATION4(3),
    ORIENTATION8(4),
    ORIENTATIONDOOR(5),
    TYPE(6);

    private int value;

    private PropertyOrdinal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
