package com.github.seventeen.playerworld;

import net.minecraft.server.world.ServerWorld;
import xyz.nucleoid.fantasy.RuntimeWorldHandle;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class PlanetManager {

    public static HashMap<UUID, Planet> planets;

    //THIS IS BROKEN FIX IT RN
    public static Planet getPlanetByUUID(UUID player) {
        return planets.get(player);
    }

    public static void getPlanetByName() {

    }

    public static class Planet {

        private final UUID creator;
        private final String name;
        private final RuntimeWorldHandle worldHandle;

        public Planet(RuntimeWorldHandle worldHandle, UUID creator, String name) {
            this.creator = creator;
            this.name = name;
            this.worldHandle = worldHandle;
            planets.put(creator, this);
        }

        public UUID getCreator() { return creator; }

        public String getName() { return name; }

        public RuntimeWorldHandle getWorldHandle() { return worldHandle; }

        public ServerWorld getWorld() { return worldHandle.asWorld(); }
    }
}
