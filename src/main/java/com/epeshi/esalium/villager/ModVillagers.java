package com.epeshi.esalium.villager;

import com.epeshi.esalium.Esalium;
import com.epeshi.esalium.block.ModBlocks;
import com.epeshi.esalium.item.ModItems;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.component.type.WrittenBookContentComponent;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapDecorationTypes;
import net.minecraft.item.map.MapState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.RawFilteredPair;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.List;
import java.util.Optional;

public class ModVillagers {

    public static final RegistryKey<PointOfInterestType> EFE_POI_KEY = RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Identifier.of(Esalium.MOD_ID, "efe_poi"));
    public static final PointOfInterestType EFE_POI = PointOfInterestHelper.register(Identifier.of(Esalium.MOD_ID, "efe_poi"), 1, 1, ModBlocks.EFES_TABLE);
    public static final VillagerProfession EFE = Registry.register(Registries.VILLAGER_PROFESSION, Identifier.of(Esalium.MOD_ID, "efe"),
            new VillagerProfession("efe", entry -> entry.matchesKey(EFE_POI_KEY), entry -> entry.matchesKey(EFE_POI_KEY),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_TOOLSMITH));

    // --- MELODI (ESKİ ADI: SVETA) ---
    // İsimler ve değişkenler Melodi'ye uyarlandı
    public static final RegistryKey<PointOfInterestType> MELODI_POI_KEY = RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Identifier.of(Esalium.MOD_ID, "melodi_poi"));
    // Melodi'nin meslek bloğu Esalium Ore Block oldu
    public static final PointOfInterestType MELODI_POI = PointOfInterestHelper.register(Identifier.of(Esalium.MOD_ID, "melodi_poi"), 1, 1, ModBlocks.ESALIUM_ORE_BLOCK);
    public static final VillagerProfession MELODI = Registry.register(Registries.VILLAGER_PROFESSION, Identifier.of(Esalium.MOD_ID, "melodi"),
            new VillagerProfession("melodi", entry -> entry.matchesKey(MELODI_POI_KEY), entry -> entry.matchesKey(MELODI_POI_KEY),
                    ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_CLERIC));

    public static final TagKey<Structure> CASTLE_STRUCTURE_TAG = TagKey.of(RegistryKeys.STRUCTURE, Identifier.of(Esalium.MOD_ID, "emperror_pu_castle"));

    public static void registerTrades() {
        // --- EFE TRADES ---

        // LEVEL 1: Story Book
        TradeOfferHelper.registerVillagerOffers(EFE, 1, factories -> {
            factories.add((entity, random) -> {

                // İsim Verme Mantığı
                if (!entity.hasCustomName()) {
                    // Dil dosyası key'i güncellendi: entity.esalium.efe_villager
                    entity.setCustomName(Text.translatable("entity.esalium.efe_villager"));
                    entity.setCustomNameVisible(true);
                }

                ItemStack bookStack = new ItemStack(Items.WRITTEN_BOOK);
                List<RawFilteredPair<Text>> pages = List.of(
                        RawFilteredPair.of(Text.translatable("book.esalium.efe_story.p1")),
                        RawFilteredPair.of(Text.translatable("book.esalium.efe_story.p2")),
                        RawFilteredPair.of(Text.translatable("book.esalium.efe_story.p3"))
                );
                bookStack.set(DataComponentTypes.WRITTEN_BOOK_CONTENT, new WrittenBookContentComponent(RawFilteredPair.of("Efe's Story"), "Efe", 0, pages, true));
                bookStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.esalium.efe_story").formatted(Formatting.GOLD));

                return new TradeOffer(new TradedItem(Items.STICK, 1), bookStack, 1, 20, 0.05f);
            });
        });

        // LEVEL 2: Castle Map
        // ModVillagers.java içindeki LEVEL 2 Trade Kısmını Bul ve Şöyle Değiştir:

// LEVEL 2: Castle Map
        TradeOfferHelper.registerVillagerOffers(EFE, 2, factories -> {
            factories.add((entity, random) -> {
                ItemStack mapStack;
                if (entity.getWorld() instanceof ServerWorld serverWorld) {
                    // Yapıyı bul
                    BlockPos structurePos = serverWorld.locateStructure(CASTLE_STRUCTURE_TAG, entity.getBlockPos(), 1000, true);

                    if (structurePos != null) {
                        // Vanilla Map oluştur
                        mapStack = FilledMapItem.createMap(serverWorld, structurePos.getX(), structurePos.getZ(), (byte) 2, true, true);
                        FilledMapItem.fillExplorationMap(serverWorld, mapStack);
                        MapState.addDecorationsNbt(mapStack, structurePos, "+", MapDecorationTypes.TARGET_X);

                        // İsim ver (Esalium.java bunu kontrol edecek)
                        mapStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.esalium.castle_map").formatted(Formatting.GOLD));

                        // Koordinatları içine yaz (Xaero için)
                        NbtCompound nbt = new NbtCompound();
                        nbt.putInt("target_x", structurePos.getX());
                        nbt.putInt("target_y", 70); // Tahmini yükseklik
                        nbt.putInt("target_z", structurePos.getZ());
                        mapStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
                    } else {
                        mapStack = new ItemStack(Items.MAP);
                        mapStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("Castle Not Found").formatted(Formatting.RED));
                    }
                } else {
                    mapStack = new ItemStack(Items.MAP);
                }

                return new TradeOffer(new TradedItem(Items.STICK, 1), mapStack, 1, 100, 0.05f);
            });
        });

        // LEVEL 3: Upgrade Template
        TradeOfferHelper.registerVillagerOffers(EFE, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 5),
                    new ItemStack(ModItems.ESALIUM_UPGRADE_SMITHING_TEMPLATE, 1), // İsim güncellendi
                    9999,
                    100,
                    0.05f
            ));
        });

        // LEVEL 4: Ore Conversion
        TradeOfferHelper.registerVillagerOffers(EFE, 4, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.ESALIUM_ORE, 4), // İsim güncellendi
                    new ItemStack(ModBlocks.RAW_ESALIUM_BLOCK, 1), // İsim güncellendi
                    16,
                    30,
                    0.05f
            ));
        });

        // --- MELODI TRADES (ESKİ: SVETA) ---
        TradeOfferHelper.registerVillagerOffers(MELODI, 1, factories -> {

            // TRADE 1: Ore -> Raw Block
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.ESALIUM_ORE, 4), // İsim güncellendi
                    new ItemStack(ModBlocks.RAW_ESALIUM_BLOCK, 1), // İsim güncellendi
                    9999,
                    30,
                    0.05f
            ));

            // TRADE 2: Block + Tear -> Epic Beacon
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModBlocks.ESALIUM_BLOCK.asItem(), 3), // İsim güncellendi
                    Optional.of(new TradedItem(ModItems.EMPERRORS_TEARS, 1)),
                    new ItemStack(ModBlocks.EPIC_BEACON, 1),
                    12,
                    50,
                    0.05f
            ));
        });
    }

    public static void registerVillagers() {
        Esalium.LOGGER.info("Registering Villagers for " + Esalium.MOD_ID);
        registerTrades();
    }
}