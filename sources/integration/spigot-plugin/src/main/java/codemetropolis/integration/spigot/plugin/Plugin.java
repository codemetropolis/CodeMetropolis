package codemetropolis.integration.spigot.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {


    @Override
    public void onEnable() {
        //Plugin startup
        System.out.println("[CodeMetropolis] Your teleport plugin has started");
        //Message in the Spigot server runner terminal, tells the user the plugin has successfully started
        getCommand("tpToBuilding").setExecutor(new Teleport());
        //Teleport command executor
    }


}
