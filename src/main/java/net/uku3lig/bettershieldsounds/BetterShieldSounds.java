package net.uku3lig.bettershieldsounds;

import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.uku3lig.bettershieldsounds.config.ShieldConfig;
import net.uku3lig.ukulib.config.ConfigManager;
import org.lwjgl.glfw.GLFW;

public class BetterShieldSounds implements ModInitializer {
    @Getter
    private static final ConfigManager<ShieldConfig> manager = ConfigManager.create(ShieldConfig.class, "bettershieldsounds");

    @Getter
    private static final KeyBinding toggle = new KeyBinding("bettershieldsounds.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "BetterShieldSounds");

    @Override
    public void onInitialize() {
        KeyBindingHelper.registerKeyBinding(toggle);
    }
}
