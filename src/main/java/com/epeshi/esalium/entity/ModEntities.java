package com.epeshi.esalium.entity;

import com.epeshi.esalium.Esalium;
import com.epeshi.esalium.entity.custom.EmperrorPuEntity;
import com.epeshi.esalium.entity.custom.LauraEntity;
import com.epeshi.esalium.entity.custom.MertEntity;
import com.epeshi.esalium.entity.custom.SelimEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<EmperrorPuEntity> EMPERROR_PU = Registry.register(
            Registries.ENTITY_TYPE,
            // KRİTİK GÜNCELLEME: "svetariumingot" -> "esalium"
            Identifier.of("esalium", "emperror_pu"),
            EntityType.Builder.create(EmperrorPuEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.9f, 2.3f)
                    .build()
    );

    public static final EntityType<LauraEntity> LAURA = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Esalium.MOD_ID, "laura"),
            EntityType.Builder.create(LauraEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.6f, 1.8f)
                    .build()
    );

    public static final EntityType<MertEntity> MERT = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Esalium.MOD_ID, "mert"),
            EntityType.Builder.create(MertEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.6f, 1.8f)
                    .build()
    );

    public static final EntityType<SelimEntity> SELIM = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Esalium.MOD_ID, "selim"),
            EntityType.Builder.create(SelimEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.6f, 1.8f).build());

    public static void registerModEntities() {
        Esalium.LOGGER.info("Registering Entities for " + Esalium.MOD_ID);
    }
}