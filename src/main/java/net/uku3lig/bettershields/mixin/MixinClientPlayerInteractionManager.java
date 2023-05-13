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
import net.uku3lig.bettershields.BetterShields;
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
        ShieldConfig config = BetterShields.getManager().getConfig();

        if (config.isSoundsEnabled() && target instanceof LivingEntity entity && BetterShields.doesShieldBlock(player.getPos(), entity) && world != null) {
            if (player.getMainHandStack().getItem() instanceof AxeItem) {
                world.playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_SHIELD_BREAK, entity.getSoundCategory(), 1.0F, 0.8F + world.random.nextFloat() * 0.4F, false);
            } else {
                world.playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_SHIELD_BLOCK, entity.getSoundCategory(), 1.0F, 0.8F + world.random.nextFloat() * 0.4F, false);
            }
        }
    }
}
