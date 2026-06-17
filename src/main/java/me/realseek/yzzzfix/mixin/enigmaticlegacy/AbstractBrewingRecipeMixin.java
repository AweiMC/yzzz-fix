package me.realseek.yzzzfix.mixin.enigmaticlegacy;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mixin(targets = "com.aizistral.enigmaticlegacy.brewing.AbstractBrewingRecipe")
public class AbstractBrewingRecipeMixin {

    @WrapOperation(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/HashMap;get(Ljava/lang/Object;)Ljava/lang/Object;",
                    remap = false
            ),
            remap = false
    )
    private Object safeGet(
            HashMap<Object, List<Object>> map,
            Object key,
            Operation<Object> original
    ) {
        if (original.call(map, key) == null && map.containsKey(key)) {
//            CrashModFixModForge.LOGGER.error(
//                    "Corrupted recipeMap entry: {}", key,
//                    new IllegalStateException("recipeMap contains null value")
//            );

            //LOGGGGGGGGGGGGGGGGGGGGGGGG DEBUGGGG
            List<Object> list = new ArrayList<>();
            map.put(key, list);
            return list;
        }
        return original.call(map, key);
    }
}
