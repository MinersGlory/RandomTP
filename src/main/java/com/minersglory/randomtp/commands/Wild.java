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
import java.util.List;
import java.util.Random;

public class Wild implements CommandExecutor {

    public RandomTP plugin;

    public Permission permission = new Permission("wild");
    public long timeleft;
    public HashMap<String, Long> cooldowns = new HashMap<>();

    // TODO: ACTUALLY GET THIS VALUE FROM A CONFIG FILE
    int cooldownDuration = plugin.getConfig().getInt("cooldown");
    public long cooldowntime = cooldownDuration * 1000;


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if ((cmd.getName().equalsIgnoreCase("wild")) && ((sender instanceof Player))) {

            Player player = (Player) sender;
            String Sender = player.getName();

            if (player.hasPermission("wild.use")) {

                if (this.cooldowns.containsKey(Sender) && System.currentTimeMillis() + cooldowns.get(Sender) <= cooldowntime) {
                    player.sendMessage(ChatColor.GRAY + "Please wait another " + Math.round((System.currentTimeMillis() - cooldowns.get(Sender)) / 1000) + ChatColor.GRAY + " seconds before trying again.");
                } else {
                    Location originalLocation = player.getLocation();

                    // Get enabled worlds from config
                    List<String> active_worlds = plugin.getConfig().getStringList("active-worlds");

                    String currentWorld = player.getWorld().getName();
                    Location spawnpoint = plugin.getServer().getWorld(currentWorld).getSpawnLocation();


                    Random random = new Random();

                    Location destination = null;


                    // Get max and minimum distance from config.yml
                    int max = plugin.getConfig().getInt("range.max");
                    int min = plugin.getConfig().getInt("range.min");

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
