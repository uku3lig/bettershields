package net.uku3lig.bettershields.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.uku3lig.bettershields.BetterShields;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemInHandRenderer.class)
public class MixinItemInHandRenderer {
    @ModifyArg(method = "submitArmWithItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V"),
            index = 2)
    public float modifyEquipProgress(float original, @Local(argsOnly = true) ItemStack itemStack) {
        if (itemStack.is(Items.SHIELD)) {
            BetterShields.setCurrentRenderedAvatar(Minecraft.getInstance().player);
            return switch (BetterShields.getManager().getConfig().getRisingAnimation()) {
                case NORMAL -> original;
                case SKIP -> 0.0F;
                case WAIT_DELAY -> Mth.ceil(original);
            };
        } else {
            return original;
        }
    }
}
