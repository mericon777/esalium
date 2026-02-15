package com.epeshi.esalium.item.custom;

import com.epeshi.esalium.item.EsaliumArmorMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public class EsaliumArmorItem extends ArmorItem {
    public EsaliumArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient()) {
            if(entity instanceof PlayerEntity player) {
                if(hasFullSuitOfArmorOn(player)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 0, false, false, true));
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private boolean hasFullSuitOfArmorOn(PlayerEntity player) {
        ItemStack boots = player.getInventory().getArmorStack(0);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack chestplate = player.getInventory().getArmorStack(2);
        ItemStack helmet = player.getInventory().getArmorStack(3);

        return !helmet.isEmpty() && !chestplate.isEmpty() &&
                !leggings.isEmpty() && !boots.isEmpty() &&
                isEsaliumArmor(helmet) && isEsaliumArmor(chestplate) &&
                isEsaliumArmor(leggings) && isEsaliumArmor(boots);
    }

    private boolean isEsaliumArmor(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getMaterial() == EsaliumArmorMaterial.ESALIUM_ARMOR_MATERIAL;
    }
}