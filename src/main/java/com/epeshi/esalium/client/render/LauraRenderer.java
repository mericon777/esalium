package com.epeshi.esalium.client.render;

import com.epeshi.esalium.Esalium;
import com.epeshi.esalium.entity.custom.LauraEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class LauraRenderer extends BipedEntityRenderer<LauraEntity, BipedEntityModel<LauraEntity>> {

    private static final Identifier TEXTURE = Identifier.of(Esalium.MOD_ID, "textures/entity/laura.png");

    public LauraRenderer(EntityRendererFactory.Context context) {
        // PLAYER yerine PLAYER_SLIM kullanarak Alex (ince kol) modeline geçiyoruz
        super(context, new BipedEntityModel<>(context.getPart(EntityModelLayers.PLAYER_SLIM)), 0.5f);
    }

    @Override
    public Identifier getTexture(LauraEntity entity) {
        return TEXTURE;
    }
}