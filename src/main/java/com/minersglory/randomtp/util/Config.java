package com.minersglory.randomtp.util;

import com.minersglory.randomtp.RandomTP;

public class Config {

    public static void generateDefaults() {
        RandomTP.getValues().addDefault("cooldown", 60);
        RandomTP.getValues().addDefault("range.max", 9999);
        RandomTP.getValues().addDefault("range.min", 100);
        RandomTP.getValues().addDefault("active-worlds", "world");
    }
}
