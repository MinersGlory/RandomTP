package com.minersglory.randomtp.commands;

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
    public long timeleft;
    // TODO: ACTUALLY GET THIS VALUE FROM A CONFIG FILE
    int cooldownDuration = 60;
    public long cooldowntime = cooldownDuration * 1000;


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if ((cmd.getName().equalsIgnoreCase("wild")) && ((sender instanceof Player))) {

            Player player = (Player) sender;
            String Sender = player.getName();

            if (player.hasPermission("wild.use")) {

                if (this.cooldowns.containsKey(Sender) && System.currentTimeMillis() - cooldowns.get(Sender) <= cooldowntime) {
                    player.sendMessage(ChatColor.GRAY + "Please wait another " + Math.round((System.currentTimeMillis() - cooldowns.get(Sender)) / 1000) + ChatColor.GRAY + " seconds before trying again.");
                } else {
                    Location originalLocation = player.getLocation();

                    Random random = new Random();

                    Location destination = null;

                    int Min = 100;
                    int Max = 9999;


                    // TODO: ACTUALLY GET THIS VALUES FROM A CONFIG FILE
                    int x = random.nextInt(Max - Min + Min);
                    int y = 150;
                    int z = random.nextInt(Max - Min + Min);

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


                    cooldowns.put(Sender, System.currentTimeMillis());
                }

            } else {
                player.sendMessage(ChatColor.DARK_RED + "You don't have permission to use this command!");
            }
            return true;
        }
        return false;
    }

}
