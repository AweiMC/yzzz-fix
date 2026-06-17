package me.realseek.yzzzfix.mixin.shieldexp;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.infernalstudios.shieldexp.events.ShieldExpansionEvents;

@Pseudo
@Mixin(ShieldExpansionEvents.class)
public abstract class ShieldExpansionEventsMixin {
    @ModifyReturnValue(
            method = "getShieldValue",
            at = @At("RETURN"),
            remap = false
    )
    private static Double fixNullReturn(
            Double original
    ) {
        return original != null ? original : 0D;
    }
}
