package me.realseek.yzzzfix.mixin.goety;

import com.Polarice3.goety_cataclysm.common.entities.projectiles.DeathLaserBeam;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity
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

   
    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/Polarice3/goety_cataclysm/common/entities/projectiles/DeathLaserBeam;updateWithCaster()V"
            )
    )
    private void safelyCallUpdateWithCaster(DeathLaserBeam instance, Operation<Void> original) {
        if (caster != null) original.call(instance);   // 调用原始的 updateWithCaster()
    }
}
