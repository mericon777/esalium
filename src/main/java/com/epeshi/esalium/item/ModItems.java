package com.epeshi.esalium.item;

import com.epeshi.esalium.Esalium;
import com.epeshi.esalium.entity.ModEntities;
import com.epeshi.esalium.item.custom.EsaliRingItem;
import com.epeshi.esalium.item.custom.EsaliumArmorItem;
import com.epeshi.esalium.item.custom.MelodiHeartItem;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.List;

public class ModItems {

    // İsimler ESALIUM olarak güncellendi
    public static final Item ESALIUM_ORE = registerItem("esalium_ore", new Item(new Item.Settings()));
    public static final Item ESALIUM_INGOT = registerItem("esalium_ingot", new Item(new Item.Settings()));
    public static final Item ESALIUM_UPGRADE_SMITHING_TEMPLATE = registerItem("esalium_upgrade_smithing_template", new Item(new Item.Settings()));

    // --- HARİTALAR (HATA ÇÖZÜMÜ BURADA) ---
    // Bu satırlar eksikti, bu yüzden Esalium.java hata veriyordu.

    // -------------------------------------

    public static final Item EMPERRORS_TEARS = registerItem("emperrors_tears", new Item(new Item.Settings().rarity(Rarity.RARE)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            if (Screen.hasShiftDown()) {
                tooltip.add(Text.translatable("item.esalium.emperrors_tears.tooltip.story").formatted(Formatting.DARK_RED, Formatting.ITALIC));
                tooltip.add(Text.translatable("item.esalium.emperrors_tears.tooltip.instruction.1").formatted(Formatting.AQUA));
                tooltip.add(Text.translatable("item.esalium.emperrors_tears.tooltip.instruction.2").formatted(Formatting.AQUA, Formatting.BOLD));
            } else {
                tooltip.add(Text.translatable("tooltip.esalium.shift_required").formatted(Formatting.GRAY));
            }
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    // --- ARAÇLAR ---
    public static final Item ESALIUM_SWORD = registerItem("esalium_sword", new SwordItem(ModToolMaterials.ESALIUM, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.ESALIUM, 3, -2.4f))));
    public static final Item ESALIUM_PICKAXE = registerItem("esalium_pickaxe", new PickaxeItem(ModToolMaterials.ESALIUM, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.ESALIUM, 1, -2.8f))));
    public static final Item ESALIUM_AXE = registerItem("esalium_axe", new AxeItem(ModToolMaterials.ESALIUM, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.ESALIUM, 6, -3.0f))));
    public static final Item ESALIUM_SHOVEL = registerItem("esalium_shovel", new ShovelItem(ModToolMaterials.ESALIUM, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.ESALIUM, 1.5f, -3.0f))));
    public static final Item ESALIUM_HOE = registerItem("esalium_hoe", new HoeItem(ModToolMaterials.ESALIUM, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.ESALIUM, 0, -3.0f))));

    // --- ZIRHLAR ---
    public static final Item ESALIUM_HELMET = registerItem("esalium_helmet",
            new EsaliumArmorItem(EsaliumArmorMaterial.ESALIUM_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(40))));

    public static final Item ESALIUM_CHESTPLATE = registerItem("esalium_chestplate",
            new EsaliumArmorItem(EsaliumArmorMaterial.ESALIUM_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(40))));

    public static final Item ESALIUM_LEGGINGS = registerItem("esalium_leggings",
            new EsaliumArmorItem(EsaliumArmorMaterial.ESALIUM_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(40))));

    public static final Item ESALIUM_BOOTS = registerItem("esalium_boots",
            new EsaliumArmorItem(EsaliumArmorMaterial.ESALIUM_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(40))));

    public static final Item EMPERROR_PU_SPAWN_EGG = registerItem("emperror_pu_spawn_egg", new SpawnEggItem(ModEntities.EMPERROR_PU, 0xFF0000, 0x90EE90, new Item.Settings()));

    public static final Item ESALI_RING = registerItem("esali_ring", new EsaliRingItem(new Item.Settings().maxCount(1)));

    // --- MELODI'NİN KALBİ (ESKİ: SVETA) ---
    public static final Item MELODI_HEART = registerItem("melodi_heart",
            new MelodiHeartItem(new Item.Settings().rarity(Rarity.EPIC).maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Esalium.MOD_ID, name), item);
    }

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (source.isBuiltin() && Identifier.of("minecraft", "entities/zombie").equals(key.getValue())) {
                tableBuilder.pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.10f))
                        .with(ItemEntry.builder(ESALIUM_ORE))
                        .build());
            }
        });
    }

    public static void registerModItems() {
        Esalium.LOGGER.info("Registering Mod Items for " + Esalium.MOD_ID);
        modifyLootTables();
    }
}