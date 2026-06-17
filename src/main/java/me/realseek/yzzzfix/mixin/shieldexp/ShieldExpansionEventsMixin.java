package me.aweimc.forge.mixins.fix.shieldexp;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.aweimc.forge.config.CrashModFixConfig;
import me.aweimc.forge.util.FixMode;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import org.infernalstudios.shieldexp.events.ShieldExpansionEvents;
import org.infernalstudios.shieldexp.init.ShieldDataLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import java.util.Map;
import java.util.Objects;

@Pseudo
@Mixin(ShieldExpansionEvents.class)
public abstract class ShieldExpansionEventsMixin {
    @Inject(
            method = "getShieldValue",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void compat(
            Item item,
            String value,
            CallbackInfoReturnable<Double> cir
    ) {

        if (CrashModFixConfig.get().FIX_MODE != FixMode.COMPATIBILITY)
            return;

        try {
            String key =
                    Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).toString();

            Map<String, Double> stats =
                    ShieldDataLoader.SHIELD_STATS.get(key);
            if (stats == null) {
                stats = ShieldDataLoader.SHIELD_STATS.get(
                                "shieldexp:default"
                        );
            }

            if (stats == null) {
                cir.setReturnValue(0D);
                return;
            }

            cir.setReturnValue(
                    stats.getOrDefault(value, 0D)
            );
        }
        catch (Throwable ignored) {
            cir.setReturnValue(0D);
        }
    }
    @ModifyReturnValue(
            method = "getShieldValue",
            at = @At("RETURN"),
            remap = false
    )
    private static Double fixNullReturn(
            Double original,
            Item item,
            String value
    ) {
        if(CrashModFixConfig.get().FIX_MODE.equals(FixMode.FAST)) return original != null ? original : 0D;
        return original;
    }
}