package net.uku3lig.bettershields.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionImpl;
import net.uku3lig.bettershields.BetterShields;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(ExplosionImpl.class)
public abstract class MixinExplosionImpl implements Explosion {
    @Inject(method = "damageEntities", at = @At("HEAD"))
    public void playShieldSoundsFromExplosion(CallbackInfo ci) {
        if (!BetterShields.getManager().getConfig().isSoundsEnabled()) return;

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (!this.getWorld().isClient || player == null) return;

        double maxDistance = this.getPower() * 2;
        double diameter = (maxDistance + 1.0) * 2;
        Box box = Box.of(this.getPosition(), diameter, diameter, diameter);

        List<LivingEntity> nearEntities = this.getWorld().getEntitiesByClass(LivingEntity.class, box, Objects::nonNull);

        for (LivingEntity nearEntity : nearEntities) {
            if (nearEntity.isAlive()) {
                double distance = Math.sqrt(nearEntity.squaredDistanceTo(this.getPosition()));
                if (distance < maxDistance && BetterShields.doesShieldBlock(this.getPosition(), nearEntity)) {
                    this.getWorld().playSound(nearEntity.getX(), nearEntity.getY(), nearEntity.getZ(), SoundEvents.ITEM_SHIELD_BLOCK, nearEntity.getSoundCategory(), 1.0F, 0.8F + this.getWorld().random.nextFloat() * 0.4F, false);
                }
            }
        }
    }
}
