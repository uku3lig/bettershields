package net.uku3lig.bettershields.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.model.special.ShieldModelRenderer;
import net.minecraft.item.ItemDisplayContext;
import net.uku3lig.bettershields.BetterShields;
import net.uku3lig.bettershields.config.ShieldConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// part of this code was kindly provided by Marlow's friend, massive thanks to them!
@Mixin(ShieldModelRenderer.class)
public class MixinShieldModelRenderer {
    @ModifyArg(method = "render(Lnet/minecraft/component/ComponentMap;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;IIZI)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;submitModelPart(Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/RenderLayer;IILnet/minecraft/client/texture/Sprite;ZZILnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;I)V"),
            index = 8)
    public int changeShieldColor(int tintedColor, @Local(argsOnly = true) ItemDisplayContext mode) {
        boolean isPlayerSelf = mode.isFirstPerson() || mode == ItemDisplayContext.GUI;
        if (isPlayerSelf) {
            BetterShields.setCurrentRenderedPlayer(MinecraftClient.getInstance().player);
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
