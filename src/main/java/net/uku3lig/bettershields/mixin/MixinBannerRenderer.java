package net.uku3lig.bettershields.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.uku3lig.bettershields.BetterShields;
import net.uku3lig.bettershields.config.ShieldConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BannerRenderer.class)
public class MixinBannerRenderer {
    @ModifyArg(method = "submitPatterns", index = 6,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"))
    private static int changePlateColor(int tintedColor, @Local(argsOnly = true, ordinal = 0) boolean isBanner) {
        if (!isBanner) {
            ShieldConfig config = BetterShields.getManager().getConfig();
            boolean isPlayerSelf = BetterShields.getCurrentRenderedPlayer().getUUID().equals(Minecraft.getInstance().player.getUUID());

            if (config.isColoredShields() && (isPlayerSelf || config.isColorOtherPlayers())) {
                return BetterShields.getShieldColorForCurrent();
            }
        }

        return tintedColor;
    }
}
