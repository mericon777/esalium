package com.epeshi.esalium.screen;

import com.epeshi.esalium.Esalium;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<EpicBeaconScreenHandler> EPIC_BEACON_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Esalium.MOD_ID, "epic_beacon"),
                    new ScreenHandlerType<>(EpicBeaconScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static void registerScreenHandlers() {
        Esalium.LOGGER.info("Epic Beacon Screen Handler kaydediliyor...");
    }
}