package com.epeshi.esalium;

import com.epeshi.esalium.block.ModBlocks;
import com.epeshi.esalium.block.entity.ModBlockEntities;
import com.epeshi.esalium.entity.ModEntities;
import com.epeshi.esalium.entity.custom.EmperrorPuEntity;
import com.epeshi.esalium.entity.custom.LauraEntity;
import com.epeshi.esalium.entity.custom.MertEntity;
import com.epeshi.esalium.entity.custom.SelimEntity;
import com.epeshi.esalium.event.ModPlayerEvent;
import com.epeshi.esalium.item.ModItemGroups;
import com.epeshi.esalium.item.ModItems;
import com.epeshi.esalium.network.ModNetworking;
import com.epeshi.esalium.screen.ModScreenHandlers;
import com.epeshi.esalium.sound.ModSounds;
import com.epeshi.esalium.villager.ModVillagers;
import com.epeshi.esalium.world.ModPlacedFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapDecorationTypes;
import net.minecraft.item.map.MapState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.structure.Structure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Esalium implements ModInitializer {
    public static final String MOD_ID = "esalium";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // PAYLOADS (İstemci-Sunucu Haberleşme Paketleri)
    public record SpawnPayload() implements CustomPayload {
        public static final Id<SpawnPayload> ID = new Id<>(Identifier.of(MOD_ID, "set_spawn"));
        public static final PacketCodec<PacketByteBuf, SpawnPayload> CODEC = PacketCodec.unit(new SpawnPayload());
        @Override public Id<? extends CustomPayload> getId() { return ID; }
    }

    public record MertActionPayload(boolean isEndGame) implements CustomPayload {
        public static final Id<MertActionPayload> ID = new Id<>(Identifier.of(MOD_ID, "mert_action"));
        public static final PacketCodec<PacketByteBuf, MertActionPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, MertActionPayload::isEndGame, MertActionPayload::new);
        @Override public Id<? extends CustomPayload> getId() { return ID; }
    }

    public record WaypointPayload(BlockPos pos, String translationKey, String symbol) implements CustomPayload {
        public static final Id<WaypointPayload> ID = new Id<>(Identifier.of(MOD_ID, "add_waypoint"));
        public static final PacketCodec<PacketByteBuf, WaypointPayload> CODEC = PacketCodec.tuple(
                BlockPos.PACKET_CODEC, WaypointPayload::pos,
                PacketCodecs.STRING, WaypointPayload::translationKey,
                PacketCodecs.STRING, WaypointPayload::symbol,
                WaypointPayload::new
        );
        @Override public Id<? extends CustomPayload> getId() { return ID; }
    }

    // --- YENİ EKLENEN SELİM PAYLOAD ---
    public record SelimRewardPayload() implements CustomPayload {
        public static final Id<SelimRewardPayload> ID = new Id<>(Identifier.of(MOD_ID, "selim_reward"));
        public static final PacketCodec<PacketByteBuf, SelimRewardPayload> CODEC = PacketCodec.unit(new SelimRewardPayload());
        @Override public Id<? extends CustomPayload> getId() { return ID; }
    }
    // ----------------------------------

    @Override
    public void onInitialize() {
        LOGGER.info("Esalium Modu Hazırlanıyor: " + MOD_ID);
        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
        ModItemGroups.registerItemGroups();
        ModBlockEntities.registerBlockEntities();
        ModScreenHandlers.registerScreenHandlers();
        ModSounds.registerModSounds();
        ModVillagers.registerVillagers();
        ModPlayerEvent.register();
        ModNetworking.registerPackets();
        ModItems.modifyLootTables();

        // Payload (Paket) Kayıtları
        PayloadTypeRegistry.playC2S().register(SpawnPayload.ID, SpawnPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(MertActionPayload.ID, MertActionPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(WaypointPayload.ID, WaypointPayload.CODEC);
        // Selim Payload Kaydı
        PayloadTypeRegistry.playC2S().register(SelimRewardPayload.ID, SelimRewardPayload.CODEC);

        // Spawn Noktası Ayarlama Mantığı
        ServerPlayNetworking.registerGlobalReceiver(SpawnPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                var player = context.player();
                player.setSpawnPoint(player.getWorld().getRegistryKey(), player.getBlockPos(), player.getYaw(), true, false);
                player.sendMessage(Text.translatable("message.esalium.spawn_set").formatted(Formatting.GREEN), true);
            });
        });

        // Mert Etkileşim Mantığı
        ServerPlayNetworking.registerGlobalReceiver(MertActionPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                ServerPlayerEntity player = context.player();
                if (payload.isEndGame()) {
                    ItemStack reward = new ItemStack(ModItems.ESALIUM_INGOT, 9);
                    player.getInventory().insertStack(reward);
                    player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                    player.sendMessage(Text.translatable("message.esalium.reward_received").formatted(Formatting.GOLD, Formatting.BOLD), true);
                    removeEntity(player, MertEntity.class);
                } else {
                    handleStructureMap(player, "efe_house", "waypoint.esalium.efe_house", "item.esalium.efe_map", "E", true, null);
                    removeEntity(player, MertEntity.class);
                }
            });
        });

        // --- SELİM TOTEM VERME MANTIĞI ---
        ServerPlayNetworking.registerGlobalReceiver(SelimRewardPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                var player = context.player();
                // Totem Ver
                player.getInventory().insertStack(new ItemStack(Items.TOTEM_OF_UNDYING));
                // Ses Çal
                player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                // Mesaj Gönder
                player.sendMessage(Text.literal("§bSelim: §fBu totem seni koruyacak, dikkatli ol!"), true);
            });
        });
        // ----------------------------------

        // Envanter Takibi (Harita Waypoint için)
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                for (int i = 0; i < player.getInventory().size(); i++) {
                    ItemStack stack = player.getInventory().getStack(i);

                    // Sadece FilledMapItem (Vanilla Harita) kontrol et
                    if (stack.getItem() instanceof FilledMapItem) {
                        Text customName = stack.get(DataComponentTypes.CUSTOM_NAME);

                        if (customName != null && customName.getContent() instanceof TranslatableTextContent text) {

                            // Kale Haritası
                            if (text.getKey().equals("item.esalium.castle_map")) {
                                if (!hasPlayerReceivedWaypoint(stack, player)) {
                                    handleStructureMap(player, "emperror_pu_castle", "waypoint.esalium.castle", "item.esalium.castle_map", "C", false, stack);
                                    markPlayerAsReceived(stack, player);
                                }
                            }

                            // Efe Haritası
                            if (text.getKey().equals("item.esalium.efe_map")) {
                                if (!hasPlayerReceivedWaypoint(stack, player)) {
                                    handleStructureMap(player, "efe_house", "waypoint.esalium.efe_house", "item.esalium.efe_map", "E", false, stack);
                                    markPlayerAsReceived(stack, player);
                                }
                            }
                        }
                    }
                }
            }
        });

        // Entity ve Attribute Kayıtları
        ModEntities.registerModEntities();
        FabricDefaultAttributeRegistry.register(ModEntities.EMPERROR_PU, EmperrorPuEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.LAURA, LauraEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MERT, MertEntity.createAttributes());
        // Selim'i Kaydet
        FabricDefaultAttributeRegistry.register(ModEntities.SELIM, SelimEntity.createAttributes());

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                ModPlacedFeatures.ESALIUM_ORE_PLACED_KEY
        );
    }

    // --- HELPER METODLAR ---

    private boolean hasPlayerReceivedWaypoint(ItemStack stack, ServerPlayerEntity player) {
        NbtComponent nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);
        if (nbtComponent == null) return false;
        NbtCompound nbt = nbtComponent.copyNbt();
        if (!nbt.contains("waypoint_receivers")) return false;
        NbtList list = nbt.getList("waypoint_receivers", NbtElement.STRING_TYPE);
        String playerUuid = player.getUuidAsString();
        for (NbtElement element : list) {
            if (element.asString().equals(playerUuid)) return true;
        }
        return false;
    }

    private void markPlayerAsReceived(ItemStack stack, ServerPlayerEntity player) {
        NbtCompound nbt = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt();
        NbtList list = nbt.contains("waypoint_receivers") ? nbt.getList("waypoint_receivers", NbtElement.STRING_TYPE) : new NbtList();
        list.add(NbtString.of(player.getUuidAsString()));
        nbt.put("waypoint_receivers", list);
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
    }

    public static void handleStructureMap(ServerPlayerEntity player, String structureId, String waypointKey, String mapNameKey, String symbol, boolean giveMapItem, ItemStack existingStack) {
        ServerWorld world = (ServerWorld) player.getWorld();
        BlockPos targetPos = null;

        if (existingStack != null) {
            NbtComponent nbtComponent = existingStack.get(DataComponentTypes.CUSTOM_DATA);
            if (nbtComponent != null) {
                NbtCompound nbt = nbtComponent.copyNbt();
                if (nbt.contains("target_x") && nbt.contains("target_y") && nbt.contains("target_z")) {
                    targetPos = new BlockPos(nbt.getInt("target_x"), nbt.getInt("target_y"), nbt.getInt("target_z"));
                }
            }
        }

        if (targetPos == null) {
            TagKey<Structure> structureTag = TagKey.of(RegistryKeys.STRUCTURE, Identifier.of(MOD_ID, structureId));
            int radius = structureId.equals("emperror_pu_castle") ? 1000 : 150;

            BlockPos rawPos = world.locateStructure(structureTag, player.getBlockPos(), radius, true);
            if (rawPos != null) {
                int surfaceY = world.getTopY(Heightmap.Type.MOTION_BLOCKING, rawPos.getX(), rawPos.getZ());
                if (surfaceY < world.getSeaLevel()) surfaceY = 80;
                targetPos = new BlockPos(rawPos.getX(), surfaceY + 1, rawPos.getZ());
            }
        }

        if (targetPos != null) {
            if (giveMapItem) {
                ItemStack mapStack = FilledMapItem.createMap(world, targetPos.getX(), targetPos.getZ(), (byte) 2, true, true);
                FilledMapItem.fillExplorationMap(world, mapStack);
                MapState.addDecorationsNbt(mapStack, targetPos, "+", MapDecorationTypes.TARGET_X);

                mapStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(mapNameKey).formatted(Formatting.GOLD));

                NbtCompound nbt = mapStack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt();
                nbt.putInt("target_x", targetPos.getX());
                nbt.putInt("target_y", targetPos.getY());
                nbt.putInt("target_z", targetPos.getZ());

                NbtList list = new NbtList();
                list.add(NbtString.of(player.getUuidAsString()));
                nbt.put("waypoint_receivers", list);

                mapStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));

                player.getInventory().insertStack(mapStack);
            }
            else if (existingStack != null) {
                NbtCompound nbt = existingStack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt();
                if (!nbt.contains("target_x")) {
                    nbt.putInt("target_x", targetPos.getX());
                    nbt.putInt("target_y", targetPos.getY());
                    nbt.putInt("target_z", targetPos.getZ());
                    existingStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
                }
            }

            ServerPlayNetworking.send(player, new WaypointPayload(targetPos, waypointKey, symbol));
            player.sendMessage(Text.translatable("message.esalium.map_received").formatted(Formatting.GREEN), true);
            player.playSound(SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1.0f, 1.0f);
        } else {
            player.sendMessage(Text.translatable("message.esalium.structure_not_found").formatted(Formatting.RED), true);
        }
    }

    private void removeEntity(ServerPlayerEntity player, Class<? extends Entity> entityClass) {
        player.getWorld().getEntitiesByClass(entityClass, player.getBoundingBox().expand(10), entity -> true).forEach(entity -> {
            if (entity.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.POOF, entity.getX(), entity.getY() + 1.0, entity.getZ(), 15, 0.4, 0.8, 0.4, 0.02);
                serverWorld.spawnParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, entity.getX(), entity.getY(), entity.getZ(), 8, 0.3, 0.5, 0.3, 0.05);
            }
            entity.discard();
        });
        player.sendMessage(Text.translatable("message.esalium.mert_leave").formatted(Formatting.YELLOW), true);
    }
}