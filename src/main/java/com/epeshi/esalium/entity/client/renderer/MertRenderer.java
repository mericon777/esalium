package com.epeshi.esalium.entity.client.renderer;

import com.epeshi.esalium.Esalium;
import com.epeshi.esalium.entity.custom.MertEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;

public class MertRenderer extends MobEntityRenderer<MertEntity, PlayerEntityModel<MertEntity>> {
    // Texture yolu namespace güncellendi: esalium
    private static final Identifier TEXTURE = Identifier.of(Esalium.MOD_ID, "textures/entity/mert.png");

    public MertRenderer(EntityRendererFactory.Context context) {
        super(context, new PlayerEntityModel<>(context.getPart(EntityModelLayers.PLAYER_SLIM), true), 0.5f);
    }

    @Override
    public Identifier getTexture(MertEntity entity) {
        return TEXTURE;
    }
}