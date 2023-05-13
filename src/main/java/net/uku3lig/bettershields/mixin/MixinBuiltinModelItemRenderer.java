package net.uku3lig.bettershields.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.UseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

// part of this code was kindly provided by Marlow's friend, massive thanks to them!
@Mixin(BuiltinModelItemRenderer.class)
public class MixinBuiltinModelItemRenderer {
    private ModelTransformation.Mode mode;

    @Inject(method = "render", at = @At("HEAD"))
    public void getMode(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        this.mode = mode;
    }

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
    public void changeShieldColor(Args args) {
        if (!this.mode.isFirstPerson()) return;

        // todo put these into config
        if (isBroken()) {
            args.set(4, 1.0F);
            args.set(5, 0.0F);
            args.set(6, 0.0F);
        } else if (isRising()) {
            args.set(4, 1.0F);
            args.set(5, 0.8F);
            args.set(6, 0.0F);
        }
    }

    private boolean isRising() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || !player.isUsingItem() || player.getActiveItem().isEmpty()) return false;

        Item item = player.getActiveItem().getItem();

        return item.getUseAction(player.getActiveItem()) == UseAction.BLOCK
                && item.getMaxUseTime(player.getActiveItem()) - player.getItemUseTimeLeft() < 5;
    }

    private boolean isBroken() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        return player != null && player.getItemCooldownManager().isCoolingDown(Items.SHIELD);
    }
}
