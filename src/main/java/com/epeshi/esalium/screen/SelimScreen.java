package com.epeshi.esalium.screen;

import com.epeshi.esalium.Esalium;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SelimScreen extends Screen {
    private int centerX;
    private int startY;

    public SelimScreen() {
        super(Text.literal("Selim"));
    }

    public static void open() {
        net.minecraft.client.MinecraftClient.getInstance().setScreen(new SelimScreen());
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

        // Başlık
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("--- Selim ---").formatted(Formatting.AQUA, Formatting.BOLD), centerX, startY, 0xFFFFFFFF);

        // Merhaba Yazısı
        context.drawCenteredTextWithShadow(this.textRenderer, Text.translatable("entity.esalium.selim.hello"), centerX, startY + 25, 0xFFFFFFFF);

        // BUTON 1: Görevi Kabul Et (Totem Ver)
        int acceptBtnY = startY + 60;
        boolean hoverAccept = isMouseOver(mouseX, mouseY, centerX, acceptBtnY, "gui.esalium.accept_quest");
        Text acceptText = Text.translatable("gui.esalium.accept_quest").formatted(Formatting.GREEN, Formatting.BOLD);
        context.drawCenteredTextWithShadow(this.textRenderer, acceptText, centerX, acceptBtnY, hoverAccept ? 0xFFFFAA00 : 0xFFFFFFFF);

        // BUTON 2: Konuşmayı Sonlandır (Kapat)
        int closeBtnY = startY + 90;
        boolean hoverClose = isMouseOver(mouseX, mouseY, centerX, closeBtnY, "gui.esalium.end_conversation");
        Text closeText = Text.translatable("gui.esalium.end_conversation").formatted(Formatting.RED);
        context.drawCenteredTextWithShadow(this.textRenderer, closeText, centerX, closeBtnY, hoverClose ? 0xFFFF5555 : 0xFFAAAAAA);
    }

    private boolean isMouseOver(double mouseX, double mouseY, int x, int y, String key) {
        int textWidth = this.textRenderer.getWidth(Text.translatable(key));
        return mouseX >= x - (textWidth / 2.0) - 8 && mouseX <= x + (textWidth / 2.0) + 8 &&
                mouseY >= y - 4 && mouseY <= y + 14;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Görevi Kabul Et
        if (isMouseOver(mouseX, mouseY, centerX, startY + 60, "gui.esalium.accept_quest")) {
            if (this.client != null && this.client.player != null) {
                // Paketi gönder (Totem ver)
                ClientPlayNetworking.send(new Esalium.SelimRewardPayload());
                this.close();
            }
            return true;
        }

        // Konuşmayı Sonlandır
        if (isMouseOver(mouseX, mouseY, centerX, startY + 90, "gui.esalium.end_conversation")) {
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