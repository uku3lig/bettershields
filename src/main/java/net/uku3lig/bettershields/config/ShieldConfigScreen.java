package net.uku3lig.bettershields.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import net.uku3lig.bettershields.BetterShields;
import net.uku3lig.ukulib.config.screen.AbstractConfigScreen;
import net.uku3lig.ukulib.config.screen.ColorSelectScreen;
import net.uku3lig.ukulib.utils.Ukutils;

public class ShieldConfigScreen extends AbstractConfigScreen<ShieldConfig> {
    public ShieldConfigScreen(Screen parent) {
        super(parent, Text.of("BetterShields"), BetterShields.getManager());
    }

    @Override
    protected SimpleOption<?>[] getOptions(ShieldConfig config) {
        return new SimpleOption[]{
                SimpleOption.ofBoolean("bettershields.config.soundsEnabled", config.isSoundsEnabled(), config::setSoundsEnabled),
                SimpleOption.ofBoolean("bettershields.config.coloredShields", config.isColoredShields(), config::setColoredShields),
                Ukutils.createOpenButton("bettershields.config.disabledColor", textColor(config.getDisabledColor()),
                        parent -> new ColorSelectScreen(Text.translatable("bettershields.config.disabledColor"), parent, config::setDisabledColor, config.getDisabledColor(), manager)),
                Ukutils.createOpenButton("bettershields.config.risingColor", textColor(config.getRisingColor()),
                        parent -> new ColorSelectScreen(Text.translatable("bettershields.config.risingColor"), parent, config::setRisingColor, config.getRisingColor(), manager)),
        };
    }

    private String textColor(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }
}
