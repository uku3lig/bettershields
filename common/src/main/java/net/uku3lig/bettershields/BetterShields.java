package net.uku3lig.bettershields;

import com.mojang.blaze3d.platform.InputConstants;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.uku3lig.bettershields.config.ShieldConfig;
import net.uku3lig.ukulib.config.ConfigManager;
import net.uku3lig.ukulib.utils.Ukutils;
import org.lwjgl.glfw.GLFW;

public class BetterShields {
    @Getter
    private static final ConfigManager<ShieldConfig> manager = ConfigManager.createDefault(ShieldConfig.class, "bettershields");

    private static final KeyMapping toggle = new KeyMapping("bettershields.toggleSounds", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, KeyMapping.Category.register(Identifier.fromNamespaceAndPath("bettershields", "key")));

    @Getter
    @Setter
    private static Avatar currentRenderedAvatar = null;

    public static void onInitialize() {
        Ukutils.registerToggleBind(toggle, () -> manager.getConfig().isSoundsEnabled(), b -> manager.getConfig().setSoundsEnabled(b), Component.literal("Shield Sounds "));
    }

    public static boolean doesShieldBlock(Vec3 attackerPos, LivingEntity target) {
        if (!target.isBlocking()) return false;

        Vec3 rotation = target.getViewVector(1);
        Vec3 relativePosition = attackerPos.vectorTo(target.position()).normalize();
        Vec3 flat = new Vec3(relativePosition.x, 0.0, relativePosition.z);
        return flat.dot(rotation) < 0.0;
    }

    public static int getShieldColorForCurrent() {
        if (!(currentRenderedAvatar instanceof Player player)) {
            return 0xFFFFFFFF;
        } else if (player.getCooldowns().isOnCooldown(new ItemStack(Items.SHIELD))) {
            return manager.getConfig().getDisabledColor();
        }

        Item item = currentRenderedAvatar.getUseItem().getItem();
        if (currentRenderedAvatar.isUsingItem()
                && !currentRenderedAvatar.getUseItem().isEmpty()
                && item.getUseAnimation(currentRenderedAvatar.getUseItem()) == ItemUseAnimation.BLOCK
                && item.getUseDuration(currentRenderedAvatar.getUseItem(), currentRenderedAvatar) - currentRenderedAvatar.getUseItemRemainingTicks() < 5) {
            return manager.getConfig().getRisingColor();
        } else {
            return 0xFFFFFFFF;
        }
    }
}
