package codemetropolis.integration.spigot.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class for the executable command
 * FileManager
 * @author Dávid Szabolcs
 * @author Deák Tamás
 * @version 1.0
 */
public class SubBuildingCommand implements CommandExecutor{

    private DocumentBuilder builder;
    private Document doc;
    private NodeList nodeList;
    private List<Node> buildingList;
    private List<Node> deepBuildingList;
    private List<Node> shallowBuildingList;
    private List<Integer> tabulatingList;
    private String path;
    private Node root;

    public DocumentBuilder getBuilder() {
        return this.builder;
    }

    public void setBuilder(DocumentBuilder builder) {
        this.builder = builder;
    }

    public Document getDoc() {
        return this.doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public NodeList getNodeList() {
        return this.nodeList;
    }

    public void setNodeList(NodeList nodeList) {
        this.nodeList = nodeList;
    }

    public List<Node> getBuildingList() {
        return this.buildingList;
    }

    public void setBuildingList(List<Node> buildingList) {
        this.buildingList = buildingList;
    }

    public List<Node> getDeepBuildingList() {
        return this.deepBuildingList;
    }

    public void setDeepBuildingList(List<Node> deepBuildingList) {
        this.deepBuildingList = deepBuildingList;
    }

    public List<Node> getShallowBuildingList() {
        return this.shallowBuildingList;
    }

    public void setShallowBuildingList(List<Node> shallowBuildingList) {
        this.shallowBuildingList = shallowBuildingList;
    }

    public List<Integer> getTabulatingList() {
        return this.tabulatingList;
    }

    public void setTabulatingList(List<Integer> tabulatingList) {
        this.tabulatingList = tabulatingList;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Node getRoot() {
        return this.root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public SubBuildingCommand(String path){
        this.path = path;
        // Initialize class attributes
        init();
    } 

    /**
     * Whenever a player types /sub-building [-d] [page] in chat, method is called
     * @param sender represents whatever is sending the command. This could be a Player, ConsoleCommandSender, or BlockCommandSender (a command block)
     * @param command represents what the command being called is. This will almost always be known ahead of time.
     * @param label represents the exact first word of the command (excluding arguments) that was entered by the sender.
     * @param args is the remainder of the command statement (excluding the label) split up by spaces and put into an array.
     * @return true/false whether it executed properly or not.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Execute only if player called the command
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // Get player coordinates
            int player_x = player.getLocation().getBlockX(), player_z = player.getLocation().getBlockZ();

            // Start searching for the root Node, or the building in which the player stands
            findNode(root, player_x, player_z);
            // Get the root Node
            Node currentNode = buildingList.get(0);
            
            // Check for -d flag in the command, or pages
            // If there is no arguments (command is executed as /sub-buildings) it gets the first page of the sub-buildings
            if(args.length == 0) {
            	getNodes(currentNode, 0, false);
            	int pages = shallowBuildingList.size() / 18 + 1;
            	sendMessage(player, shallowBuildingList, null, currentNode, 1, pages);
            } else if(args.length == 1 || args.length == 2) {
                // Deep search
                if(args[0].equals("-d") || args[0].equals("-deep")) {
            		getNodes(currentNode, 0, true);
                	int pages = deepBuildingList.size() / 18 + 1;
                	if(args.length == 1) {
                		sendMessage(player, deepBuildingList, tabulatingList, currentNode, 1, pages);
                	} else {
                		try {
                			int page = Integer.parseInt(args[1]);
                			sendMessage(player, deepBuildingList, tabulatingList, currentNode, page, pages);
                		} catch (NumberFormatException e) {
                			sender.sendMessage(ChatColor.RED + "Wrong usage! Use /sub-buildings [-d] [page]");
    						e.printStackTrace();
    					}
                	}
                    // Shallow search
                	} else {
                		getNodes(currentNode, 0, false);
                		int pages = shallowBuildingList.size() / 18 + 1;
                		try {
                			int page = Integer.parseInt(args[0]);
                			sendMessage(player, shallowBuildingList, null, currentNode, page, pages);
                		} catch (NumberFormatException e) {
                			sender.sendMessage(ChatColor.RED + "Wrong usage! Use /sub-buildings [-d] [page]");
							e.printStackTrace();
						}
                	}	
                } else {
                    // If something else happens when executing the command
                    sender.sendMessage(ChatColor.RED + "Wrong usage! Use /sub-buildings [-d] [page]");
                }
            
        } else {
            // If command was executed from console
            sender.sendMessage(ChatColor.RED + "Only in-game players can use this command!");
        }
        return false;
    }

    /**
     * Initialize all class attributes, needed for testing
     */
    public void init() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(path);
            nodeList = doc.getElementsByTagName("buildable");
            root = nodeList.item(0);
            buildingList = new ArrayList<>();
            deepBuildingList = new ArrayList<>();
            shallowBuildingList = new ArrayList<>();
            tabulatingList = new ArrayList<>();
        // Error message if something happened to the file
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param player to whom the message is sent
     * @param buildingList list of sub-buildings
     * @param tabulatingList list in which the tab rate is stored. Null if the search is shallow.
     * @param currentNode root Node
     * @param page page to be displayed
     * @param allPages number of pages
     */
    public void sendMessage(Player player, List<Node> buildingList, List<Integer> tabulatingList, Node currentNode, int page, int allPages) {
        // If player wants to display a non-existing page. For example, page 5 when there are only 4 pages.
    	if (page > allPages) {
    		if(allPages == 1) {
    			player.sendMessage(ChatColor.RED + "There is only 1 page, please try /sub-buildings [-d] [1]!");
    		} else {
        		player.sendMessage(ChatColor.RED + "There are only " + allPages + " pages, please try /sub-buildings [-d] [1-" + allPages + "]!");
    		}
    		return;
    	}
        // First and last items to display
		int first = (page - 1) * 18; 
		int last = Math.min(page * 18, buildingList.size()); 
		player.sendMessage(ChatColor.GOLD + "Sub-buildings of " + ChatColor.YELLOW + ((Element) currentNode).getAttribute("name") + ChatColor.GOLD + " [" + page + "]:");
        // Listing all the sub-buildings
		for(int j = first; j < last; j++) {
			if(tabulatingList != null) {    		
				StringBuilder strb = new StringBuilder();
				for(int k = 0; k < tabulatingList.get(j) + 1; k++) {
					strb.append("  ");
				}
				player.sendMessage(ChatColor.GOLD + strb.toString() + ChatColor.GOLD + ((Element)buildingList.get(j)).getAttribute("name").substring(0, Math.min(((Element)buildingList.get(j)).getAttribute("name").length(), 53 - tabulatingList.get(j)))  + (((Element)buildingList.get(j)).getAttribute("name").length() > 53 - tabulatingList.get(j) ? "..." : ""));
        	} else {
                player.sendMessage(ChatColor.GOLD + "   " + ((Element)shallowBuildingList.get(j)).getAttribute("name").substring(0, Math.min(((Element)shallowBuildingList.get(j)).getAttribute("name").length(), 49))  + (((Element)shallowBuildingList.get(j)).getAttribute("name").length() > 49 ? "..." : ""));
        	}

    	}
    	player.sendMessage(ChatColor.GOLD + "Page " + page + " of " + allPages);
	}


    /**
     * Return the Node we are looking for and stores it in buildingList
     * @param root Node
     * @param x coordinate of the player
     * @param z coordinate of the player
     */
	public void findNode(Node root, int x, int z) {
        Element element = (Element) root;
        NodeList nodeList = element.getChildNodes();
        int pos_x = 0, pos_z = 0, size_x = 0, size_z = 0;
        // Loop through the children of the root Node
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element_ = (Element) node;
                if (element_.getTagName().equals("position") || element_.getTagName().equals("size") || element_.getTagName().equals("children")) {
                    // Get position and size attributes
                    if (element_.getTagName().equals("position")) {
                        pos_x = Integer.parseInt(element_.getAttribute("x"));
                        pos_z = Integer.parseInt(element_.getAttribute("z"));
                    }
                    if (element_.getTagName().equals("size")) {
                        size_x = Integer.parseInt(element_.getAttribute("x"));
                        size_z = Integer.parseInt(element_.getAttribute("z"));
                    }
                    // If the player's coordinates are in the area being examined
                    if (x >= pos_x && z >= pos_z && x <= pos_x + size_x && z <= pos_z + size_z && element_.getTagName().equals("children")) {
                        NodeList nl = node.getChildNodes();
                        for(int j = 0; j < nl.getLength(); j++) {
                            if(j % 2 == 1) {
                                // Recursion
                                findNode((Element)nl.item(j), x, z);
                            }
                        }
                        // If all the recursive calls have been called, and it can't find new Nodes, we found the one we are looking for, fill the list
                        buildingList.add(root);
                        return;
                    }
                }
            }
        }
    }
    
    /**
     * Fills deepBuildingList and shallowBuildingList with the child Nodes of the root Node
     * @param node root Node
     * @param deepness fills tabulatingList for tabulating the items later @see line 147
     * @param deep if true, deep search is called, deepBuildingList is filled, recursion
     */
    public void getNodes(Node node, int deepness, boolean deep) {
    	NodeList nodeList = node.getChildNodes();
        // Loop through the children of the root Node
        for(int j = 0; j < nodeList.getLength(); j++) {
            Node n = nodeList.item(j);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element element_ = (Element) n;
                if(element_.getTagName().equals("children")) {
                    NodeList nl = n.getChildNodes();
                    for(int k = 0; k < nl.getLength(); k++) {
                        if(k % 2 == 1) {
                            // If deep is true, deep search is called,  recursion
                        	if(deep) {
                        		deepBuildingList.add(nl.item(k));
                                tabulatingList.add(deepness);
                                getNodes(nl.item(k), deepness + 1, true);
                        	} else {
                                shallowBuildingList.add(nl.item(k));
                        	}
                        }
                    }
                }
            }
        }
    }    

}
