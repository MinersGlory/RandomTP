package com.minersglory.randomtp.commands;

import com.minersglory.randomtp.RandomTP;
import com.minersglory.randomtp.util.CooldownHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.minersglory.randomtp.RandomTP.plugin;

public class Wild implements CommandExecutor {

    private final CooldownHandler cooldownHandler = new CooldownHandler();

    /*
    TODO: Fix cooldowns
    TODO: Add configurable worlds to be enabled
    TODO:

    */

    /*public final RandomTP plugin;*/

    /*public Wild(RandomTP plugin) {
        this.plugin = plugin;
    }*/



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        String Sender = player.getName();
        int timeLeft = cooldownHandler.getCooldown(player.getUniqueId());
        int cooldownDuration = plugin.getConfig().getInt("cooldown");

        if (sender instanceof Player) {
            if (cmd.getName().equalsIgnoreCase("wild")) {
                if (player.hasPermission("wild.use")) {
                    if(timeLeft == 0){
                        cooldownHandler.setCooldown(player.getUniqueId(), CooldownHandler.DEFAULT_COOLDOWN);
                        new BukkitRunnable() {
                            @Override
                            public void run() {


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

                                player.sendMessage(ChatColor.GRAY + "You have been teleported " + (int) destination.distance(originalLocation) + " blocks away!");

                                // Set the timer countdown
                                int timeLeft = cooldownHandler.getCooldown(player.getUniqueId());
                                cooldownHandler.setCooldown(player.getUniqueId(), --timeLeft);
                                if(timeLeft == 0){
                                    this.cancel();
                                }
                            }
                        }.runTaskTimer(plugin, 20, 20);

                    } else {
                        player.sendMessage(ChatColor.GRAY + "Please wait another " + ChatColor.RED.toString() + timeLeft + ChatColor.GRAY + " seconds before trying again.");
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
