package com.epeshi.esalium.block.entity;

import com.epeshi.esalium.Esalium;
import com.epeshi.esalium.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<EpicBeaconBlockEntity> EPIC_BEACON_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Esalium.MOD_ID, "epic_beacon_be"),
                    BlockEntityType.Builder.create(EpicBeaconBlockEntity::new, ModBlocks.EPIC_BEACON).build(null));

    public static void registerBlockEntities() {
        Esalium.LOGGER.info("Block Entityler kaydediliyor...");
    }
}