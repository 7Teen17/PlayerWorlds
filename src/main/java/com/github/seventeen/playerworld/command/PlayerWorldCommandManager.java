package com.github.seventeen.playerworld.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class PlayerWorldCommandManager {
    public static void createCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {

            LiteralCommandNode<ServerCommandSource> deleteNode = CommandManager
                    .literal("adminworlddelete")
                    .build();

                ArgumentCommandNode<ServerCommandSource, String> deleteNode1 = CommandManager
                        .argument("iden1", StringArgumentType.string())
                        .build();

                ArgumentCommandNode<ServerCommandSource, String> deleteNode2 = CommandManager
                        .argument("iden2", StringArgumentType.string())
                        .executes(PlanetCommand::admindelete)
                        .build();

            LiteralCommandNode<ServerCommandSource> planetNode = CommandManager
                .literal("planet")
                .build();

                LiteralCommandNode<ServerCommandSource> planetHelpNode = CommandManager
                        .literal("help")
                        .executes(PlanetCommand::help)
                        .build();

                LiteralCommandNode<ServerCommandSource> planetCreateNode = CommandManager
                        .literal("create")
                        .build();

                    ArgumentCommandNode<ServerCommandSource, String> planetCreateNameNode = CommandManager
                            .argument("name", StringArgumentType.string())
                            .executes(PlanetCommand::create)
                            .build();

                LiteralCommandNode<ServerCommandSource> planetDeleteNode = CommandManager
                        .literal("delete")
                        .build();

                    ArgumentCommandNode<ServerCommandSource, String> planetDeleteNameNode = CommandManager
                            .argument("name", StringArgumentType.string())
                            .executes(PlanetCommand::delete)
                            .build();

                LiteralCommandNode<ServerCommandSource> planetTeleportNode = CommandManager
                        .literal("tp")
                        .build();

                    ArgumentCommandNode<ServerCommandSource, EntitySelector> planetTeleportNameNode = CommandManager
                            .argument("player", EntityArgumentType.player())
                            .executes(PlanetCommand::tp)
                            .build();

                LiteralCommandNode<ServerCommandSource> planetInfoNode = CommandManager
                        .literal("info")
                        .build();

                    ArgumentCommandNode<ServerCommandSource, EntitySelector> planetInfoNameNode = CommandManager
                            .argument("player", EntityArgumentType.player())
                            .executes(PlanetCommand::info)
                            .build();

                LiteralCommandNode<ServerCommandSource> planetSettingsNode = CommandManager
                        .literal("settings")
                        .build();

                    LiteralCommandNode<ServerCommandSource> planetSettingsPublicNode = CommandManager
                            .literal("setpublic")
                            .build();

                        ArgumentCommandNode<ServerCommandSource, Boolean> planetSettingsPublicToggleNode = CommandManager
                                .argument("isPublic", BoolArgumentType.bool())
                                .executes(PlanetCommand::setPublic)
                                .build();

                    LiteralCommandNode<ServerCommandSource> planetSettingsAddVisitorNode = CommandManager
                            .literal("addvisitor")
                            .build();

                        ArgumentCommandNode<ServerCommandSource, EntitySelector> planetSettingsAddVisitorPlayerNode = CommandManager
                                .argument("player", EntityArgumentType.player())
                                .executes(PlanetCommand::addVisitor)
                                .build();

                    LiteralCommandNode<ServerCommandSource> planetSettingsDeleteVisitorNode = CommandManager
                            .literal("removevisitor")
                            .build();

                        ArgumentCommandNode<ServerCommandSource, EntitySelector> planetSettingsDeleteVisitorPlayerNode = CommandManager
                                .argument("player", EntityArgumentType.player())
                                .executes(PlanetCommand::deleteVisitor)
                                .build();

                LiteralCommandNode<ServerCommandSource> spawnNode = CommandManager
                        .literal("spawn")
                        .executes(new TestCommand())
                        .build();


                dispatcher.getRoot().addChild(deleteNode);
                deleteNode.addChild(deleteNode1);
                deleteNode1.addChild(deleteNode2);
            dispatcher.getRoot().addChild(planetNode);

                planetNode.addChild(spawnNode);

                planetNode.addChild(planetHelpNode);

                planetNode.addChild(planetCreateNode);
                    planetCreateNode.addChild(planetCreateNameNode);

                planetNode.addChild(planetDeleteNode);
                    planetDeleteNode.addChild(planetDeleteNameNode);

                planetNode.addChild(planetTeleportNode);
                    planetTeleportNode.addChild(planetTeleportNameNode);

                planetNode.addChild(planetInfoNode);
                    planetInfoNode.addChild(planetInfoNameNode);

                planetNode.addChild(planetSettingsNode);

                    planetSettingsNode.addChild(planetSettingsPublicNode);
                        planetSettingsPublicNode.addChild(planetSettingsPublicToggleNode);

                    planetSettingsNode.addChild(planetSettingsAddVisitorNode);
                        planetSettingsAddVisitorNode.addChild(planetSettingsAddVisitorPlayerNode);

                    planetSettingsNode.addChild(planetSettingsDeleteVisitorNode);
                        planetSettingsDeleteVisitorNode.addChild(planetSettingsDeleteVisitorPlayerNode);
        });
    }
}
