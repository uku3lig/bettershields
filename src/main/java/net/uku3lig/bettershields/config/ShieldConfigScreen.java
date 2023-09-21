package net.uku3lig.bettershields.config;

import net.minecraft.client.gui.screen.Screen;
import net.uku3lig.bettershields.BetterShields;
import net.uku3lig.ukulib.config.option.ColorOption;
import net.uku3lig.ukulib.config.option.CyclingOption;
import net.uku3lig.ukulib.config.option.WidgetCreator;
import net.uku3lig.ukulib.config.screen.AbstractConfigScreen;

public class ShieldConfigScreen extends AbstractConfigScreen<ShieldConfig> {
    public ShieldConfigScreen(Screen parent) {
        super("BetterShields Config", parent, BetterShields.getManager());
    }

    @Override
    protected WidgetCreator[] getWidgets(ShieldConfig config) {
        return new WidgetCreator[] {
                CyclingOption.ofBoolean("bettershields.config.soundsEnabled", config.isSoundsEnabled(), config::setSoundsEnabled),
                CyclingOption.ofBoolean("bettershields.config.coloredShields", config.isColoredShields(), config::setColoredShields),
                new ColorOption("bettershields.config.disabledColor", config.getDisabledColor(), config::setDisabledColor),
                new ColorOption("bettershields.config.risingColor", config.getRisingColor(), config::setRisingColor),
        };
    }
}
