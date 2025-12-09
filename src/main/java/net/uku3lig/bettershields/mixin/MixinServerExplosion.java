package net.uku3lig.bettershields.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ServerExplosion;
import net.minecraft.world.phys.AABB;
import net.uku3lig.bettershields.BetterShields;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerExplosion.class)
public abstract class MixinServerExplosion implements Explosion {
    @Inject(method = "hurtEntities", at = @At("HEAD"))
    public void playShieldSoundsFromExplosion(CallbackInfo ci) {
        if (!BetterShields.getManager().getConfig().isSoundsEnabled()) return;

        LocalPlayer player = Minecraft.getInstance().player;
        if (!this.level().isClientSide() || player == null) return;

        double maxDistance = this.radius() * 2;
        double diameter = (maxDistance + 1.0) * 2;
        AABB box = AABB.ofSize(this.center(), diameter, diameter, diameter);

        List<LivingEntity> nearEntities = this.level().getEntitiesOfClass(LivingEntity.class, box, obj -> true);

        for (LivingEntity nearEntity : nearEntities) {
            if (nearEntity.isAlive()) {
                double distance = Math.sqrt(nearEntity.distanceToSqr(this.center()));
                if (distance < maxDistance && BetterShields.doesShieldBlock(this.center(), nearEntity)) {
                    this.level().playSound(nearEntity, nearEntity.getX(), nearEntity.getY(), nearEntity.getZ(), SoundEvents.SHIELD_BLOCK.value(), nearEntity.getSoundSource(), 1.0F, 0.8F + this.level().random.nextFloat() * 0.4F);
                }
            }
        }
    }
}
