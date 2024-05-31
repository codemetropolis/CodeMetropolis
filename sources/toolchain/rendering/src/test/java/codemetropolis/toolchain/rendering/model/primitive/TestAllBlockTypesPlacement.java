package codemetropolis.toolchain.rendering.model.primitive;

import codemetropolis.toolchain.rendering.control.WorldBuilder;
import codemetropolis.toolchain.rendering.exceptions.RenderingException;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestAllBlockTypesPlacement {

    @Test
    public void testGenerateBlocks(){

        File tempDir = new File("D:/suli/szakgyak/CodeMetropolis/sources/world", "TEMP");

        WorldBuilder worldBuilder = new WorldBuilder("D:/suli/szakgyak/CodeMetropolis/sources/world");
        try {
            worldBuilder.build(tempDir);
        } catch (RenderingException e) {
            System.err.println(e.getMessage());
        }
    }
}
