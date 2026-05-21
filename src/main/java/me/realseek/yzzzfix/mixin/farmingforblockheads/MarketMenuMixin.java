package me.realseek.yzzzfix.mixin.farmingforblockheads;

import net.blay09.mods.farmingforblockheads.menu.MarketMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 修复市场交易错误比较 NBT 的问题。
 * <p>将支付物品匹配改为仅检查物品 ID，忽略 NBT。</p>
 */
@Mixin(value = MarketMenu.class, remap = false)
public abstract class MarketMenuMixin {

    @Unique
    @Redirect(
            method = "isPaymentItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;m_150942_(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"
            ),
            remap = false
    )
    private boolean yzzzfix$matchesByIdOnly(ItemStack stack1, ItemStack stack2) {
        if (stack1 == null || stack2 == null) return false;
        return stack1.getItem() == stack2.getItem();
    }
}