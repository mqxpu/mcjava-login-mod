package com.example.loginmod;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginMod implements DedicatedServerModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "mcjava-login-mod";
    private static LoginManager loginManager;
    private static final String LOGIN_CHANNEL = "mcjava:login";

    @Override
    public void onInitializeServer() {
        LOGGER.info("Initializing MCJava Login Mod");
        
        // Initialize login manager
        loginManager = new LoginManager();
        
        // Register login network channel
        ServerLoginNetworking.registerGlobalReceiver(LOGIN_CHANNEL, (server, handler, understood, buf, synchronizer, responder) -> {
            if (!understood) {
                responder.accept(null, Text.literal("Client does not have the login mod installed"));
                return;
            }
            
            String playerName = handler.getPlayerName();
            String password = buf.readString(100);
            
            server.execute(() -> {
                if (loginManager.validatePassword(playerName, password)) {
                    responder.accept(null, null);
                } else {
                    responder.accept(null, Text.literal("Incorrect password"));
                }
            });
        });
        
        // Register login start event
        ServerLoginConnectionEvents.QUERY_START.register((handler, server, synchronizer) -> {
            String playerName = handler.getPlayerName();
            
            // Send password request
            PacketByteBuf buf = new PacketByteBuf(synchronizer.createPacketBuffer());
            buf.writeBoolean(loginManager.hasPassword(playerName));
            buf.writeString(playerName);
            
            synchronizer.send(LOGIN_CHANNEL, buf);
        });
        
        // Register commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            LoginCommand.register(dispatcher, loginManager);
        });
        
        // Server shutdown event
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            loginManager.savePasswords();
        });
        
        LOGGER.info("MCJava Login Mod initialized successfully");
    }
    
    public static LoginManager getLoginManager() {
        return loginManager;
    }
}