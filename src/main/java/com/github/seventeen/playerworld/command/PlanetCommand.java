package com.github.seventeen.playerworld.command;

import com.github.seventeen.playerworld.FantasyInitializer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import xyz.nucleoid.fantasy.RuntimeWorldHandle;

public class PlanetCommand {

    public static int help(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        context.getSource().getPlayer().sendMessage(Text.of("Help Message for planet system: none yet lol"), false);
        return 1;
    }

    public static int create(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String name = context.getArgument("name", String.class);
        context.getSource().getPlayer().sendMessage(Text.of("Creating planet with name " + name + "..."), false);
        RuntimeWorldHandle worldHandle = FantasyInitializer.fantasy.openTemporaryWorld(FantasyInitializer.worldConfig);
        ServerWorld world = worldHandle.asWorld();
        context.getSource().getPlayer().sendMessage(Text.of("Teleporting to world " + name + "."), false);
        context.getSource().getPlayer().teleport(world, 0, 70, 0, 0, 0);
        return 1;
    }

    public static int delete(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        context.getSource().getPlayer().sendMessage(Text.of("Deleting planet..."), false);
        return 1;
    }
}
