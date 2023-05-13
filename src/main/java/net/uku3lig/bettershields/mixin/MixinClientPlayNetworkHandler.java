package net.uku3lig.bettershields.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.uku3lig.bettershields.BetterShieldSounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
    @Shadow private ClientWorld world;

    @Inject(method = "onEntitiesDestroy", at = @At("HEAD"))
    public void hi(EntitiesDestroyS2CPacket packet, CallbackInfo ci) {
        StreamSupport.intStream(packet.getEntityIds().spliterator(), false)
                .mapToObj(this.world::getEntityById)
                .filter(Objects::nonNull)
                .filter(EndCrystalEntity.class::isInstance)
                .map(EndCrystalEntity.class::cast)
                .forEach(this::processExplosion);
    }

    private void processExplosion(Entity attacker) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;

        double maxDistance = 12;

        int x1 = MathHelper.floor(attacker.getX() - maxDistance - 1.0);
		int x2 = MathHelper.floor(attacker.getX() + maxDistance + 1.0);
		int y1 = MathHelper.floor(attacker.getY() - maxDistance - 1.0);
		int y2 = MathHelper.floor(attacker.getY() + maxDistance + 1.0);
		int z1 = MathHelper.floor(attacker.getZ() - maxDistance - 1.0);
		int z2 = MathHelper.floor(attacker.getZ() + maxDistance + 1.0);
        List<Entity> nearEntities = this.world.getOtherEntities(attacker, new Box(x1, y1, z1, x2, y2, z2));

        for (Entity nearEntity : nearEntities) {
            if (nearEntity.isAlive() && nearEntity instanceof LivingEntity livingEntity) {
                double distance = Math.sqrt(nearEntity.squaredDistanceTo(attacker));
                if (distance < maxDistance && BetterShieldSounds.doesShieldBlock(attacker, livingEntity)) {
                    world.playSound(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.ITEM_SHIELD_BLOCK, livingEntity.getSoundCategory(), 1.0F, 0.8F + world.random.nextFloat() * 0.4F, false);
                }
            }
        }

        System.out.println("crystal destroyed! at " + attacker.getBlockPos());
    }
}
