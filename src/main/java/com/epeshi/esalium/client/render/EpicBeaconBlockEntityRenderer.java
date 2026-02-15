package com.epeshi.esalium.client.render;

import com.epeshi.esalium.block.entity.EpicBeaconBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public class EpicBeaconBlockEntityRenderer implements BlockEntityRenderer<EpicBeaconBlockEntity> {
    public static final Identifier BEAM_TEXTURE = Identifier.of("minecraft", "textures/entity/beacon_beam.png");

    public EpicBeaconBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(EpicBeaconBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.getWorld() == null) return;

        long time = entity.getWorld().getTime();

        // KIRMIZI RENK AYARI:
        // Alpha: 255 (Tam görünür)
        // Red: 255 (Maksimum Kırmızı)
        // Green: 0
        // Blue: 0
        int colorInt = ColorHelper.Argb.getArgb(255, 255, 0, 0);

        // 1.21.1'in beklediği 'int' alan versiyon:
        BeaconBlockEntityRenderer.renderBeam(
                matrices,
                vertexConsumers,
                BEAM_TEXTURE,
                tickDelta,
                1.0f,      // heightScale
                time,
                0,         // yOffset
                1024,      // maxY
                colorInt,  // Artık kırmızı renk gönderiliyor
                0.2f,      // innerRadius
                0.25f      // outerRadius
        );
    }

    @Override
    public boolean rendersOutsideBoundingBox(EpicBeaconBlockEntity blockEntity) {
        return true;
    }
}