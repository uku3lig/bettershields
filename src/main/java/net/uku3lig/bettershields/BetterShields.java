package net.uku3lig.bettershields;

import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.uku3lig.bettershields.config.ShieldConfig;
import net.uku3lig.ukulib.config.ConfigManager;
import net.uku3lig.ukulib.utils.Ukutils;
import org.lwjgl.glfw.GLFW;

public class BetterShields implements ModInitializer {
    @Getter
    private static final ConfigManager<ShieldConfig> manager = ConfigManager.createDefault(ShieldConfig.class, "bettershields");

    private static final KeyBinding toggle = new KeyBinding("bettershields.toggleSounds", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "BetterShieldSounds");

    @Override
    public void onInitialize() {
        Ukutils.registerToggleBind(toggle, () -> manager.getConfig().isSoundsEnabled(), b -> manager.getConfig().setSoundsEnabled(b), Text.literal("Shield Sounds "));
    }

    public static boolean doesShieldBlock(Vec3d attackerPos, LivingEntity target) {
        if (!target.isBlocking()) return false;

        Vec3d rotation = target.getRotationVec(1);
        Vec3d relativePosition = attackerPos.relativize(target.getPos()).normalize();
        Vec3d flat = new Vec3d(relativePosition.x, 0.0, relativePosition.z);
        return flat.dotProduct(rotation) < 0.0;
    }
}
