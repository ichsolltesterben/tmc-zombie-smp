package com.themaskedcrusader.zombiesmp.utility;

import org.bukkit.Location;

public class StringUtils {

    public static String getLocation(Location location) {
        return "" + location.getBlockX() + location.getBlockY() + location.getBlockZ();
    }
}
