package codemetropolis.toolchain.commons.model.property;

public enum OrientationDoor {
    EAST(0),
    SOUTH(1),
    WEST(2),
    NORTH(3);

    private int value;

    OrientationDoor(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
