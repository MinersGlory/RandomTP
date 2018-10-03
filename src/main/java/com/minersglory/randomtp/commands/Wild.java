package com.minersglory.randomtp.commands;

import com.minersglory.randomtp.RandomTP;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.HashMap;
import java.util.Random;

public class Wild implements CommandExecutor {

    public Permission permission = new Permission("wild");

    public HashMap<String, Long> cooldowns = new HashMap<String, Long>();
    // TODO: ACTUALLY GET THIS VALUE FROM A CONFIG FILE
    int cooldownDuration = 60;
    public long cooldowntime = cooldownDuration * 1000;

    private RandomTP plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if ((cmd.getName().equalsIgnoreCase("wild")) && ((sender instanceof Player))) {

            Player player = (Player) sender;
            String Sender = player.getName();

            if (player.hasPermission("wild.use")) {
                    Location originalLocation = player.getLocation();

                    Random random = new Random();

                    Location destination = null;

                    int max = plugin.getConfig().getInt("range.max");
                    int min = plugin.getConfig().getInt("range.min");


                    // TODO: ACTUALLY GET THIS VALUES FROM A CONFIG FILE
                    int x = random.nextInt(max - min + min);
                    int y = 150;
                    int z = random.nextInt(max - min + min);

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
