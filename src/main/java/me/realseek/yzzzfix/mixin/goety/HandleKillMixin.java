package me.realseek.yzzzfix.mixin.goety;

import com.Polarice3.Goety.utils.SEHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * 使 SoulEater 附魔兼容法术投射物击杀。
 * <p>将 handleKill 中的 killer 替换为投射物的施法玩家。</p>
 */
@Mixin(value = SEHelper.class, remap = false)
public abstract class HandleKillMixin {

    @Unique
    @ModifyArg(
            method = "handleKill",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/Polarice3/Goety/utils/SEHelper;rawHandleKill(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;ILnet/minecraft/world/damagesource/DamageSource;)V"
            ),
            index = 0,
            remap = false
    )
    private static LivingEntity yzzzfix$modifyKiller(LivingEntity killer, LivingEntity victim, int soulEater, DamageSource source) {
        if (killer instanceof Player) return killer;
        Player player = yzzzfix$resolvePlayer(killer);
        if (player == null) {
            Entity direct = source.getDirectEntity();
            if (direct != null && direct != killer) {
                player = yzzzfix$resolvePlayer(direct);
            }
        }
        return player != null ? player : killer;
    }

    @Unique
    private static Player yzzzfix$resolvePlayer(Entity entity) {
        if (entity instanceof Player player) return player;
        if (entity instanceof Projectile projectile) {
            Entity owner = projectile.getOwner();
            if (owner instanceof Player player) return player;
            if (owner != null) return yzzzfix$resolvePlayer(owner);
        }
        return null;
    }
}