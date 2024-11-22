package codemetropolis.toolchain.commons.model.property;

public enum BlockFacingTorch {
    NONE(0),
    WEST(1),
    EAST(2),
    NORTH(3),
    SOUTH(4);

    private int value;

    BlockFacingTorch(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
