package com.epeshi.esalium.mixin;

import com.epeshi.esalium.item.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    // Namespace 'esalium' olarak güncellendi
    private static final TagKey<net.minecraft.item.Item> ESALIUM_GEAR =
            TagKey.of(RegistryKeys.ITEM, Identifier.of("esalium", "esalium_gear"));

    private boolean hasEsaliRing(PlayerEntity player) {
        return dev.emi.trinkets.api.TrinketsApi.getTrinketComponent(player)
                .map(component -> (Boolean) component.isEquipped(ModItems.ESALI_RING))
                .orElse(false);
    }

    // GİYME KISITLAMASI (Esalium zırhlarını yüzüksüz giyemez)
    @Inject(method = "equipStack", at = @At("HEAD"), cancellable = true)
    private void preventEquip(EquipmentSlot slot, ItemStack stack, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (stack.isIn(ESALIUM_GEAR) && !hasEsaliRing(player)) {
            if (!player.getWorld().isClient) {
                player.sendMessage(Text.literal("§cBu zırhı giymek için Esali's Ring takmalısın!"), true);
            }
            ci.cancel();
        }
    }

    // SALDIRI KISITLAMASI (Esalium kılıçlarını yüzüksüz kullanamaz)
    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void checkAttack(net.minecraft.entity.Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack stack = player.getMainHandStack();
        if (stack.isIn(ESALIUM_GEAR) && !hasEsaliRing(player)) {
            if (!player.getWorld().isClient) {
                player.sendMessage(Text.literal("§cEsali's Ring olmadan bu gücü kullanamazsın!"), true);
            }
            ci.cancel();
        }
    }
}