package com.example.loginmod;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockPlaceEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class PlayerActionListener {
    public static void register() {
        // Prevent block breaking
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            if (!isAuthenticated(player)) {
                sendLoginMessage(player);
                return false;
            }
            return true;
        });
        
        // Prevent block placing
        PlayerBlockPlaceEvents.BEFORE.register((world, player, pos, state) -> {
            if (!isAuthenticated(player)) {
                sendLoginMessage(player);
                return false;
            }
            return true;
        });
        
        // Prevent using items
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (!isAuthenticated(player)) {
                sendLoginMessage(player);
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
        
        // Prevent using blocks
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!isAuthenticated(player)) {
                sendLoginMessage(player);
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
        
        // Prevent interacting with entities
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!isAuthenticated(player)) {
                sendLoginMessage(player);
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
    }
    
    private static boolean isAuthenticated(PlayerEntity player) {
        if (!(player instanceof ServerPlayerEntity)) {
            return true;
        }
        
        LoginManager loginManager = LoginMod.getLoginManager();
        return loginManager != null && loginManager.isAuthenticated((ServerPlayerEntity) player);
    }
    
    private static void sendLoginMessage(PlayerEntity player) {
        player.sendMessage(Text.literal("Please login first using /login <password>").formatted(Formatting.RED), false);
    }
}