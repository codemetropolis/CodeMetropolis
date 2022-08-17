import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import codemetropolis.integration.spigot.plugin.SubBuildingCommand;

public class TestSubBuildingCommand {
 
    private static String path = "../../../sources/toolchain/placing/placingToRendering.xml";
    
    @Test
    void testFindNode_1() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 3, 3);
        Node node = subBuildingCommand.getBuildingList().get(0);
        Element element = (Element) node;
        assertEquals("codemetropolis", element.getAttribute("name"));
    }

    @Test
    void testFindNode_2() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 199, 62);
        Node node = subBuildingCommand.getBuildingList().get(0);
        Element element = (Element) node;
        assertEquals("CdfTree createElements()", element.getAttribute("name"));
    }

    @Test
    void testFindNode_3() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 76, 15);
        Node node = subBuildingCommand.getBuildingList().get(0);
        Element element = (Element) node;
        assertEquals("BuildableTree", element.getAttribute("name"));
    }

    @Test
    void testFindNode_4() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 112, 70);
        Node node = subBuildingCommand.getBuildingList().get(0);
        Element element = (Element) node;
        assertEquals("x", element.getAttribute("name"));
    }

    @Test
    void testFindNode_5() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 12, 187);
        Node node = subBuildingCommand.getBuildingList().get(0);
        Element element = (Element) node;
        assertEquals("executor", element.getAttribute("name"));
    }

    @Test
    void testGetNodesWithShallowSearchWithNoChild() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.init();
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 36, 190);
        subBuildingCommand.getNodes(subBuildingCommand.getBuildingList().get(0), 0, false);
        assertEquals(0, subBuildingCommand.getShallowBuildingList().size());
    }

    @Test
    void testGetNodesWithShallowSearchWithOneChildren() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.init();
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 36, 163);
        subBuildingCommand.getNodes(subBuildingCommand.getBuildingList().get(0), 0, false);
        assertEquals(1, subBuildingCommand.getShallowBuildingList().size());
    }

    @Test
    void testGetNodesWithShallowSearchWithTwoChildren() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.init();
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 12, 187);
        subBuildingCommand.getNodes(subBuildingCommand.getBuildingList().get(0), 0, false);
        assertEquals(2, subBuildingCommand.getShallowBuildingList().size());
    }

    @Test
    void testGetNodesWithShallowSearchWithThreeChildren() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.init();
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 172, 62);
        subBuildingCommand.getNodes(subBuildingCommand.getBuildingList().get(0), 0, false);
        assertEquals(3, subBuildingCommand.getShallowBuildingList().size());
    }

    @Test
    void testGetNodesWithShallowSearchWithMoreChildren_1() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.init();
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 169, 59);
        subBuildingCommand.getNodes(subBuildingCommand.getBuildingList().get(0), 0, false);
        assertEquals(11, subBuildingCommand.getShallowBuildingList().size());
    }

    @Test
    void testGetNodesWithShallowSearchWithMoreChildren_2() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.init();
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 143, 62);
        subBuildingCommand.getNodes(subBuildingCommand.getBuildingList().get(0), 0, false);
        assertEquals(6, subBuildingCommand.getShallowBuildingList().size());
    }

    @Test
    void testGetNodesWithShallowSearchWithMoreChildren_3() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.init();
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 15, 15);
        subBuildingCommand.getNodes(subBuildingCommand.getBuildingList().get(0), 0, false);
        assertEquals(67, subBuildingCommand.getShallowBuildingList().size());
    }

    @Test
    void testGetNodesWithDeepSearchWithNoChild() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.init();
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 18, 193);
        subBuildingCommand.getNodes(subBuildingCommand.getBuildingList().get(0), 0, true);
        assertEquals(0, subBuildingCommand.getShallowBuildingList().size());
    }

    @Test
    void testGetNodesWithDeepSearchWithOneChildren() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.init();
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 18, 102);
        subBuildingCommand.getNodes(subBuildingCommand.getBuildingList().get(0), 0, true);
        assertEquals(1, subBuildingCommand.getDeepBuildingList().size());
    }

    @Test
    void testGetNodesWithDeepSearchWithMoreChildren_1() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.init();
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 79, 70);
        subBuildingCommand.getNodes(subBuildingCommand.getBuildingList().get(0), 0, true);
        assertEquals(6, subBuildingCommand.getDeepBuildingList().size());
    }

    @Test
    void testGetNodesWithDeepSearchWithMoreChildren_2() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.init();
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 76, 67);
        subBuildingCommand.getNodes(subBuildingCommand.getBuildingList().get(0), 0, true);
        assertEquals(14, subBuildingCommand.getDeepBuildingList().size());
    }

    @Test
    void testGetNodesWithDeepSearchWithMoreChildren_3() {
        SubBuildingCommand subBuildingCommand = new SubBuildingCommand(path);
        subBuildingCommand.init();
        subBuildingCommand.findNode(subBuildingCommand.getRoot(), 137, 12);
        subBuildingCommand.getNodes(subBuildingCommand.getBuildingList().get(0), 0, true);
        assertEquals(61, subBuildingCommand.getDeepBuildingList().size());
    }

}
