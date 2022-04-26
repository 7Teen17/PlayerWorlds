package com.github.seventeen.playerworld;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
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
        private Vec3d spawnLocation;
        //TODO: (maybe add spawn setting command)

        public Planet(UUID creator, String name) {

            RuntimeWorldHandle worldHandle = FantasyInitializer.fantasy.getOrOpenPersistentWorld(new Identifier("planet", creator.toString()), FantasyInitializer.worldConfig);
            this.creator = creator;
            this.name = name;
            this.worldHandle = worldHandle;
            this.isPublic = false;
            this.allowedVisitors = new ArrayList<>();
            this.spawnLocation = new Vec3d(0, 70, 0);
            planets.put(creator, this);
        }

        public UUID getCreator() { return this.creator; }

        public String getName() { return this.name; }

        public RuntimeWorldHandle getWorldHandle() { return this.worldHandle; }

        public ServerWorld getWorld() { return this.worldHandle.asWorld(); }

        public Vec3d getSpawnLocation() { return this.spawnLocation; }

        public void setSpawnLocation(Vec3d pos) { this.spawnLocation = pos; }

        public void setPublicity(Boolean publicity) {
          this.isPublic = publicity;
        }

        public Boolean isPublic() { return this.isPublic; }

        public Boolean allowedToVisit(UUID visitor) { return allowedVisitors.contains(visitor); }

        public void addVisitor(UUID visitor) { allowedVisitors.add(visitor); }

        public void removeVisitor(UUID visitor) { allowedVisitors.remove(visitor); }
    }
}
