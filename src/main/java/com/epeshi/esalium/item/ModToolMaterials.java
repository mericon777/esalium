package com.epeshi.esalium.item;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

public enum ModToolMaterials implements ToolMaterial {
    // SVETARIUM -> ESALIUM
    ESALIUM(5, 2000, 15.0F, 4.0F, 24, () -> Ingredient.ofItems(ModItems.ESALIUM_INGOT));

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = Suppliers.memoize(repairIngredient);
    }

    @Override
    public int getDurability() { return this.itemDurability; }

    @Override
    public float getMiningSpeedMultiplier() { return this.miningSpeed; }

    @Override
    public float getAttackDamage() { return this.attackDamage; }

    @Override
    public TagKey<Block> getInverseTag() {
        return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
    }

    @Override
    public int getEnchantability() { return this.enchantability; }

    @Override
    public Ingredient getRepairIngredient() { return this.repairIngredient.get(); }
}