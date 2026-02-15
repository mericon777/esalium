package com.epeshi.esalium.screen;

import com.epeshi.esalium.Esalium;
import com.epeshi.esalium.network.EffectC2SPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class EpicBeaconScreen extends HandledScreen<EpicBeaconScreenHandler> {
    // Texture yolu namespace güncellendi: esalium
    private static final Identifier TEXTURE = Identifier.of(Esalium.MOD_ID, "textures/gui/container/epic_beacon_gui.png");

    private static final Identifier SPEED_ICON = Identifier.of("minecraft", "textures/mob_effect/speed.png");
    private static final Identifier HASTE_ICON = Identifier.of("minecraft", "textures/mob_effect/haste.png");
    private static final Identifier RESISTANCE_ICON = Identifier.of("minecraft", "textures/mob_effect/resistance.png");
    private static final Identifier JUMP_BOOST_ICON = Identifier.of("minecraft", "textures/mob_effect/jump_boost.png");
    private static final Identifier STRENGTH_ICON = Identifier.of("minecraft", "textures/mob_effect/strength.png");
    private static final Identifier REGENERATION_ICON = Identifier.of("minecraft", "textures/mob_effect/regeneration.png");

    // --- KOORDİNAT AYARLARI ---
    int speedX = 25, speedY = 18;
    int hasteX = 53, hasteY = 18;
    int resistanceX = 80, resistanceY = 18;
    int jumpX = 30, jumpY = 46;
    int strengthX = 58, strengthY = 46;
    int regenX = 86, regenY = 46;

    public EpicBeaconScreen(EpicBeaconScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 186;
    }

    @Override
    protected void init() {
        super.init();
        addEffectButton(1, speedX, speedY);
        addEffectButton(2, hasteX, hasteY);
        addEffectButton(3, resistanceX, resistanceY);
        addEffectButton(4, jumpX, jumpY);
        addEffectButton(5, strengthX, strengthY);
        addEffectButton(6, regenX, regenY);
    }

    private void addEffectButton(int type, int xPos, int yPos) {
        this.addDrawableChild(ButtonWidget.builder(Text.empty(), button -> {
                    ClientPlayNetworking.send(new EffectC2SPacket(type));
                })
                .dimensions(this.x + xPos, this.y + yPos, 22, 22)
                .build());
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(TEXTURE, this.x, this.y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        int offset = 2;

        drawIcon(context, SPEED_ICON, speedX + offset, speedY + offset);
        drawIcon(context, HASTE_ICON, hasteX + offset, hasteY + offset);
        drawIcon(context, RESISTANCE_ICON, resistanceX + offset, resistanceY + offset);
        drawIcon(context, JUMP_BOOST_ICON, jumpX + offset, jumpY + offset);
        drawIcon(context, STRENGTH_ICON, strengthX + offset, strengthY + offset);
        drawIcon(context, REGENERATION_ICON, regenX + offset, regenY + offset);

        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private void drawIcon(DrawContext context, Identifier icon, int x, int y) {
        context.drawTexture(icon, this.x + x, this.y + y, 0, 0, 18, 18, 18, 18);
    }
}