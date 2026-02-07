package com.example.loginmod;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginManager {
    private final Map<String, String> playerPasswords = new HashMap<>();
    private final Map<String, Boolean> authenticatedPlayers = new HashMap<>();
    private static final String PASSWORD_FILE = "config/mcjava-login-mod/passwords.txt";
    
    public LoginManager() {
        loadPasswords();
    }
    
    public void onPlayerJoin(ServerPlayerEntity player) {
        String playerName = player.getName().getString();
        authenticatedPlayers.put(playerName, false);
        
        if (!playerPasswords.containsKey(playerName)) {
            player.sendMessage(Text.literal("Welcome! Please set a password using /login setpassword <password>").formatted(Formatting.YELLOW), false);
        } else {
            player.sendMessage(Text.literal("Please login using /login <password>").formatted(Formatting.YELLOW), false);
        }
        
        // Kick if not authenticated after 60 seconds
        new Thread(() -> {
            try {
                Thread.sleep(60000);
                if (!authenticatedPlayers.getOrDefault(playerName, false) && player.server.getPlayerManager().getPlayer(playerName) != null) {
                    player.kick(Text.literal("Login timeout - please try again"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public void onPlayerLeave(ServerPlayerEntity player) {
        String playerName = player.getName().getString();
        authenticatedPlayers.remove(playerName);
    }
    
    public boolean loginPlayer(ServerPlayerEntity player, String password) {
        String playerName = player.getName().getString();
        
        if (!playerPasswords.containsKey(playerName)) {
            player.sendMessage(Text.literal("No password set - use /login setpassword <password>").formatted(Formatting.RED), false);
            return false;
        }
        
        if (playerPasswords.get(playerName).equals(password)) {
            authenticatedPlayers.put(playerName, true);
            player.sendMessage(Text.literal("Login successful!").formatted(Formatting.GREEN), false);
            return true;
        } else {
            player.sendMessage(Text.literal("Incorrect password!").formatted(Formatting.RED), false);
            return false;
        }
    }
    
    public boolean setPassword(ServerPlayerEntity player, String password) {
        String playerName = player.getName().getString();
        playerPasswords.put(playerName, password);
        authenticatedPlayers.put(playerName, true);
        savePasswords();
        player.sendMessage(Text.literal("Password set successfully!").formatted(Formatting.GREEN), false);
        return true;
    }
    
    public boolean isAuthenticated(ServerPlayerEntity player) {
        String playerName = player.getName().getString();
        return authenticatedPlayers.getOrDefault(playerName, false);
    }
    
    public boolean validatePassword(String playerName, String password) {
        if (!playerPasswords.containsKey(playerName)) {
            return false;
        }
        return playerPasswords.get(playerName).equals(password);
    }
    
    public boolean hasPassword(String playerName) {
        return playerPasswords.containsKey(playerName);
    }
    
    public void savePasswords() {
        File file = new File(PASSWORD_FILE);
        file.getParentFile().mkdirs();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<String, String> entry : playerPasswords.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            LoginMod.LOGGER.error("Failed to save passwords: " + e.getMessage());
        }
    }
    
    public void loadPasswords() {
        File file = new File(PASSWORD_FILE);
        if (!file.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    playerPasswords.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            LoginMod.LOGGER.error("Failed to load passwords: " + e.getMessage());
        }
    }
}