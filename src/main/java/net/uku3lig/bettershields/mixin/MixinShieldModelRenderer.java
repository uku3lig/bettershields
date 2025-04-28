package net.uku3lig.bettershields.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.item.model.special.ShieldModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.item.consume.UseAction;
import net.uku3lig.bettershields.BetterShields;
import net.uku3lig.bettershields.config.ShieldConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

// part of this code was kindly provided by Marlow's friend, massive thanks to them!
@Mixin(ShieldModelRenderer.class)
public class MixinShieldModelRenderer {
    @WrapOperation(method = "render(Lnet/minecraft/component/ComponentMap;Lnet/minecraft/item/ModelTransformationMode;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IIZ)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V"))
    public void changeShieldColor(ModelPart instance, MatrixStack matrices, VertexConsumer vertices, int light, int overlay, Operation<Void> original, @Local(argsOnly = true) ModelTransformationMode mode) {
        ShieldConfig config = BetterShields.getManager().getConfig();
        if (config.isColoredShields()) {
            boolean isClientPlayer = mode.isFirstPerson() || mode != ModelTransformationMode.GUI;
            PlayerEntity player = isClientPlayer ? MinecraftClient.getInstance().player : BetterShields.getCurrentRenderedPlayer();

            if (isDisabled(player)) {
                instance.render(matrices, vertices, light, overlay, config.getDisabledColor());
            } else if (isRising(player)) {
                instance.render(matrices, vertices, light, overlay, config.getRisingColor());
            } else {
                original.call(instance, matrices, vertices, light, overlay);
            }
        } else {
            original.call(instance, matrices, vertices, light, overlay);
        }
    }

    @Unique
    private boolean isRising(PlayerEntity player) {
        if (player == null || !player.isUsingItem() || player.getActiveItem().isEmpty()) return false;

        Item item = player.getActiveItem().getItem();

        return item.getUseAction(player.getActiveItem()) == UseAction.BLOCK
                && item.getMaxUseTime(player.getActiveItem(), player) - player.getItemUseTimeLeft() < 5;
    }

    @Unique
    private boolean isDisabled(PlayerEntity player) {
        return player != null && player.getItemCooldownManager().isCoolingDown(new ItemStack(Items.SHIELD));
    }
}
