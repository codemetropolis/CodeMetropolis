package codemetropolis.toolchain.converter.relations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import static org.junit.Assert.*;

public class RelationsTest {

    private static Relations relations = null;


    @BeforeClass
    public static void setUpBeforeClass() {
        relations = new Relations("test/codemetropolis/toolchain/converter/relations/test.limml");
        try {
            relations.parseRelationFile();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void relationsConstructorTest() throws Exception {
        assertNotNull(relations);
    }

    @Test
    public void getRelationsMapTest() throws Exception {;

        assertTrue(relations.getRelationsMap().keySet().contains("L111"));

    }

    @Test
    public void getAttributesMapTest() throws Exception {
        assertNotNull(relations.getAttributesMap());
    }

    @Test public void toStringTest() {
        assertTrue("Class: L111 -> SubClasses: [L122] | Class: L111 -> Attributes: [L119] | ".equals(relations.toString()));
    }

}