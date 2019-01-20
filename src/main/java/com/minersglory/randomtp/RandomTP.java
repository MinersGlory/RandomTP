package com.minersglory.randomtp;

import com.minersglory.randomtp.commands.Wild;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;
import java.io.File;

public class RandomTP extends JavaPlugin {

    public static RandomTP plugin;

    PluginCommand wildCmd = getCommand("wild");

    public final Logger logger = getLogger();

    File config = new File(this.getDataFolder(), "config.yml");

    @Override
    public void onEnable() {
        plugin = this;
        if (config.exists()) {
            logger.info("RandomTP has been enabled.");

            // Register commands
            wildCmd.setExecutor(new Wild(this));
        } else {
            // Create config
            this.saveDefaultConfig();
            logger.info("Generated default config.yml");
        }
    }

    @Override
    public void onDisable() {
        logger.info("RandomTP has been disabled.");
        plugin = null;
    }

    /**
     * Get an instance of the main RandomTP class
     *
     * @return an instance of the RandoMTP class
     */
    public static RandomTP getPlugin() {
        return plugin;
    }


}

