package com.github.seventeen.playerworld.command;

import com.github.seventeen.playerworld.FantasyInitializer;
import com.github.seventeen.playerworld.PlanetManager;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.EntitySelector;
import net.minecraft.server.ServerTask;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

//TODO: mixin to on world join or smth with a variable if its new or not and reset the structure that way
public class PlanetCommand {

    public static int admindelete(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String iden1 = context.getArgument("iden1", String.class);
        String iden2 = context.getArgument("iden2", String.class);
        FantasyInitializer.fantasy.getOrOpenPersistentWorld(new Identifier(iden1, iden2), FantasyInitializer.worldConfig).delete();
        return 1;
    }

    public static int help(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        context.getSource().getPlayer().sendMessage(Text.of("PLANET SYSTEM"), false);
        context.getSource().getPlayer().sendMessage(Text.of("/planet create <name>: Creates a planet with the given name."), false);
        context.getSource().getPlayer().sendMessage(Text.of("/planet delete <name>: Deletes a planet with the given name."), false);
        return 1;
    }

    public static int create(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayer();
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
    public static int info(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        ServerPlayerEntity executor = context.getSource().getPlayer();
        ServerPlayerEntity planetOwner = context.getArgument("player", EntitySelector.class).getPlayer(context.getSource());
        PlanetManager.Planet playerPlanet = PlanetManager.getPlanetByUUID(planetOwner.getUuid());

        if (playerPlanet == null) {
            executor.sendMessage(Text.of("Error: Player does not have a world!"), false);
            return 0;
        }
        executor.sendMessage(Text.of("Planet " + playerPlanet.getName()), false);
        executor.sendMessage(Text.of(""), false);
        executor.sendMessage(Text.of("Owner: " + planetOwner.getName().asString()), false);
        executor.sendMessage(Text.of("Public: " + playerPlanet.isPublic().toString()), false);
        //TODO: change to status: and add BANNED, allowed, public instead of true false null
        executor.sendMessage(Text.of("Allowed to visit: " + playerPlanet.allowedToVisit(executor.getUuid()).toString()), false);
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

        } else if (playerPlanet.allowedToVisit(executor.getUuid()) || playerPlanet.getCreator().equals(executor.getUuid())) {

            Vec3d spawn = playerPlanet.getSpawnLocation();
            executor.teleport(playerPlanet.getWorld(), spawn.x, spawn.y, spawn.z, 0, 0);
            return 1;

        } else {
            executor.sendMessage(Text.of("Error: Not allowed to visit that player's world."), false);
            return 0;
        }
    }

    public static int setPublic(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Boolean publicity = context.getArgument("isPublic", Boolean.class);
        ServerPlayerEntity executor = context.getSource().getPlayer();
        assert PlanetManager.getPlanetByUUID(executor.getUuid()) != null;
        PlanetManager.getPlanetByUUID(executor.getUuid()).setPublicity(publicity);
        executor.sendMessage(Text.of("Successfully set Planet publicity to " + publicity.toString() + "."), false);
        return 1;
    }

    public static int addVisitor(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity addedPlayer = context.getArgument("player", EntitySelector.class).getPlayer(context.getSource());
        ServerPlayerEntity executor = context.getSource().getPlayer();
        PlanetManager.getPlanetByUUID(executor.getUuid()).addVisitor(addedPlayer.getUuid());
        executor.sendMessage(Text.of("Successfully added " + addedPlayer.getName().asString() + " as a visitor."), false);
        return 1;
    }

    public static int deleteVisitor(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity addedPlayer = context.getArgument("player", EntitySelector.class).getPlayer(context.getSource());
        ServerPlayerEntity executor = context.getSource().getPlayer();
        PlanetManager.getPlanetByUUID(executor.getUuid()).removeVisitor(addedPlayer.getUuid());
        executor.sendMessage(Text.of("Successfully removed " + addedPlayer.getName().asString() + " as a visitor."), false);
        return 1;
    }
}
