package com.epeshi.esalium.screen;

import com.epeshi.esalium.Esalium;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class LauraScreen extends Screen {
    private int centerX;
    private int startY;

    public LauraScreen() {
        super(Text.literal("Laura"));
    }

    public static void open() {
        net.minecraft.client.MinecraftClient.getInstance().setScreen(new LauraScreen());
    }

    @Override
    protected void init() {
        this.centerX = this.width / 2;
        this.startY = this.height / 2 - 60;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        // --- HİKAYE METİNLERİ (Keyler: entity.esalium...) ---
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("--- Laura ---").formatted(Formatting.GOLD, Formatting.BOLD), centerX, startY, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("entity.esalium.laura.line1"), centerX, startY + 25, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("entity.esalium.laura.line2"), centerX, startY + 40, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("entity.esalium.laura.line3"), centerX, startY + 55, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("entity.esalium.laura.line4"), centerX, startY + 70, 0xFFFFFFFF);

        // --- SEÇENEKLER ---

        // Spawnpoint (Key: gui.esalium.set_spawn)
        int spawnY = startY + 105;
        boolean hoverSpawn = isMouseOver(mouseX, mouseY, centerX, spawnY, "gui.esalium.set_spawn");
        Text spawnText = Text.translatable("gui.esalium.set_spawn").formatted(Formatting.YELLOW, Formatting.BOLD);
        context.drawCenteredTextWithShadow(this.textRenderer, spawnText, centerX, spawnY, hoverSpawn ? 0xFFFFFFFF : 0xFFFFF200);

        // Çıkış (Key: gui.esalium.exit)
        int exitY = startY + 125;
        boolean hoverExit = isMouseOver(mouseX, mouseY, centerX, exitY, "gui.esalium.exit");
        Text exitText = Text.translatable("gui.esalium.exit").formatted(Formatting.GRAY);
        context.drawCenteredTextWithShadow(this.textRenderer, exitText, centerX, exitY, hoverExit ? 0xFFFF5555 : 0xFFAAAAAA);
    }

    private boolean isMouseOver(double mouseX, double mouseY, int x, int y, String key) {
        int textWidth = this.textRenderer.getWidth(Text.translatable(key));
        return mouseX >= x - (textWidth / 2.0) - 8 && mouseX <= x + (textWidth / 2.0) + 8 &&
                mouseY >= y - 4 && mouseY <= y + 14;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Spawnpoint seçeneği
        if (isMouseOver(mouseX, mouseY, centerX, startY + 105, "gui.esalium.set_spawn")) {
            if (this.client != null && this.client.player != null) {
                // Networking Paketi: Esalium.SpawnPayload
                ClientPlayNetworking.send(new Esalium.SpawnPayload());
                this.close();
            }
            return true;
        }

        // Çıkış seçeneği
        if (isMouseOver(mouseX, mouseY, centerX, startY + 125, "gui.esalium.exit")) {
            this.close();
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}