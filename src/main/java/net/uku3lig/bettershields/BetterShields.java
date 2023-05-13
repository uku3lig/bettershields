package net.uku3lig.bettershields;

import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.uku3lig.bettershields.config.ShieldConfig;
import net.uku3lig.ukulib.config.ConfigManager;
import org.lwjgl.glfw.GLFW;

public class BetterShields implements ModInitializer {
    @Getter
    private static final ConfigManager<ShieldConfig> manager = ConfigManager.create(ShieldConfig.class, "bettershields");

    @Getter
    private static final KeyBinding toggle = new KeyBinding("bettershields.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "BetterShieldSounds");

    @Override
    public void onInitialize() {
        KeyBindingHelper.registerKeyBinding(toggle);
    }

    public static boolean doesShieldBlock(Vec3d attackerPos, LivingEntity target) {
        if (!target.isBlocking()) return false;

        Vec3d rotation = target.getRotationVec(1);
        Vec3d relativePosition = attackerPos.relativize(target.getPos()).normalize();
        Vec3d flat = new Vec3d(relativePosition.x, 0.0, relativePosition.z);
        return flat.dotProduct(rotation) < 0.0;
    }
}
