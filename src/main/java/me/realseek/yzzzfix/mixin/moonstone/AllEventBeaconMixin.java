package me.realseek.yzzzfix.mixin.moonstone;

import com.moonstone.moonstonemod.event.AllEvent;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 修复 AllEvent.beacon 中因调用 collectEquipmentChanges 导致的 ConcurrentModificationException。
 * <p>原方法在生成信标效果云后调用了 {@code player.m_21219_()}，该调用在复杂模组环境下
 * 可能触发并发修改异常。本 Mixin 直接取消该调用，保留云实体生成逻辑不变。</p>
 */
@Mixin(value = AllEvent.class, remap = false)
public abstract class AllEventBeaconMixin {


    @Redirect(
            method = "beacon",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;m_21219_()V"
            ),
            remap = false
    )
    private void yzzzfix$cancelCollectEquipmentChanges(LivingEntity instance) {
    }
}