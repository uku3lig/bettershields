package net.uku3lig.bettershields.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.uku3lig.bettershields.BetterShields;
import net.uku3lig.bettershields.config.ShieldConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BannerBlockEntityRenderer.class)
public class MixinBannerBlockEntityRenderer {
    @WrapOperation(method = "renderCanvas(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/util/SpriteIdentifier;ZLnet/minecraft/util/DyeColor;Lnet/minecraft/component/type/BannerPatternsComponent;ZZ)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V"))
    private static void changePlateColor(ModelPart instance, MatrixStack matrices, VertexConsumer vertices, int light, int overlay, Operation<Void> original, @Local(argsOnly = true, ordinal = 0) boolean isBanner) {
        if (!isBanner) {
            ShieldConfig config = BetterShields.getManager().getConfig();
            boolean isPlayerSelf = BetterShields.getCurrentRenderedPlayer().getUuid().equals(MinecraftClient.getInstance().player.getUuid());

            if (config.isColoredShields() && (isPlayerSelf || config.isColorOtherPlayers())) {
                instance.render(matrices, vertices, light, overlay, BetterShields.getShieldColorForCurrent());
                return;
            }
        }

        original.call(instance, matrices, vertices, light, overlay);
    }
}
