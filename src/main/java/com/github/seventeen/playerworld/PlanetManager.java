package com.github.seventeen.playerworld;

import com.github.seventeen.playerworld.cca.generic.BooleanComponent;
import com.github.seventeen.playerworld.cca.generic.StringComponent;
import com.github.seventeen.playerworld.cca.generic.UUIDComponent;
import com.github.seventeen.playerworld.cca.generic.Vec3dComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import xyz.nucleoid.fantasy.RuntimeWorldHandle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class PlanetManager {

    public static final ComponentKey<StringComponent> WORLDNAME = ComponentRegistry.getOrCreate(new Identifier("playerworld", "name"), StringComponent.class);
    public static final ComponentKey<UUIDComponent> CREATOR = ComponentRegistry.getOrCreate(new Identifier("playerworld", "creator"), UUIDComponent.class);
    public static final ComponentKey<BooleanComponent> ISPUBLIC = ComponentRegistry.getOrCreate(new Identifier("playerworld", "ispublic"), BooleanComponent.class);
    public static final ComponentKey<Vec3dComponent> SPAWN = ComponentRegistry.getOrCreate(new Identifier("playerworld", "spawn"), Vec3dComponent.class);

    public static HashMap<UUID, Planet> planets = new HashMap<>();

    public static Planet getPlanetByUUID(UUID player) {
        return planets.get(player);
    }

    public static void deletePlanetByUUID(UUID player) { planets.remove(player); }

    public static Planet initializePlanet(MinecraftServer server, UUID player) {
        for (ServerWorld world : server.getWorlds()) {
            if (CREATOR.get(world).getValue().equals(player)) {
                return new Planet(player, WORLDNAME.get(world).getValue());
            }
        }
        return null;
    }

    public static class Planet {

        private ArrayList<UUID> allowedVisitors;
        private final RuntimeWorldHandle worldHandle;

        public Planet(UUID creator, String name) {

            RuntimeWorldHandle worldHandle = FantasyInitializer.fantasy.getOrOpenPersistentWorld(new Identifier("planet", creator.toString()), FantasyInitializer.worldConfig);
            ServerWorld world = worldHandle.asWorld();
            CREATOR.get(world).setValue(creator);
            WORLDNAME.get(world).setValue(name);
            this.worldHandle = worldHandle;
            if (ISPUBLIC.get(world).getValue() == null) {
                ISPUBLIC.get(world).setValue(true);
            }

            this.allowedVisitors = new ArrayList<>();
            SPAWN.get(world).setValue(new Vec3d(0, 70, 0));
            if (planets.get(creator) != null) {
                planets.replace(creator, this);
            } else {
                planets.put(creator, this);
            }

        }

        public RuntimeWorldHandle getWorldHandle() { return this.worldHandle; }

        public UUID getCreator() { return CREATOR.get(this.getWorld()).getValue(); }

        public String getName() { return WORLDNAME.get(this.getWorld()).getValue(); }

        public ServerWorld getWorld() { return this.worldHandle.asWorld(); }

        public Vec3d getSpawnLocation() { return SPAWN.get(this.getWorld()).getValue(); }

        public void setSpawnLocation(Vec3d pos) { SPAWN.get(this.getWorld()).setValue(pos); }

        public void setPublicity(boolean publicity) { ISPUBLIC.get(this.getWorld()).setValue(publicity); }

        public Boolean isPublic() { return ISPUBLIC.get(this.getWorld()).getValue(); }
        //TODO: set value of visitor to either true false or null; true = specifically allowed, false = banned, null = default to isPublic
        public Boolean allowedToVisit(UUID visitor) { return allowedVisitors.contains(visitor); }

        public void addVisitor(UUID visitor) { allowedVisitors.add(visitor); }

        public void removeVisitor(UUID visitor) { allowedVisitors.remove(visitor); }
    }
}
