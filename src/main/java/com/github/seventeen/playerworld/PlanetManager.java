package com.github.seventeen.playerworld;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import xyz.nucleoid.fantasy.RuntimeWorldHandle;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class PlanetManager {

    public static HashMap<UUID, Planet> planets = new HashMap<>();

    public static Planet getPlanetByUUID(UUID player) {
        return planets.get(player);
    }

    public static void deletePlanetByUUID(UUID player) { planets.replace(player, null); }

    public static void getPlanetByName() {

    }

    public static class Planet {

        private ArrayList<UUID> allowedVisitors;
        private Boolean isPublic;
        private final UUID creator;
        private final String name;
        private final RuntimeWorldHandle worldHandle;
        //TODO: Add list of doubles for spawn location and getSpawn() command (maybe add spawn setting command)

        public Planet(UUID creator, String name) {

            RuntimeWorldHandle worldHandle = FantasyInitializer.fantasy.getOrOpenPersistentWorld(new Identifier("planet", creator.toString()), FantasyInitializer.worldConfig);
            this.creator = creator;
            this.name = name;
            this.worldHandle = worldHandle;
            this.isPublic = false;
            this.allowedVisitors = new ArrayList<>();
            planets.put(creator, this);
        }

        public UUID getCreator() { return creator; }

        public String getName() { return name; }

        public RuntimeWorldHandle getWorldHandle() { return worldHandle; }

        public ServerWorld getWorld() { return worldHandle.asWorld(); }

        public void setVisibility(Boolean visibility) {
          this.isPublic = visibility;
        }
    }
}
