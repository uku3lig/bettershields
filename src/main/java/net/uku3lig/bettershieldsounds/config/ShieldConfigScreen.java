package net.uku3lig.bettershieldsounds.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import net.uku3lig.bettershieldsounds.BetterShieldSounds;
import net.uku3lig.ukulib.config.screen.AbstractConfigScreen;

public class ShieldConfigScreen extends AbstractConfigScreen<ShieldConfig> {
    public ShieldConfigScreen(Screen parent) {
        super(parent, Text.of("BetterShieldSounds"), BetterShieldSounds.getManager());
    }

    @Override
    protected Option[] getOptions(ShieldConfig config) {
        return new Option[]{
                CyclingOption.create("bettershieldsounds.config.enabled", opt -> config.isEnabled(), (opt, options, value) -> config.setEnabled(value))
        };
    }
}
