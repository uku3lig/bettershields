package net.uku3lig.bettershields.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.uku3lig.bettershields.BetterShields;
import net.uku3lig.ukulib.config.screen.AbstractConfigScreen;
import net.uku3lig.ukulib.config.screen.ColorSelectScreen;
import net.uku3lig.ukulib.utils.Ukutils;

public class ShieldConfigScreen extends AbstractConfigScreen<ShieldConfig> {
    public ShieldConfigScreen(Screen parent) {
        super(parent, Text.of("BetterShields"), BetterShields.getManager());
    }

    @Override
    protected Option[] getOptions(ShieldConfig config) {
        return new Option[]{
                CyclingOption.create("bettershields.config.soundsEnabled", opt -> config.isSoundsEnabled(), (opt, option, value) -> config.setSoundsEnabled(value)),
                CyclingOption.create("bettershields.config.coloredShields", opt -> config.isColoredShields(), (opt, option, value) -> config.setColoredShields(value)),
                Ukutils.createOpenButton("bettershields.config.disabledColor", textColor(config.getDisabledColor()),
                        parent -> new ColorSelectScreen(new TranslatableText("bettershields.config.disabledColor"), parent, config::setDisabledColor, config.getDisabledColor(), manager)),
                Ukutils.createOpenButton("bettershields.config.risingColor", textColor(config.getRisingColor()),
                        parent -> new ColorSelectScreen(new TranslatableText("bettershields.config.risingColor"), parent, config::setRisingColor, config.getRisingColor(), manager)),
        };
    }

    private String textColor(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }
}
