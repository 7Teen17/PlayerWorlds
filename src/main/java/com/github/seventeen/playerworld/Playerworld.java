package com.github.seventeen.playerworld;

import com.github.seventeen.playerworld.command.PlayerWorldCommandManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class Playerworld implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(new FantasyInitializer());
        PlayerWorldCommandManager.createCommands();
    }
}
