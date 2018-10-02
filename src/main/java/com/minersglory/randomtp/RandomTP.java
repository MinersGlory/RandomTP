package com.minersglory.randomtp;

import com.minersglory.randomtp.commands.Wild;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

public class RandomTP extends JavaPlugin {

    public final Logger logger = getLogger();



    public void onEnable() {
        logger.info("RandomTP has been enabled.");
        this.getCommand("wild").setExecutor(new Wild());
    }

    public void onDisable() {
        logger.info("RandomTP has been disabled.");
    }

}

