package net.uku3lig.bettershields.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.special.ShieldSpecialRenderer;
import net.uku3lig.bettershields.BetterShields;
import net.uku3lig.bettershields.config.ShieldConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// part of this code was kindly provided by Marlow's friend, massive thanks to them!
@Mixin(ShieldSpecialRenderer.class)
public class MixinShieldSpecialRenderer {
    @ModifyArg(method = "submit(Lnet/minecraft/core/component/DataComponentMap;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;IIZI)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;IIILnet/minecraft/client/resources/model/sprite/SpriteId;Lnet/minecraft/client/resources/model/sprite/SpriteGetter;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"),
            index = 5)
    public int changeShieldColor(int tintedColor) {
        boolean isPlayerSelf = Minecraft.getInstance().player.is(BetterShields.getCurrentRenderedAvatar());

        ShieldConfig config = BetterShields.getManager().getConfig();
        // only color other player's shield if that's set to true
        if (config.isColoredShields() && (isPlayerSelf || config.isColorOtherPlayers())) {
            return BetterShields.getShieldColorForCurrent() | (0xFF << 24);
        } else {
            return tintedColor;
        }
    }
}
