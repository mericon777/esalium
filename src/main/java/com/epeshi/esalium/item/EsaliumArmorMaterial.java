package com.epeshi.esalium.item;

import com.epeshi.esalium.Esalium;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class EsaliumArmorMaterial {

    public static final RegistryEntry<ArmorMaterial> ESALIUM_ARMOR_MATERIAL = registerArmorMaterial("esalium",
            () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 4);
                        map.put(ArmorItem.Type.LEGGINGS, 7);
                        map.put(ArmorItem.Type.CHESTPLATE, 9);
                        map.put(ArmorItem.Type.HELMET, 4);
                        map.put(ArmorItem.Type.BODY, 12);
                    }),
                    25,
                    SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,
                    () -> Ingredient.ofItems(ModItems.ESALIUM_INGOT),
                    List.of(new ArmorMaterial.Layer(Identifier.of(Esalium.MOD_ID, "esalium"))),
                    4.0f,
                    0.2f
            )
    );

    public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(Esalium.MOD_ID, name), material.get());
    }
}