package com.github.seventeen.playerworld.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class PlayerWorldCommandManager {
    public static void createCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {

            LiteralCommandNode<ServerCommandSource> planetNode = CommandManager
                .literal("planet")
                .executes(new TestCommand())
                .build();

            LiteralCommandNode<ServerCommandSource> planetHelpNode = CommandManager
                    .literal("help")
                    .executes(PlanetCommand::help)
                    .build();

            LiteralCommandNode<ServerCommandSource> planetCreateNode = CommandManager
                    .literal("create")
                    //.executes(PlanetCommand::create)
                    .build();

            ArgumentCommandNode<ServerCommandSource, String> planetCreateNameNode = CommandManager
                    .argument("name", StringArgumentType.string())
                    .executes(PlanetCommand::create)
                    .build();

            LiteralCommandNode<ServerCommandSource> planetDeleteNode = CommandManager
                    .literal("delete")
                    .executes(PlanetCommand::delete)
                    .build();

            dispatcher.getRoot().addChild(planetNode);

                planetNode.addChild(planetHelpNode);

                planetNode.addChild(planetCreateNode);
                    planetCreateNode.addChild(planetCreateNameNode);

                planetNode.addChild(planetDeleteNode);
        });
    }
}
