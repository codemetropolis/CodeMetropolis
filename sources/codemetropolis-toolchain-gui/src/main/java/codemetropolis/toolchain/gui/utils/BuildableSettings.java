package codemetropolis.toolchain.gui.utils;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import codemetropolis.toolchain.gui.beans.BadConfigFileFomatException;

/**
 * This class is responsible for providing information which buildable attributes are desired by the user to display on the GUI.
 */
public class BuildableSettings {
	/**
	 * {@link Map}, which contains the default settings of what properties to display to the individual buildable types.
	 */
	public static final Map<String, String[]> DEFAULT_SETTINGS = new HashMap<>();
	
	/**
	 * {@Map}, which contains the types (int, float(0 to 1),...) of the possible buildable attributes.
	 */
	public static final Map<String, String> BUILDABLE_ATTRIBUTE_TYPES = new HashMap<String, String>();
	
	/**
	 * Stores the possible values, which can be applied to the buildable attributes "character" and "external_character".
	 */
	public static final List<String> VALID_CHARACTER_TYPES = new ArrayList<String>(Arrays.asList(new String[] {
			"stone", "cobblestone", "mossy_stone", "sandstone", "obsidian", 
			"wood", "dark_wood", "birch_wood", "planks", "dark_planks", "metal",
			"dirt", "sand", "red_sand", "brick", "stone_brick", "dark_brick",
			"glass", "gold", "diamond"	
	}));
	
	/**
	 * Path of the file containing the settings.
	 */
	private static final String CFG_FILEPATH ="./src/main/resources/buildableProperties.cmcfg";
	
	/**
	 * {@link Map}, serves containing the buildable types(floor, garden, ...) and its assigned properties(height, character, ...).
	 */
	private static Map<String, String[]> DISPLAYED_PROPERTIES = new HashMap<String, String[]>();

    static {
        DEFAULT_SETTINGS.put("FLOOR", new String[]{"width", "height", "length", "character", "external_character", "torches"});
        DEFAULT_SETTINGS.put("CELLAR", new String[]{"width", "height", "length", "character", "external_character", "torches"});
        DEFAULT_SETTINGS.put("GARDEN", new String[]{"tree-ratio", "mushroom-ratio", "flower-ratio"});
        DEFAULT_SETTINGS.put("GROUND", new String[]{});
        
		DISPLAYED_PROPERTIES.put("FLOOR", new String[] {});
        DISPLAYED_PROPERTIES.put("CELLAR", new String[] {});
        DISPLAYED_PROPERTIES.put("GARDEN", new String[] {});
        DISPLAYED_PROPERTIES.put("GROUND", new String[] {});
        
        BUILDABLE_ATTRIBUTE_TYPES.put("width", "int");
        BUILDABLE_ATTRIBUTE_TYPES.put("height", "int");
        BUILDABLE_ATTRIBUTE_TYPES.put("length", "int");
        BUILDABLE_ATTRIBUTE_TYPES.put("character", "string");
        BUILDABLE_ATTRIBUTE_TYPES.put("external_character", "string");
        BUILDABLE_ATTRIBUTE_TYPES.put("torches", "int(0 to 5)");
        BUILDABLE_ATTRIBUTE_TYPES.put("tree-ratio", "float(0 to 1)");
        BUILDABLE_ATTRIBUTE_TYPES.put("mushroom-ratio", "float(0 to 1)");
        BUILDABLE_ATTRIBUTE_TYPES.put("flower-ratio", "float(0 to 1)");
	}
	
    /**
     * Reads the user's display settings from the configuration file.
     * @return A {@link Map} which contains the possible buildable types as keys and its attributes, which are desired to be displayed.
     * @throws BadConfigFileFomatException If the format of the configuration file is not appropriate.
     * @throws FileNotFoundException If the configuration file cannot be found.
     */
	public static Map<String, String[]> readSettings() throws BadConfigFileFomatException, FileNotFoundException{
		BufferedReader cfgFileReader = null;
		
		cfgFileReader = new BufferedReader(new FileReader(CFG_FILEPATH));
		
		try{			
			String actLine;
			//A regular line: buildable name is followed by an '=' sign, that follows the list of attributes separated by commas. 
			while((actLine = cfgFileReader.readLine()).split("=").length == 2) {				
				String buildableName = actLine.split("=")[0];
				String[] buildableProperties = actLine.split("=")[1].split(",");
				if(DISPLAYED_PROPERTIES.containsKey(buildableName)) {
					//If there is no assigned attribute given to the actual buildable type...
					if(buildableProperties.length == 1 && buildableProperties[0].isEmpty()) {
						DISPLAYED_PROPERTIES.put(buildableName, new String[] {});
					}
					else {
						if(validateProperties(buildableName, buildableProperties)) {
							DISPLAYED_PROPERTIES.put(buildableName, buildableProperties);
						}
						else {
							throw new BadConfigFileFomatException();
						}
					}
				}
				else {
					throw new BadConfigFileFomatException();
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				cfgFileReader.close();
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return DISPLAYED_PROPERTIES;		
	}
	
	/**
	 * Validates, if all assigned attributes of the specified buildable type are valid or not.
	 * @param buildableType The type of the buildable (floor, garden, ...)
	 * @param buildabeAttributes The array of the attributes which are examined if they are valid or not.
	 * @return All of the specified buildable attributes are valid or not.
	 */
	private static boolean validateProperties(String buildableType, String[] buildabeAttributes) {
		List<String> validProperties = new ArrayList<String>(Arrays.asList(DEFAULT_SETTINGS.get(buildableType)));
		for(String property : buildabeAttributes) {
			if(!validProperties.contains(property)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Writes to the console, what display settings will be provided to the Mapping file editor GUI.
	 */
	public static void displaySettings() {
		try {
			Map<String, String[]> returnedSettings = readSettings();
			
			for(String buildableType : returnedSettings.keySet()) {
				
				String[] buildableProperties = returnedSettings.get(buildableType);
				
				System.out.print(buildableType + "=");
				
				for(int i = 0; i < buildableProperties.length; i++) {
					
					System.out.print(buildableProperties[i] + ";");
					
				}
				System.out.println();
			}
		} 
		catch (BadConfigFileFomatException e) {
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
}