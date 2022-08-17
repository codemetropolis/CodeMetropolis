package codemetropolis.integration.spigot.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.io.File;
public class Teleport implements CommandExecutor {
    //Class of teleport command

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Actions of the command

        if(sender instanceof Player){
            Player player = (Player) sender;
            //Teleporting action can only happen to Players

            if(args.length == 0){
                player.sendMessage(ChatColor.RED + "Invalid Argument");
                player.sendMessage(ChatColor.BLUE + "Usage: '/tptobuilding' + Name of preferred building");
                //If the user does not provide arguments, alert messages appear in the chat

            }else if(args.length >= 1){
                try {
                    String conc = "";
                    for(int i = 0; i < args.length; i++) {
                        System.out.println(args[i]);
                        conc += (i != 0 ? " " + args[i] : args[i]);
                    }
                    fun(conc, player);
                    //Concatenating arguments given by user

                }catch(Exception e) {
                    //Block of code to handle errors
                }
            }
        }
        return true;
    }

    public static void fun(String s, Player player) throws Exception {
        //Method for getting coordinates from XML file (Coordinates needed to achieve teleport)

        CrunchifyGetPropertyValues properties = new CrunchifyGetPropertyValues();
        System.out.println(properties.getPropValues() + " ");
        //Gets the path of the XML file which contains the coordinates

        try {
        Document document = getDocument(properties.getPropValues());
        String xpathExpression = "/buildables/buildable[contains(@name, '" + s + "')]/position/@x";
        //Using XPath expressions, a String variable is created containing the String given by the user command and the node names we are looking for inside the XML file (in this case the 'x' coordinate node)
        String wxpathExpression = "/buildables/buildable/@id";
        //Using XPath expressions, a String variable is created containing the node name we are looking for inside the XML file (in this case the 'id' node)

        List<String> evaluatedArray = new ArrayList<String>();
        //Array is created to store String of coordinates
        List<String> coordinateList = new ArrayList<String>();
        //Array is created to store final coordinates
        evaluatedArray = evaluateXPath(document, wxpathExpression);
        //Call of evaluateXPath method on the XML file
        int found = 0;
        //An int variable is created to indicate if the user's string was found in the XML
        while (!evaluatedArray.isEmpty()) {

            List<String> temp = new ArrayList<String>();
            //A temporary array is created to store coordinates
            temp = evaluateXPath(document, xpathExpression);
            //Coordinates are loaded into the temporary array

            if (!temp.isEmpty()) {
                //Loops through the coordinates, cutting useless characters, leaving coordinate numbers and adding them to the final coordinateList
                coordinateList.add(temp.get(0));
                //Puts x coordinate number into final coordinate list
                coordinateList.add(evaluateXPath(document, (xpathExpression.split("\\@x", 2)[0] + "@y")).get(0));
                //Puts y coordinate number into final coordinate list, splitting off any unwanted characters
                coordinateList.add(evaluateXPath(document, (xpathExpression.split("\\@x", 2)[0] + "@z")).get(0));
                //Puts z coordinate number into final coordinate list, splitting off any unwanted characters
                World world = player.getWorld();
                //Gets which world the player is in currently (imporart for the location of the teleport)
                Location loc = new Location(world, Double.parseDouble(temp.get(0))-3, Double.parseDouble(evaluateXPath(document, (xpathExpression.split("\\@x", 2)[0] + "@y")).get(0)), Double.parseDouble(evaluateXPath(document, (xpathExpression.split("\\@x", 2)[0] + "@z")).get(0))+4, -90, -30);
                //A location variable is created, which gets the x,y,z coordinates.
                player.teleport(loc);
                //Teleport action to the preferred location is executed
                found = 1;
                //A correct building was found, the teleport was succesful, no further action needed
                break;
            }

            xpathExpression = xpathExpression.split("\\[contains\\(\\@", 2)[0] + "/children/buildable[contains(@" + xpathExpression.split("\\[contains\\(\\@", 2)[1];
            wxpathExpression = wxpathExpression.split("\\/\\@", 2)[0] + "/children/buildable/@" + wxpathExpression.split("\\/\\@", 2)[1];
            evaluatedArray = evaluateXPath(document, wxpathExpression);
        }
        if (found == 0) {
            player.sendMessage(ChatColor.RED + "Invalid Argument: Cannot find specific building");
            //If a correct building name was NOT found in the XML, the teleport was NOT succesful, the user gets an alert chat message
        }
    }catch(Exception e){
            player.sendMessage(ChatColor.DARK_PURPLE + (properties.getPropValues()));
            player.sendMessage(ChatColor.RED + ("Path of the XML file is incorrect. Please check the 'config.properties' file"));
            //If the path of the XML file was set up incorrectly in the config file, the user gets alerting chat messages
    }
    }

    private static List<String> evaluateXPath(Document document, String xpathExpression) throws Exception
    {
        // Create XPathFactory object
        XPathFactory xpathFactory = XPathFactory.newInstance();

        // Create XPath object
        XPath xpath = xpathFactory.newXPath();

        List<String> values = new ArrayList<>();
        try
        {
            // Create XPathExpression object
            XPathExpression expr = xpath.compile(xpathExpression);

            // Evaluate expression result on XML document
            NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < nodes.getLength(); i++) {
                values.add(nodes.item(i).getNodeValue());
            }

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return values;
    }

    private static Document getDocument(String fileName) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(fileName);
        return doc;
    }
}
