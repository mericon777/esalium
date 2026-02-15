package com.epeshi.esalium;

import com.epeshi.esalium.block.ModBlocks;
import com.epeshi.esalium.block.entity.ModBlockEntities;
import com.epeshi.esalium.client.render.EpicBeaconBlockEntityRenderer;
import com.epeshi.esalium.client.render.LauraRenderer;
import com.epeshi.esalium.entity.ModEntities;
import com.epeshi.esalium.entity.client.ModModelLayers;
import com.epeshi.esalium.entity.client.model.EmperrorPuModel;
import com.epeshi.esalium.entity.client.renderer.EmperrorPuRenderer;
import com.epeshi.esalium.entity.client.renderer.MertRenderer;
import com.epeshi.esalium.entity.client.renderer.SelimRenderer;
import com.epeshi.esalium.screen.EpicBeaconScreen;
import com.epeshi.esalium.screen.ModScreenHandlers;
// import com.epeshi.esalium.util.XaeroIntegration; // GEÇİCİ OLARAK KAPATILDI
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.text.Text;

public class EsaliumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EPIC_BEACON, RenderLayer.getTranslucent());

        HandledScreens.register(ModScreenHandlers.EPIC_BEACON_SCREEN_HANDLER, EpicBeaconScreen::new);

        BlockEntityRendererFactories.register(ModBlockEntities.EPIC_BEACON_BLOCK_ENTITY, EpicBeaconBlockEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.EMPERROR_PU, EmperrorPuRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.EMPERROR_PU, EmperrorPuModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.LAURA, LauraRenderer::new);
        EntityRendererRegistry.register(ModEntities.MERT, MertRenderer::new);
        EntityRendererRegistry.register(ModEntities.SELIM, SelimRenderer::new);

        // Waypoint (Xaero Minimap Entegrasyonu) - GEÇİCİ OLARAK KAPATILDI
        /*
        ClientPlayNetworking.registerGlobalReceiver(Esalium.WaypointPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                if (net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("xaerominimap")) {
                    // İsim çevirisi
                    String translatedName = Text.translatable(payload.translationKey()).getString();

                    // EKSİK OLAN SATIR BURASIYDI:
                    XaeroIntegration.addWaypoint(
                            translatedName,
                            payload.pos().getX(),
                            payload.pos().getY(),
                            payload.pos().getZ(),
                            payload.symbol()
                    );

                    // (İsteğe bağlı) Oyuncuya bilgi mesajı
                    // context.player().sendMessage(Text.literal("§aHarita güncellendi!"), true);
                }
            });
        });
        */
    }
}