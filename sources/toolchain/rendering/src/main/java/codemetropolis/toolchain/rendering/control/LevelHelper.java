package codemetropolis.toolchain.rendering.control;
//import codemetropolis.toolchain.rendering.control.NBTTag;
import java.util.Objects;

public class LevelHelper {

    /**
     * Generates a compound tag that describes one layer.
     *
     * @param block  the layer's desired block
     * @param height the desired height
     * @return an unnamed TAG_Compound NBT tag, consisting a TAG_String (block) and
     * a TAG_Byte (height)
     */
    public NBTTag getLayerTagByBlockAndHeight(String block, byte height) {
        Objects.requireNonNull(block, "The block cannot be null.");
        NBTTag blockTag = new NBTTag(NBTTag.Type.TAG_String, "block", block);
        NBTTag heightTag = new NBTTag(NBTTag.Type.TAG_Byte, "height", height);

        return new NBTTag(NBTTag.Type.TAG_Compound, "", new NBTTag[]{blockTag, heightTag, NBTTag.END_TAG});
    }

}