package net.uku3lig.bettershields.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.uku3lig.bettershields.BetterShields;
import net.uku3lig.bettershields.config.ShieldConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiPlayerGameMode.class)
public class MixinMultiplayerGameMode {
    @Inject(method = "attack", at = @At("HEAD"))
    public void playShieldSound(Player player, Entity target, CallbackInfo ci) {
        ClientLevel world = Minecraft.getInstance().level;
        ShieldConfig config = BetterShields.getManager().getConfig();

        if (config.isSoundsEnabled() && target instanceof LivingEntity entity && BetterShields.doesShieldBlock(player.position(), entity) && world != null) {
            if (player.getMainHandItem().getItem() instanceof AxeItem) {
                world.playSound(entity, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.SHIELD_BREAK.value(), entity.getSoundSource(), 1.0F, 0.8F + world.getRandom().nextFloat() * 0.4F);
                if (target instanceof Player otherPlayer) {
                    otherPlayer.getCooldowns().addCooldown(new ItemStack(Items.SHIELD), 100);
                }
            } else {
                world.playSound(entity, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.SHIELD_BLOCK.value(), entity.getSoundSource(), 1.0F, 0.8F + world.getRandom().nextFloat() * 0.4F);
            }
        }
    }
}
