package com.example.loginmod;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class LoginCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, LoginManager loginManager) {
        dispatcher.register(CommandManager.literal("login")
            .then(CommandManager.argument("password", StringArgumentType.greedyString())
                .executes(context -> {
                    return executeLogin(context, loginManager);
                })
            )
            .then(CommandManager.literal("setpassword")
                .then(CommandManager.argument("password", StringArgumentType.greedyString())
                    .executes(context -> {
                        return executeSetPassword(context, loginManager);
                    })
                )
            )
        );
    }
    
    private static int executeLogin(CommandContext<ServerCommandSource> context, LoginManager loginManager) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendError(Text.literal("This command can only be used by players"));
            return 0;
        }
        
        String password = StringArgumentType.getString(context, "password");
        loginManager.loginPlayer(player, password);
        return 1;
    }
    
    private static int executeSetPassword(CommandContext<ServerCommandSource> context, LoginManager loginManager) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendError(Text.literal("This command can only be used by players"));
            return 0;
        }
        
        String password = StringArgumentType.getString(context, "password");
        loginManager.setPassword(player, password);
        return 1;
    }
}