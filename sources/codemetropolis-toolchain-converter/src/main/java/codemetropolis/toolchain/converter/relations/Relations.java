package codemetropolis.toolchain.converter.relations;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class Relations {

    // Map to store the relations between classes, first argument is the key, the ID of the class
    // second argument is the child classes of the first argument (it can be null that means they have no child)
    private Map<String , ArrayList<String>> relationsMap = new HashMap<String, ArrayList<String>>();
    private String relationFile = null;

    public Relations(String relationFile) {
        this.relationFile = relationFile;
    }

    public void parseRelationFile() {

        try {

            System.out.println(relationFile);

            File inputFile = new File(relationFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList classList = doc.getElementsByTagName("logical:Class");
            NodeList typeList = doc.getElementsByTagName("type:Type");
            NodeList typeFormerTypeList = doc.getElementsByTagName("type:TypeFormerType");


            String refIdToRelation = null;
            String refIdToRelation2 = null;
            String parent = null;
            String currentClass = null;

            // TODO: too complex
            for (int temp = 0; temp < classList.getLength(); temp++) {
                Node nNode = classList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                Element currentClassNode = (Element) nNode;
                currentClass =  currentClassNode.getAttribute("id");

                for (int temp2 = 0; temp2 < nNode.getChildNodes().getLength(); temp2++) {
                    Node n = nNode.getChildNodes().item(temp2);

                    if ("Class_IsSubclass".equals(n.getNodeName())) {
                        Element classListElement = (Element) n;
                        System.out.println("Class_IsSubclass REF = " + classListElement.getAttribute("ref"));
                        refIdToRelation = ((Element) n).getAttribute("ref");

                        for (int temp3 = 0; temp3 < typeList.getLength(); temp3++) {
                            Node nNodeType = typeList.item(temp3);
                            ((Element) nNodeType).getAttribute("id");
                            // If the type was found search for "Type_HasTypeFormer" ref
                            if (refIdToRelation.equals(((Element) nNodeType).getAttribute("id"))) {
                                for (int temp4 = 0; temp4 < nNodeType.getChildNodes().getLength(); temp4++) {
                                    Node n2 = nNodeType.getChildNodes().item(temp4);
                                    if ("Type_HasTypeFormer".equals(n2.getNodeName())) {
                                        Element hasTypeFormerElement = (Element) n2;
                                        refIdToRelation2 = hasTypeFormerElement.getAttribute("ref");
                                        System.out.println("GOOOD: " + refIdToRelation2);

                                        // // If the Type_HasTypeFormer was found search for "TypeFormerType_RefersTo" ref

                                        for (int temp5 = 0; temp5 < typeFormerTypeList.getLength(); temp5++) {
                                            Node nNodeTypeFormerType = typeFormerTypeList.item(temp5);
                                            ((Element) nNodeTypeFormerType).getAttribute("id");
                                            // If the type was found search for "Type_HasTypeFormer" ref
                                            if (refIdToRelation2.equals(((Element) nNodeTypeFormerType).getAttribute("id"))) {
                                                for (int temp6 = 0; temp6 < nNodeTypeFormerType.getChildNodes().getLength(); temp6++) {
                                                    Node n3 = nNodeTypeFormerType.getChildNodes().item(temp6);
                                                    if ("TypeFormerType_RefersTo".equals(n3.getNodeName())) {
                                                        Element hasTypeFormerTypeRefersToElement = (Element) n3;
                                                        parent = hasTypeFormerTypeRefersToElement.getAttribute("ref");
                                                        System.out.println("PARENT: " + parent);

                                                        parent = parent.replaceAll("id", "L");
                                                        currentClass = currentClass.replaceAll("id", "L");

                                                        ArrayList<String> childs;
                                                        childs = relationsMap.get(parent);

                                                        if (childs != null) {
                                                            childs.add(currentClass);
                                                        } else {
                                                            childs = new ArrayList<String>();
                                                            childs.add(currentClass);
                                                        }
                                                        relationsMap.put(parent, childs);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: throw a normal error message
            e.printStackTrace();
        }
    }

    public Map<String, ArrayList<String>> getRelationsMap() {
        return relationsMap;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();


        for (String key : relationsMap.keySet()) {
            s.append("Class: " + key +  " -> " + "SubClasses: " + relationsMap.get(key) + " | ");
        }

        return s.toString();
    }
}
