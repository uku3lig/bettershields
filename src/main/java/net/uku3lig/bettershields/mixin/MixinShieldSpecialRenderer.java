package net.uku3lig.bettershields.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.special.ShieldSpecialRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.uku3lig.bettershields.BetterShields;
import net.uku3lig.bettershields.config.ShieldConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// part of this code was kindly provided by Marlow's friend, massive thanks to them!
@Mixin(ShieldSpecialRenderer.class)
public class MixinShieldSpecialRenderer {
    @ModifyArg(method = "submit(Lnet/minecraft/core/component/DataComponentMap;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;IIZI)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModelPart(Lnet/minecraft/client/model/geom/ModelPart;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;ZZILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;I)V"),
            index = 8)
    public int changeShieldColor(int tintedColor, @Local(argsOnly = true) ItemDisplayContext mode) {
        boolean isPlayerSelf = mode.firstPerson() || mode == ItemDisplayContext.GUI;
        if (isPlayerSelf) {
            BetterShields.setCurrentRenderedPlayer(Minecraft.getInstance().player);
        }

        ShieldConfig config = BetterShields.getManager().getConfig();
        // only color other player's shield if that's set to true
        if (config.isColoredShields() && (isPlayerSelf || config.isColorOtherPlayers())) {
            return BetterShields.getShieldColorForCurrent() | (0xFF << 24);
        } else {
            return tintedColor;
        }
    }
}
