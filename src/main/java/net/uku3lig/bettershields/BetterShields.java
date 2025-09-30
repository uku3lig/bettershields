package net.uku3lig.bettershields;

import lombok.Getter;
import lombok.Setter;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.uku3lig.bettershields.config.ShieldConfig;
import net.uku3lig.ukulib.config.ConfigManager;
import net.uku3lig.ukulib.utils.Ukutils;
import org.lwjgl.glfw.GLFW;

public class BetterShields implements ModInitializer {
    @Getter
    private static final ConfigManager<ShieldConfig> manager = ConfigManager.createDefault(ShieldConfig.class, "bettershields");

    private static final KeyBinding toggle = new KeyBinding("bettershields.toggleSounds", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, KeyBinding.Category.create(Identifier.of("bettershields", "key")));

    @Getter
    @Setter
    private static PlayerEntity currentRenderedPlayer = null;

    @Override
    public void onInitialize() {
        Ukutils.registerToggleBind(toggle, () -> manager.getConfig().isSoundsEnabled(), b -> manager.getConfig().setSoundsEnabled(b), Text.literal("Shield Sounds "));
    }

    public static boolean doesShieldBlock(Vec3d attackerPos, LivingEntity target) {
        if (!target.isBlocking()) return false;

        Vec3d rotation = target.getRotationVec(1);
        Vec3d relativePosition = attackerPos.relativize(target.getEntityPos()).normalize();
        Vec3d flat = new Vec3d(relativePosition.x, 0.0, relativePosition.z);
        return flat.dotProduct(rotation) < 0.0;
    }

    public static int getShieldColorForCurrent() {
        if (currentRenderedPlayer == null) {
            return 0xFFFFFFFF;
        } else if (currentRenderedPlayer.getItemCooldownManager().isCoolingDown(new ItemStack(Items.SHIELD))) {
            return manager.getConfig().getDisabledColor();
        }

        Item item = currentRenderedPlayer.getActiveItem().getItem();
        if (currentRenderedPlayer.isUsingItem()
                && !currentRenderedPlayer.getActiveItem().isEmpty()
                && item.getUseAction(currentRenderedPlayer.getActiveItem()) == UseAction.BLOCK
                && item.getMaxUseTime(currentRenderedPlayer.getActiveItem(), currentRenderedPlayer) - currentRenderedPlayer.getItemUseTimeLeft() < 5) {
            return manager.getConfig().getRisingColor();
        } else {
            return 0xFFFFFFFF;
        }
    }
}
