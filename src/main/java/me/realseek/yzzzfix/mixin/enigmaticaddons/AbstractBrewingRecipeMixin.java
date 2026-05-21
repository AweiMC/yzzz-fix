package me.realseek.yzzzfix.mixin.enigmaticaddons;

import com.aizistral.enigmaticlegacy.brewing.AbstractBrewingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 修复 Enigmatic Addons 中 AbstractBrewingRecipe 初始化时可能出现的空值异常。
 * <p>原构造方法从 HashMap 获取列表，但键存在时值可能意外为 null。
 * 本 Mixin 拦截 HashMap.get 调用，若键存在但值为 null，则创建空 ArrayList 放回，避免空指针。</p>
 */
@Mixin(value = AbstractBrewingRecipe.class, remap = false)
public abstract class AbstractBrewingRecipeMixin {

    @Unique
    @SuppressWarnings("unchecked")
    @Redirect(
            method = "<init>(Lnet/minecraft/resources/ResourceLocation;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/HashMap;get(Ljava/lang/Object;)Ljava/lang/Object;"
            ),
            remap = false
    )
    private Object yzzzfix$fixNullList(HashMap<Object, Object> map, Object key) {
        Object result = map.get(key);
        if (result == null && map.containsKey(key)) {
            result = new ArrayList<>();
            map.put(key, result);
        }
        return result;
    }
}