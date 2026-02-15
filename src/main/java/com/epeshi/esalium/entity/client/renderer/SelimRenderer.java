package com.epeshi.esalium.entity.client.renderer;

import com.epeshi.esalium.Esalium;
import com.epeshi.esalium.entity.custom.SelimEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;

public class SelimRenderer extends MobEntityRenderer<SelimEntity, PlayerEntityModel<SelimEntity>> {
    // Doku dosyasının adı 'selim.png' olmalı
    private static final Identifier TEXTURE = Identifier.of(Esalium.MOD_ID, "textures/entity/selim.png");

    public SelimRenderer(EntityRendererFactory.Context context) {
        // PLAYER modelini çağırıyoruz (Steve Modeli)
        super(context, new PlayerEntityModel<>(context.getPart(EntityModelLayers.PLAYER), false), 0.5f);
    }

    @Override
    public Identifier getTexture(SelimEntity entity) {
        return TEXTURE;
    }
}