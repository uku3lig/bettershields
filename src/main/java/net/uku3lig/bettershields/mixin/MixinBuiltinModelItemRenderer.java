package net.uku3lig.bettershields.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
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
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

// part of this code was kindly provided by Marlow's friend, massive thanks to them!
@Mixin(BuiltinModelItemRenderer.class)
public class MixinBuiltinModelItemRenderer {
    @Unique
    private ModelTransformationMode mode;

    @Inject(method = "render", at = @At("HEAD"))
    public void getMode(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        this.mode = mode;
    }

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
    public void changeShieldColor(Args args) {
        if (!this.mode.isFirstPerson()) return;
        ShieldConfig config = BetterShields.getManager().getConfig();
        if (!config.isColoredShields()) return;

        if (isDisabled()) {
            setColor(args, config.getDisabledColor());
        } else if (isRising()) {
            setColor(args, config.getRisingColor());
        }
    }

    @Unique
    private void setColor(Args args, int color) {
        args.set(4, (color >> 16 & 255) / 255.0F);
        args.set(5, (color >> 8 & 255) / 255.0F);
        args.set(6, (color & 255) / 255.0F);
    }

    @Unique
    private boolean isRising() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || !player.isUsingItem() || player.getActiveItem().isEmpty()) return false;

        Item item = player.getActiveItem().getItem();

        return item.getUseAction(player.getActiveItem()) == UseAction.BLOCK
                && item.getMaxUseTime(player.getActiveItem()) - player.getItemUseTimeLeft() < 5;
    }

    @Unique
    private boolean isDisabled() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        return player != null && player.getItemCooldownManager().isCoolingDown(Items.SHIELD);
    }
}
