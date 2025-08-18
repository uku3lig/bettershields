package net.uku3lig.bettershields.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.item.model.special.ShieldModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.uku3lig.bettershields.BetterShields;
import net.uku3lig.bettershields.config.ShieldConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// part of this code was kindly provided by Marlow's friend, massive thanks to them!
@Mixin(ShieldModelRenderer.class)
public class MixinShieldModelRenderer {
    @WrapOperation(method = "render(Lnet/minecraft/component/ComponentMap;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IIZ)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V"))
    public void changeShieldColor(ModelPart instance, MatrixStack matrices, VertexConsumer vertices, int light, int overlay, Operation<Void> original, @Local(argsOnly = true) ItemDisplayContext mode) {
        boolean isPlayerSelf = mode.isFirstPerson() || mode == ItemDisplayContext.GUI;
        if (isPlayerSelf) {
            BetterShields.setCurrentRenderedPlayer(MinecraftClient.getInstance().player);
        }

        ShieldConfig config = BetterShields.getManager().getConfig();
        // only color other player's shield if that's set to true
        if (config.isColoredShields() && (isPlayerSelf || config.isColorOtherPlayers())) {
            instance.render(matrices, vertices, light, overlay, BetterShields.getShieldColorForCurrent());
        } else {
            original.call(instance, matrices, vertices, light, overlay);
        }
    }
}
