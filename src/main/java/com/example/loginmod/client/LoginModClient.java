package com.example.loginmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class LoginModClient implements ClientModInitializer {
    private static final String LOGIN_CHANNEL = "mcjava:login";

    @Override
    public void onInitializeClient() {
        // Register login network channel
        ClientLoginNetworking.registerGlobalReceiver(LOGIN_CHANNEL, (client, handler, buf, synchronizer) -> {
            boolean hasPassword = buf.readBoolean();
            String playerName = buf.readString(100);
            
            // Open password input screen
            synchronizer.waitFor(server -> {
                MinecraftClient.getInstance().execute(() -> {
                    MinecraftClient.getInstance().setScreen(new PasswordInputScreen(hasPassword, playerName, synchronizer));
                });
            });
        });
    }
    
    public static class PasswordInputScreen extends Screen {
        private final boolean hasPassword;
        private final String playerName;
        private final ClientLoginNetworking.LoginSynchronizer synchronizer;
        private TextFieldWidget passwordField;
        private ButtonWidget submitButton;
        private ButtonWidget cancelButton;
        private Text message;

        public PasswordInputScreen(boolean hasPassword, String playerName, ClientLoginNetworking.LoginSynchronizer synchronizer) {
            super(Text.literal("Server Login"));
            this.hasPassword = hasPassword;
            this.playerName = playerName;
            this.synchronizer = synchronizer;
            this.message = hasPassword ? Text.literal("Enter your password to join the server:") : Text.literal("No password set for this account");
        }

        @Override
        protected void init() {
            int centerX = width / 2;
            int centerY = height / 2;
            
            passwordField = new TextFieldWidget(textRenderer, centerX - 100, centerY - 20, 200, 20, Text.literal("Password"));
            passwordField.setMaxLength(100);
            passwordField.setFocusUnlocked(true);
            passwordField.setFocused(true);
            addDrawableChild(passwordField);
            
            submitButton = ButtonWidget.builder(Text.literal("Submit"), button -> {
                sendPassword(passwordField.getText());
            }).position(centerX - 105, centerY + 10).size(95, 20).build();
            addDrawableChild(submitButton);
            
            cancelButton = ButtonWidget.builder(Text.literal("Cancel"), button -> {
                synchronizer.abort(Text.literal("Login cancelled"));
            }).position(centerX + 10, centerY + 10).size(95, 20).build();
            addDrawableChild(cancelButton);
        }

        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            renderBackground(matrices);
            drawCenteredText(matrices, textRenderer, title, width / 2, height / 2 - 60, 0xFFFFFF);
            drawCenteredText(matrices, textRenderer, message, width / 2, height / 2 - 40, 0xFFFFFF);
            super.render(matrices, mouseX, mouseY, delta);
        }

        private void sendPassword(String password) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeString(password);
            
            ClientLoginNetworking.send(LOGIN_CHANNEL, buf);
            synchronizer.suspend();
        }

        @Override
        public boolean shouldCloseOnEsc() {
            return false;
        }
    }
}