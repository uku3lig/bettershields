package net.uku3lig.bettershields;

import net.minecraft.client.gui.screen.Screen;
import net.uku3lig.bettershields.config.ShieldConfigScreen;
import net.uku3lig.ukulib.api.UkulibAPI;
import net.uku3lig.ukulib.config.screen.AbstractConfigScreen;

import java.util.function.Function;

public class UkulibHook implements UkulibAPI {
    @Override
    public Function<Screen, AbstractConfigScreen<?>> supplyConfigScreen() {
        return ShieldConfigScreen::new;
    }
}
