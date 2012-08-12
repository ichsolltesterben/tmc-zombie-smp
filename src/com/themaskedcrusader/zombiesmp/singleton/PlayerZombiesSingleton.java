package com.themaskedcrusader.zombiesmp.singleton;

public class PlayerZombiesSingleton {

    private static PlayerZombiesSingleton instance = null;

    private PlayerZombiesSingleton() {
        // Exists only to defeat instantiation.
    }

    public static PlayerZombiesSingleton getInstance() {
        if (instance == null) {
            instance = new PlayerZombiesSingleton();
        }
        return instance;
    }
}
