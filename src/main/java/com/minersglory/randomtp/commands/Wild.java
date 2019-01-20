package com.minersglory.randomtp.commands;

import com.minersglory.randomtp.RandomTP;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.minersglory.randomtp.RandomTP.plugin;

public class Wild implements CommandExecutor {
    /*public final RandomTP plugin;*/

    /*public Wild(RandomTP plugin) {
        this.plugin = plugin;
    }*/


    public HashMap<String, Long> cooldowns = new HashMap<>();
    public long timeleft;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        String Sender = player.getName();

        int cooldownDuration = RandomTP.getValues().getInt("cooldown");
        long cooldowntime = cooldownDuration * 1000;
        if (sender instanceof Player) {
            if (cmd.getName().equalsIgnoreCase("wild")) {
                if (player.hasPermission("wild.use")) {
                    if (this.cooldowns.containsKey(Sender) && System.currentTimeMillis() + cooldowns.get(Sender) <= cooldowntime) {
                        player.sendMessage(ChatColor.GRAY + "Please wait another " + Math.round((System.currentTimeMillis() - cooldowns.get(Sender)) / 1000) + ChatColor.GRAY + " seconds before trying again.");
                    } else {
                        Location originalLocation = player.getLocation();

                        // Get enabled worlds from config
                        List<String> active_worlds = RandomTP.getValues().getStringList("active-worlds");

                        String currentWorld = player.getWorld().getName();
                        Location spawnpoint = plugin.getServer().getWorld(currentWorld).getSpawnLocation();


                        Random random = new Random();

                        Location destination = null;


                        // Get max and minimum distance from config.yml
                        int max = RandomTP.getValues().getInt("range.max");
                        int min = RandomTP.getValues().getInt("range.min");

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

                        player.sendMessage(ChatColor.GRAY + "You have been teleported " + (int) destination.distance(originalLocation) + " blocks away!");


                        cooldowns.put(Sender, System.currentTimeMillis());
                    }

                } else {
                    plugin.logger.info(ChatColor.RED + "You have no permission!");
                }
            }
        }
        plugin.logger.info("You must be a player to use that command!");
        return false;
        }
}
