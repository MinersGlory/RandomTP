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
import java.util.concurrent.TimeUnit;

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

        if (!cmd.getName().equalsIgnoreCase("wild")) {
            return true;
        }

        if(!(sender instanceof Player)) {
            plugin.logger.info(ChatColor.GRAY + "You must be a player to use that command!");
            return true;
        }


        Player player = (Player) sender;
        String Sender = player.getName();

        if(!(player.hasPermission("wild.use"))) {
            player.sendMessage(ChatColor.RED + "Insufficient permissions!");
            return true;
        }

        if (args.length > 0) {
            player.sendMessage(ChatColor.GRAY + "Invalid command syntax! " +  ChatColor.DARK_GREEN + "Usage: /wild");
            return true;
        }

        CooldownHandler cooldown = CooldownHandler.getCooldown(player);
        if(!cooldown.check(player) && (cooldown.getTimeLeft(player) * -1) >= 1) {
            player.sendMessage("Please wait " + (cooldown.getTimeLeft(player) * -1) + " seconds to use this again!");
            return true;
        } else if ((cooldown.getTimeLeft(player) * -1) == 0) {
            player.sendMessage(ChatColor.GREEN + "You can teleport again!");
            cooldown.finalize();
        } else {
            player.sendMessage(ChatColor.GREEN + "You can teleport again!");
            cooldown.finalize();
        }

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
                        return true;
                }


        }

