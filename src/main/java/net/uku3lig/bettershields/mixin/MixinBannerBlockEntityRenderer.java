package net.uku3lig.bettershields.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.uku3lig.bettershields.BetterShields;
import net.uku3lig.bettershields.config.ShieldConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BannerBlockEntityRenderer.class)
public class MixinBannerBlockEntityRenderer {
    @ModifyArg(method = "renderCanvas", index = 6,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/RenderLayer;IIILnet/minecraft/client/texture/Sprite;ILnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V"))
    private static int changePlateColor(int tintedColor, @Local(argsOnly = true, ordinal = 0) boolean isBanner) {
        if (!isBanner) {
            ShieldConfig config = BetterShields.getManager().getConfig();
            boolean isPlayerSelf = BetterShields.getCurrentRenderedPlayer().getUuid().equals(MinecraftClient.getInstance().player.getUuid());

            if (config.isColoredShields() && (isPlayerSelf || config.isColorOtherPlayers())) {
                return BetterShields.getShieldColorForCurrent();
            }
        }

        return tintedColor;
    }
}
