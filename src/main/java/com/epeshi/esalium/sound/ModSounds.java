package com.epeshi.esalium.sound; // Paket ismi güncellendi

import com.epeshi.esalium.Esalium;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    // Sesimizi Tanımlıyoruz
    public static final SoundEvent BOSS_MUSIC = registerSoundEvent("boss_music");

    private static SoundEvent registerSoundEvent(String name) {
        // Mod ID: esalium
        Identifier id = Identifier.of(Esalium.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSounds() {
        Esalium.LOGGER.info("Registering Mod Sounds for " + Esalium.MOD_ID);
    }
}