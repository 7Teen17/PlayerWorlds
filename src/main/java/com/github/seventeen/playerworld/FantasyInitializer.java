package com.github.seventeen.playerworld;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.dimension.DimensionType;
import xyz.nucleoid.fantasy.Fantasy;
import xyz.nucleoid.fantasy.RuntimeWorldConfig;

public class FantasyInitializer implements ServerLifecycleEvents.ServerStarted {

    public RuntimeWorldConfig worldConfig;

    @Override
    public void onServerStarted(MinecraftServer server) {
        Fantasy fantasy = Fantasy.get(server);
        worldConfig = new RuntimeWorldConfig()
                .setDimensionType(DimensionType.OVERWORLD_REGISTRY_KEY)
                .setDifficulty(Difficulty.EASY)
                .setGameRule(GameRules.DO_DAYLIGHT_CYCLE, false)
                .setGenerator(server.getOverworld().getChunkManager().getChunkGenerator())
                .setSeed(171717L);
    }
}
