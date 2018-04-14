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

public class BuildableSettings {
	
	//Path of the file containing the settings.
	private static final String CFG_FILEPATH ="./src/main/resources/buildableProperties.cmcfg";
	
	//Map, serves containing the buildable types and its assigned properties.
	private static Map<String, String[]> DISPLAYED_PROPERTIES = new HashMap<String, String[]>();
	
	//Map, which contains the default settings of what properties to display to the individual buildable types.
	public static final Map<String, String[]> DEFAULT_SETTINGS = new HashMap<>();

    static {
        DEFAULT_SETTINGS.put("FLOOR", new String[]{"width", "height", "length", "character", "external_character", "torches"});
        DEFAULT_SETTINGS.put("CELLAR", new String[]{"width", "height", "length", "character", "external_character", "torches"});
        DEFAULT_SETTINGS.put("GARDEN", new String[]{"tree-ratio", "mushroom-ratio", "flower-ratio"});
        DEFAULT_SETTINGS.put("GROUND", new String[]{});
        
		DISPLAYED_PROPERTIES.put("FLOOR", new String[] {});
        DISPLAYED_PROPERTIES.put("CELLAR", new String[] {});
        DISPLAYED_PROPERTIES.put("GARDEN", new String[] {});
        DISPLAYED_PROPERTIES.put("GROUND", new String[] {});
	}
	
    //Reads the user's display settings from the configuration file.
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
	
	//Validates, if all assigned properties of the specified buildable type are valid or not.
	private static boolean validateProperties(String buildableName, String[] buildabeProperties) {
		List<String> validProperties = new ArrayList<String>(Arrays.asList(DEFAULT_SETTINGS.get(buildableName)));
		for(String property : buildabeProperties) {
			if(!validProperties.contains(property)) {
				return false;
			}
		}
		return true;
	}
	
	//Writes to the console, what display settings will be provided to the Mapping file editor GUI.
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