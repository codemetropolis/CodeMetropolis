package codemetropolis.integration.spigot.plugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * FileManager class for config file
 * FileManager
 * @author Dávid Szabolcs
 * @author Deák Tamás
 * @version 1.0
 */
public class FileManager {
    private File configFile;
    private YamlConfiguration config;

    public FileManager(Plugin plugin) {
        configFile = new File(plugin.getDataFolder(), "config.yml");

        if(!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();;
            }
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Return the path of the XML file from config file
     * @return file path
     */
    public String getPath() {
        return config.getString("Path");
    }
}
