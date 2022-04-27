package com.github.seventeen.playerworld.command;

import com.github.seventeen.playerworld.PlanetManager;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.core.jmx.Server;

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

        if (PlanetManager.getPlanetByUUID(player.getUuid()) != null) {
            player.sendMessage(Text.of("Error: You already have a planet!"), false);
            return 0;
        } else {
            playerPlanet = new PlanetManager.Planet(player.getUuid(), name);
            player.sendMessage(Text.of("Sucessfully created Planet " + name + "!"), false);
            return 1;
        }

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

    }

    public static int delete(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
        player.sendMessage(Text.of("Deleting planet..."), false);
        String name = context.getArgument("name", String.class);
        PlanetManager.Planet playerWorld = PlanetManager.getPlanetByUUID(context.getSource().getPlayer().getUuid());
        if (playerWorld == null) {
            player.sendMessage(Text.of("ERROR: Couldn't find your world."), false);
            return 0;

        } else {
            player.sendMessage(Text.of("BEFORE DELETE"), false);

            playerWorld.getWorldHandle().delete();
            player.sendMessage(Text.of("AFTER DELETE"), false);
            PlanetManager.deletePlanetByUUID(context.getSource().getPlayer().getUuid());
            player.sendMessage(Text.of("AFTER DELETE BY UUID"), false);
            context.getSource().getPlayer().sendMessage(Text.of("Successfully deleted your world."), false);

        }
        return 1;
    }

    public static int tp(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        ServerPlayerEntity executor = context.getSource().getPlayer();
        ServerPlayerEntity planetOwner = context.getArgument("player", EntitySelector.class).getPlayer(context.getSource());

        if (PlanetManager.getPlanetByUUID(planetOwner.getUuid()) == null) {

            executor.sendMessage(Text.of("Error: Player does not have a world!"), false);
            return 0;

        }
        PlanetManager.Planet playerPlanet = PlanetManager.getPlanetByUUID(planetOwner.getUuid());

        if (playerPlanet.isPublic()) {

            Vec3d spawn = playerPlanet.getSpawnLocation();
            executor.teleport(playerPlanet.getWorld(), spawn.x, spawn.y, spawn.z, 0, 0);
            return 1;

        } else if (playerPlanet.allowedToVisit(executor.getUuid())) {

            Vec3d spawn = playerPlanet.getSpawnLocation();
            executor.teleport(playerPlanet.getWorld(), spawn.x, spawn.y, spawn.z, 0, 0);
            return 1;

        } else {
            executor.sendMessage(Text.of("Error: Not allowed to visit that player's world."), false);
            return 0;
        }
    }
}
