package com.github.seventeen.playerworld.command;

import com.github.seventeen.playerworld.FantasyInitializer;
import com.github.seventeen.playerworld.PlanetManager;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xyz.nucleoid.fantasy.RuntimeWorldHandle;

public class PlanetCommand {

    public static int help(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        context.getSource().getPlayer().sendMessage(Text.of("Help Message for planet system: none yet lol"), false);
        return 1;
    }

    public static int create(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String name = context.getArgument("name", String.class);
        PlanetManager.Planet playerWorld;
        context.getSource().getPlayer().sendMessage(Text.of("Creating planet with name " + name + "..."), false);
        RuntimeWorldHandle worldHandle = FantasyInitializer.fantasy.getOrOpenPersistentWorld(new Identifier("planet", name), FantasyInitializer.worldConfig);
        if (PlanetManager.getPlanetByUUID(context.getSource().getPlayer().getUuid()) == null) {
            playerWorld = new PlanetManager.Planet(worldHandle, context.getSource().getPlayer().getUuid(), name);
        } else {
            playerWorld = PlanetManager.getPlanetByUUID(context.getSource().getPlayer().getUuid());
        }

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
            context.getSource().getPlayer().sendMessage(Text.of("Successfully deleted your world."), false);
        }
        return 1;
    }
}
