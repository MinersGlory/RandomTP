package com.minersglory.randomtp;

import com.minersglory.randomtp.commands.Wild;
import com.minersglory.randomtp.util.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class RandomTP extends JavaPlugin {
    public static RandomTP plugin;
    public static final Logger logger = Logger.getLogger("Minecraft");

    File configFile = new File(this.getDataFolder(), "config.yml");
    private static FileConfiguration values;


    @Override
    public void onEnable() {
        plugin = this;
        if (configFile.exists()) {
            // Register commands
            getCommand("wild").setExecutor(new Wild());
            logger.info(ChatColor.GREEN + "Registered listeners");
            logger.info(ChatColor.GREEN + "RandomTP has been enabled.");
        } else {
            this.saveDefaultConfig();
            logger.info(ChatColor.GRAY + "Generated config.yml");
            this.saveConfig();
        }



    }

    @Override
    public void onDisable() {
        saveConfig();
        plugin = null;
        logger.info("RandomTP has been disabled.");
    }

    /**
     * Get an instance of the main RandomTP class
     *
     * @return an instance of the RandoMTP class
     */
    public static RandomTP getPlugin() {
        return plugin;
    }

    public static FileConfiguration getValues() {
        return values;
    }

    public static void setValues(FileConfiguration values) {
        RandomTP.values = values;
    }

}

