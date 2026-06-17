package me.realseek.yzzzfix.mixin.shieldexp;

import org.infernalstudios.shieldexp.init.ShieldDataLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShieldDataLoader.class)
public abstract class ShieldDataLoaderMixin {

    @Inject(
            method = "apply*",
            at = @At("HEAD"),
            remap = false
    )
    private void clearToSync(
            CallbackInfo ci
    ) {
        ShieldDataLoader.toSync.clear();
    }
}
