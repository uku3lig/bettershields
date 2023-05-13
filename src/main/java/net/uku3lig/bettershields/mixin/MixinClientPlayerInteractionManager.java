package net.uku3lig.bettershields.mixin;

import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.uku3lig.bettershields.BetterShieldSounds;
import net.uku3lig.bettershields.config.ShieldConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
@Slf4j
public class MixinClientPlayerInteractionManager {
    @Inject(method = "attackEntity", at = @At("HEAD"))
    public void playShieldSound(PlayerEntity player, Entity target, CallbackInfo ci) {
        ClientWorld world = MinecraftClient.getInstance().world;
        ShieldConfig config = BetterShieldSounds.getManager().getConfig();

        if (config.isEnabled() && target instanceof LivingEntity entity && doesShieldBlock(player, entity) && world != null) {
            if (player.getMainHandStack().getItem() instanceof AxeItem) {
                player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + world.random.nextFloat() * 0.4F);
            } else {
                player.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            }
        }
    }

    private static boolean doesShieldBlock(PlayerEntity attacker, LivingEntity target) {
        if (!target.isBlocking()) return false;

        Vec3d rotation = target.getRotationVec(1);
        Vec3d relativePosition = attacker.getPos().relativize(target.getPos()).normalize();
        Vec3d flat = new Vec3d(relativePosition.x, 0.0, relativePosition.z);
        return flat.dotProduct(rotation) < 0.0;
    }
}
