package com.epeshi.esalium.entity.client.renderer;

import com.epeshi.esalium.entity.client.ModModelLayers;
import com.epeshi.esalium.entity.client.model.EmperrorPuModel;
import com.epeshi.esalium.entity.custom.EmperrorPuEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class EmperrorPuRenderer extends MobEntityRenderer<EmperrorPuEntity, EmperrorPuModel<EmperrorPuEntity>> {
    // Texture yolu: esalium
    private static final Identifier TEXTURE = Identifier.of("esalium", "textures/entity/emperror_pu.png");

    public EmperrorPuRenderer(EntityRendererFactory.Context context) {
        super(context, new EmperrorPuModel<>(context.getPart(ModModelLayers.EMPERROR_PU)), 0.7f);
    }

    @Override
    public Identifier getTexture(EmperrorPuEntity entity) {
        return TEXTURE;
    }
}