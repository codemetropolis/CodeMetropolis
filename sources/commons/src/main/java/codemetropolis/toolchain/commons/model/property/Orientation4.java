package codemetropolis.toolchain.commons.model.property;

public enum Orientation4 {
    NORTH(2),
    SOUTH(3),
    WEST(4),
    EAST(5);

    private int value;

    Orientation4(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
