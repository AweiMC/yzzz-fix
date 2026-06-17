package me.aweimc.forge.mixins.fix.goety_cataclysm;

import com.Polarice3.goety_cataclysm.common.entities.projectiles.DeathLaserBeam;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.aweimc.forge.config.CrashModFixConfig;
import me.aweimc.forge.util.FixMode;
import net.minecraft.entity.LivingEntity;
//net.minecraft.world.entity.LivingEntity
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(DeathLaserBeam.class)
public abstract class DeathLaserBeamMixin  {
    @Shadow
    public LivingEntity caster;

    @Inject(method = "updateWithCaster", at = @At("HEAD"), cancellable = true, remap = false)
    private void onUpdateWithCaster(CallbackInfo ci) {
        if(CrashModFixConfig.get().FIX_MODE.equals(FixMode.FAST)) {
            if (caster == null) ci.cancel();  // 跳过方法体，避免空指针
        }
    }

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/Polarice3/goety_cataclysm/common/entities/projectiles/DeathLaserBeam;updateWithCaster()V"
            )
    )
    private void safelyCallUpdateWithCaster(DeathLaserBeam instance, Operation<Void> original) {
        if (CrashModFixConfig.get().FIX_MODE.equals(FixMode.COMPATIBILITY)) {
            // 只有施法者存在时才执行激光更新，否则跳过（优雅地什么都不做）
            if (caster != null) {
                original.call(instance);   // 调用原始的 updateWithCaster()
            }
            // else: 静默跳过，光束不会更新位置，但 entity 会自然过期销毁
        } else {
            // 其他模式（FAST 或关闭修复）保持原样，总是调用
            original.call(instance);
        }
    }
}
