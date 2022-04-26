package com.github.seventeen.playerworld.command;

import com.github.seventeen.playerworld.PlanetManager;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelector;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlanetCommand {

    public static int help(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        context.getSource().getPlayer().sendMessage(Text.of("PLANET SYSTEM"), false);
        context.getSource().getPlayer().sendMessage(Text.of("/planet create <name>: Creates a planet with the given name."), false);
        context.getSource().getPlayer().sendMessage(Text.of("/planet delete <name>: Deletes a planet with the given name."), false);
        return 1;
    }

    public static int create(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        //context.getSource().getPlayer().sendMessage(Text.of("start"), false);
        String name = context.getArgument("name", String.class);
        PlanetManager.Planet playerPlanet;
        player.sendMessage(Text.of("Creating planet with name " + name + "..."), false);
        if (PlanetManager.getPlanetByUUID(player.getUuid()) == null) {
            playerPlanet = new PlanetManager.Planet(player.getUuid(), name);
        }
        PlanetManager.Planet playerWorld = new PlanetManager.Planet(player.getUuid(), name);

        //TODO: FINISH REWRITING THIS!!!

        /*
        RuntimeWorldHandle worldHandle = FantasyInitializer.fantasy.getOrOpenPersistentWorld(new Identifier("planet", name), FantasyInitializer.worldConfig);
        //context.getSource().getPlayer().sendMessage(Text.of("before uuid get"), false);
        if (PlanetManager.getPlanetByUUID(context.getSource().getPlayer().getUuid()) == null) {
            //context.getSource().getPlayer().sendMessage(Text.of("after"), false);
            playerWorld = new PlanetManager.Planet(worldHandle, context.getSource().getPlayer().getUuid(), name);
        } else {
            //context.getSource().getPlayer().sendMessage(Text.of("after2"), false);
            playerWorld = PlanetManager.getPlanetByUUID(context.getSource().getPlayer().getUuid());
        }
        */
        context.getSource().getPlayer().sendMessage(Text.of("Teleporting to world " + name + "."), false);
        context.getSource().getPlayer().teleport(playerWorld.getWorld(), 0, 70, 0, 0, 0);
        return 1;
    }

    public static int delete(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        context.getSource().getPlayer().sendMessage(Text.of("Deleting planet..."), false);
        String name = context.getArgument("name", String.class);
        PlanetManager.Planet playerWorld = PlanetManager.getPlanetByUUID(context.getSource().getPlayer().getUuid());
        if (playerWorld == null) {
            context.getSource().getPlayer().sendMessage(Text.of("ERROR: Couldn't find your world."), false);
            return 0;
        } else {
            playerWorld.getWorldHandle().delete();
            PlanetManager.deletePlanetByUUID(context.getSource().getPlayer().getUuid());
            context.getSource().getPlayer().sendMessage(Text.of("Successfully deleted your world."), false);
        }
        return 1;
    }

    public static int tp(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getArgument("player", EntitySelector.class).getPlayer(context.getSource());
        if (PlanetManager.getPlanetByUUID(player.getUuid()) != null) {
            //TODO: Finish teleport command and get planet's spawn to tp to
            //Add check for if player is in allowedVisitors before tping
        }
        return 1;
    }
}
