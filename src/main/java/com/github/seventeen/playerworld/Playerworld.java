package com.github.seventeen.playerworld;

import com.github.seventeen.playerworld.command.PlayerWorldCommandManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class Playerworld implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(new FantasyInitializer());
        ServerPlayConnectionEvents.JOIN.register(new PlayerPlanetInitializer());
        PlayerWorldCommandManager.createCommands();
        //TODO: add planet list initializer here to grab all worlds
    }
}
