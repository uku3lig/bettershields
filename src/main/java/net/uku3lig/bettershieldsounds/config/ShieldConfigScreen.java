package net.uku3lig.bettershieldsounds.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.uku3lig.bettershieldsounds.BetterShieldSounds;
import net.uku3lig.ukulib.config.screen.AbstractConfigScreen;

public class ShieldConfigScreen extends AbstractConfigScreen<ShieldConfig> {
    public ShieldConfigScreen(Screen parent) {
        super(parent, Text.of("BetterShieldSounds"), BetterShieldSounds.getManager());
    }

    @Override
    protected SimpleOption<?>[] getOptions(ShieldConfig config) {
        return new SimpleOption[]{
                SimpleOption.ofBoolean("bettershieldsounds.config.enabled", config.isEnabled(), config::setEnabled)
        };
    }
}
