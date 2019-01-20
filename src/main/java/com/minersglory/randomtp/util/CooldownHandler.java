package com.minersglory.randomtp.util;

import com.minersglory.randomtp.RandomTP;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.minersglory.randomtp.RandomTP.plugin;

public class CooldownHandler {
    Player player = null;

    int coolDownTime = plugin.getConfig().getInt("cooldown");

    Map<String, Long> cooldowns = new HashMap<String, Long>();
    static Map<String, CooldownHandler> cooldownMap = new HashMap<String, CooldownHandler>();

    public void start() {
        cooldowns.put(player.getUniqueId().toString(), System.currentTimeMillis());
        cooldownMap.put(player.getUniqueId().toString(), this);
    }


    public void finalize() {
        cooldowns.remove(player.getUniqueId().toString());
        cooldownMap.remove(player.getUniqueId().toString());
    }

    public long getTimeLeft(Player player) {
        long startTime = (System.currentTimeMillis() - cooldowns.get(player.getUniqueId().toString())) / 1000;
        return startTime - coolDownTime;
    }

    public boolean check(Player player) {
        if(((cooldowns.get(player.getUniqueId().toString()) - System.currentTimeMillis()) / 1000) < coolDownTime)
            return false;
        else
            return true;
    }

    public static CooldownHandler getCooldown(Player player) {
        return cooldownMap.get(player.getUniqueId().toString());
    }



    /* phase this out
    public void setCooldown(UUID player, long time){
        if(time < 1) {
            cooldowns.remove(player);
        } else {
            cooldowns.put(player, time);
        }
    }

    public int getCooldown(UUID player){
        return cooldowns.getOrDefault(player, 0);
    }
    */


}
