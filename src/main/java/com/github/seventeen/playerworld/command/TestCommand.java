package com.github.seventeen.playerworld.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class TestCommand implements Command<ServerCommandSource> {
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        context.getSource().getPlayer().teleport(context.getSource().getServer().getWorld(ServerWorld.OVERWORLD), 0, 70, 0, 0, 0);
        return 1;
    }
}
