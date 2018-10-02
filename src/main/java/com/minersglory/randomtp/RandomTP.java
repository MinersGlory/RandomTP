package com.minersglory.randomtp;

import com.minersglory.randomtp.commands.Wild;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;
import java.io.File;

public class RandomTP extends JavaPlugin {

    public final Logger logger = getLogger();

    File config = new File(this.getDataFolder(), "config.yml");

    public void onEnable() {
        if (config.exists()) {
            // Do nothing
        } else {
            // Create config
            this.saveDefaultConfig();
        }
        logger.info("RandomTP has been enabled.");
        this.getCommand("wild").setExecutor(new Wild());
    }

    public void onDisable() {
        logger.info("RandomTP has been disabled.");
    }

}

