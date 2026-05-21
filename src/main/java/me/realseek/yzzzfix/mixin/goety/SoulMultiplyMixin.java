package me.realseek.yzzzfix.mixin.goety;

import com.Polarice3.Goety.utils.ModDamageSource;
import com.Polarice3.Goety.utils.SEHelper;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 移除 SoulEater 仅对物理攻击生效的限制。
 * <p>强制 SoulMultiply 始终返回大于 1 的倍率。</p>
 */
@Mixin(value = SEHelper.class, remap = false)
public abstract class SoulMultiplyMixin {

    @Unique
    @Redirect(
            method = "SoulMultiply",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/Polarice3/Goety/utils/ModDamageSource;physicalAttacks(Lnet/minecraft/world/damagesource/DamageSource;)Z"
            ),
            remap = false
    )
    private static boolean yzzzfix$alwaysAllowSoulEater(DamageSource source) {
        return true;
    }
}