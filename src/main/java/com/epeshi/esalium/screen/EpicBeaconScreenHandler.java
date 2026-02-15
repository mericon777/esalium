package com.epeshi.esalium.screen;

import com.epeshi.esalium.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class EpicBeaconScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public EpicBeaconScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1));
    }

    public EpicBeaconScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.EPIC_BEACON_SCREEN_HANDLER, syncId);
        checkSize(inventory, 1);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        // --- ÖZEL ESALIUM SLOTU ---
        this.addSlot(new Slot(inventory, 0, 130, 33) {
            @Override
            public boolean canInsert(ItemStack stack) {
                // İsim güncellendi: ESALIUM_INGOT
                return stack.isOf(ModItems.ESALIUM_INGOT);
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    public boolean consumeIngot() {
        ItemStack stack = this.inventory.getStack(0);
        // İsim güncellendi: ESALIUM_INGOT
        if (!stack.isEmpty() && stack.isOf(ModItems.ESALIUM_INGOT)) {
            stack.decrement(1);
            return true;
        }
        return false;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        // Eşya slotta kalır, geri verilmez.
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < 1) {
                if (!this.insertItem(originalStack, 1, 37, true)) return ItemStack.EMPTY;
            } else if (originalStack.isOf(ModItems.ESALIUM_INGOT)) { // İsim güncellendi
                if (!this.insertItem(originalStack, 0, 1, false)) return ItemStack.EMPTY;
            } else if (invSlot < 28) {
                if (!this.insertItem(originalStack, 28, 37, false)) return ItemStack.EMPTY;
            } else if (invSlot < 37 && !this.insertItem(originalStack, 1, 28, false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) slot.setStack(ItemStack.EMPTY);
            else slot.markDirty();
        }
        return newStack;
    }
}