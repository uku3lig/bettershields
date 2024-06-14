package net.uku3lig.bettershields.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.UseAction;
import net.uku3lig.bettershields.BetterShields;
import net.uku3lig.bettershields.config.ShieldConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// part of this code was kindly provided by Marlow's friend, massive thanks to them!
@Mixin(BuiltinModelItemRenderer.class)
public class MixinBuiltinModelItemRenderer {
    @Unique
    private ModelTransformationMode mode;

    @Inject(method = "render", at = @At("HEAD"))
    public void getMode(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        this.mode = mode;
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V"))
    public void changeShieldColor(ModelPart instance, MatrixStack matrices, VertexConsumer vertices, int light, int overlay, Operation<Void> original) {
        ShieldConfig config = BetterShields.getManager().getConfig();
        if (this.mode.isFirstPerson() && config.isColoredShields()) {
            if (isDisabled()) {
                instance.render(matrices, vertices, light, overlay, config.getDisabledColor());
            } else if (isRising()) {
                instance.render(matrices, vertices, light, overlay, config.getRisingColor());
            } else {
                original.call(instance, matrices, vertices, light, overlay);
            }
        } else {
            original.call(instance, matrices, vertices, light, overlay);
        }
    }

    @Unique
    private boolean isRising() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || !player.isUsingItem() || player.getActiveItem().isEmpty()) return false;

        Item item = player.getActiveItem().getItem();

        return item.getUseAction(player.getActiveItem()) == UseAction.BLOCK
                && item.getMaxUseTime(player.getActiveItem(), player) - player.getItemUseTimeLeft() < 5;
    }

    @Unique
    private boolean isDisabled() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        return player != null && player.getItemCooldownManager().isCoolingDown(Items.SHIELD);
    }
}
