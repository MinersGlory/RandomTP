package com.minersglory.randomtp;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;
import java.util.logging.Logger;

public class RandomTP extends JavaPlugin {

    public final Logger logger = Logger.getLogger("Minecraft");
    public Permission permission = new Permission("wild");

    public void onEnable() {

        this.logger.info("RandomTP has been enabled.");
        PluginManager pm = getServer().getPluginManager();
        pm.addPermission(this.permission);
    }

    public void onDisable() {
        this.logger.info("RandomTP has been disabled.");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ((cmd.getName().equalsIgnoreCase("wild")) && ((sender instanceof Player))) {
            Player player = (Player) sender;
            if (player.hasPermission("wild.use")) {
                Location originalLocation = player.getLocation();

                Random random = new Random();

                Location destination = null;

                int x = random.nextInt(100) + 1;
                int y = 150;
                int z = random.nextInt(100) + 1;

                boolean isLandMass = false;
                while (!isLandMass) {
                    destination = new Location(player.getWorld(), x, y, z);
                    if (destination.getBlock().getType() != Material.AIR) {
                        isLandMass = true;
                    } else {
                        y--;
                    }
                }
                player.teleport(new Location(player.getWorld(), destination.getX(), destination.getY() + 1.0D, destination.getZ()));

                player.sendMessage(ChatColor.GREEN + "You have been teleported " + (int) destination.distance(originalLocation) + " blocks away!");
            } else {
                player.sendMessage(ChatColor.DARK_RED + "You don't have permission to use this command!");
            }
            return true;
        }
        return false;
    }
}

