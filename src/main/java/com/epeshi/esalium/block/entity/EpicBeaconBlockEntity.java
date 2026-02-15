package com.epeshi.esalium.block.entity;

import com.epeshi.esalium.screen.EpicBeaconScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EpicBeaconBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public EpicBeaconBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EPIC_BEACON_BLOCK_ENTITY, pos, state);
    }

    // --- SES EFEKTİ VE ÇALIŞMA MANTIĞI (TICK) ---
    public static void tick(World world, BlockPos pos, BlockState state, EpicBeaconBlockEntity blockEntity) {
        // Eğer slotta Esalium Ingot (veya herhangi bir item) varsa
        boolean hasIngot = !blockEntity.getStack(0).isEmpty();

        if (hasIngot) {
            if (world.getTime() % 80L == 0L) {
                world.playSound(null, pos, SoundEvents.BLOCK_BEACON_AMBIENT, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        }
    }

    @Override
    public DefaultedList<ItemStack> getItems() { return inventory; }

    @Override
    public Text getDisplayName() { return Text.literal("Epic Beacon"); }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1.0f, 1.0f);
        return new EpicBeaconScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public void markDirty() { super.markDirty(); }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
    }
}