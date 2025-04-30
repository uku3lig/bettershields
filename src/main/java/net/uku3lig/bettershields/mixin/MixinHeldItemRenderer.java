package net.uku3lig.bettershields.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.uku3lig.bettershields.BetterShields;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HeldItemRenderer.class)
public class MixinHeldItemRenderer {
    @ModifyArg(method = "renderFirstPersonItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyEquipOffset(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/Arm;F)V"),
            index = 2)
    public float modifyEquipProgress(float original, @Local(argsOnly = true) ItemStack item) {
        if (item.isOf(Items.SHIELD)) {
            return switch (BetterShields.getManager().getConfig().getRisingAnimation()) {
                case NORMAL -> original;
                case SKIP -> 0;
                case WAIT_DELAY -> MathHelper.ceil(original);
            };
        } else {
            return original;
        }
    }
}
