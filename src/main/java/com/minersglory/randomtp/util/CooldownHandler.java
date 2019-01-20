package com.minersglory.randomtp.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.minersglory.randomtp.RandomTP.plugin;

public class CooldownHandler {

    private final Map<UUID, Integer> cooldowns = new HashMap<>();
    public static final int DEFAULT_COOLDOWN = plugin.getConfig().getInt("cooldown");

    public void setCooldown(UUID player, int time){
        if(time < 1) {
            cooldowns.remove(player);
        } else {
            cooldowns.put(player, time);
        }
    }

    public int getCooldown(UUID player){
        return cooldowns.getOrDefault(player, 0);
    }


}
