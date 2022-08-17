package codemetropolis.integration.spigot.plugin;

import org.bukkit.plugin.java.JavaPlugin;


/**
 * Main class for the Plugin
 * Plugin
 * @author Dávid Szabolcs
 * @author Deák Tamás
 * @version 1.0
 */
public class Plugin extends JavaPlugin {


    // FileManager object for config file
    private static FileManager fileManager;

    /**
     * Enable method when Spigot server starts, initializing FileManager object for config file, also initializing the executable command needed
     */
    @Override
    public void onEnable() {
        fileManager = new FileManager(this);
        getCommand("sub-buildings").setExecutor(new SubBuildingCommand(fileManager.getPath()));
    }

}
