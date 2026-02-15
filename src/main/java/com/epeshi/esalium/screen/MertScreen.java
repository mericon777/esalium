package com.epeshi.esalium.screen;

import com.epeshi.esalium.Esalium;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class MertScreen extends Screen {
    private final boolean isEndGame;
    private int centerX;
    private int startY;

    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 20;

    public MertScreen(boolean isEndGame) {
        // Key: entity.esalium.mert
        super(Text.translatable("entity.esalium.mert"));
        this.isEndGame = isEndGame;
    }

    public static void open(boolean isEndGame) {
        net.minecraft.client.MinecraftClient.getInstance().setScreen(new MertScreen(isEndGame));
    }

    @Override
    protected void init() {
        this.centerX = this.width / 2;
        this.startY = this.height / 2 - 50;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        // Key: gui.esalium.mert.header
        Text title = Text.translatable("gui.esalium.mert.header").formatted(Formatting.AQUA, Formatting.BOLD);
        context.drawCenteredTextWithShadow(this.textRenderer, title, centerX, startY - 25, 0xFFFFFFFF);

        if (!isEndGame) {
            // BAŞLANGIÇ DİYALOGLARI (Keyler: entity.esalium...)
            drawTextLine(context, "entity.esalium.mert.start.line1", 0);
            drawTextLine(context, "entity.esalium.mert.start.line2", 15);
            drawTextLine(context, "entity.esalium.mert.start.line3", 30);
            drawTextLine(context, "entity.esalium.mert.start.line4", 45);

            // BUTON 1: Haritayı Al
            int mapBtnY = startY + 80;
            boolean hoverMap = isMouseOverButton(mouseX, mouseY, mapBtnY);
            Text mapText = Text.translatable("gui.esalium.give_map").formatted(Formatting.GOLD, Formatting.BOLD);
            context.drawCenteredTextWithShadow(this.textRenderer, mapText, centerX, mapBtnY, hoverMap ? 0xFFFFAA00 : 0xFFFFFFFF);

            // BUTON 2: Çıkış
            int exitBtnY = startY + 105;
            boolean hoverExit = isMouseOverButton(mouseX, mouseY, exitBtnY);
            Text exitText = Text.translatable("gui.esalium.exit").formatted(Formatting.GRAY);
            context.drawCenteredTextWithShadow(this.textRenderer, exitText, centerX, exitBtnY, hoverExit ? 0xFFFFFFFF : 0xFFAAAAAA);

        } else {
            // FİNAL DİYALOGLARI
            context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("entity.esalium.mert.end.line1").formatted(Formatting.GREEN, Formatting.BOLD), centerX, startY, 0xFFFFFFFF);
            drawTextLine(context, "entity.esalium.mert.end.line2", 15);
            drawTextLine(context, "entity.esalium.mert.end.line3", 30);
            drawTextLine(context, "entity.esalium.mert.end.line4", 45);

            // BUTON: Teşekkürler Mert
            int thanksBtnY = startY + 80;
            boolean hoverThanks = isMouseOverButton(mouseX, mouseY, thanksBtnY);
            Text thanksText = Text.translatable("gui.esalium.thanks_mert").formatted(Formatting.GOLD, Formatting.BOLD);
            context.drawCenteredTextWithShadow(this.textRenderer, thanksText, centerX, thanksBtnY, hoverThanks ? 0xFFFFAA00 : 0xFFFFFFFF);
        }
    }

    private void drawTextLine(DrawContext context, String key, int yOffset) {
        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable(key), centerX, startY + yOffset, 0xFFFFFFFF);
    }

    private boolean isMouseOverButton(double mouseX, double mouseY, int y) {
        return mouseX >= centerX - (BUTTON_WIDTH / 2) && mouseX <= centerX + (BUTTON_WIDTH / 2)
                && mouseY >= y - (BUTTON_HEIGHT / 2) && mouseY <= y + (BUTTON_HEIGHT / 2);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (!isEndGame) {
                // Haritayı Al (False gönder)
                if (isMouseOverButton(mouseX, mouseY, startY + 80)) {
                    ClientPlayNetworking.send(new Esalium.MertActionPayload(false));
                    this.close();
                    return true;
                }
                // Çıkış
                if (isMouseOverButton(mouseX, mouseY, startY + 105)) {
                    this.close();
                    return true;
                }
            } else {
                // Final (True gönder)
                if (isMouseOverButton(mouseX, mouseY, startY + 80)) {
                    ClientPlayNetworking.send(new Esalium.MertActionPayload(true));
                    this.close();
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() { return false; }
}