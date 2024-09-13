package codemetropolis.toolchain.commons.model.property;

public enum ColorBanner {
    WHITE(15),
    ORANGE(14),
    MAGENTA(13),
    LIGHT_BLUE(12),
    YELLOW(11),
    LIME(10),
    PINK(9),
    GRAY(8),
    LIGHT_GRAY(7),
    CYAN(6),
    PURPLE(5),
    BLUE(4),
    BROWN(3),
    GREEN(2),
    RED(1),
    BLACK(0);

    private int value;

    ColorBanner(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
