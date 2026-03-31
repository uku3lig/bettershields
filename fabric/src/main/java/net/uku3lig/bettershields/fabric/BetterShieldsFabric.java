package net.uku3lig.bettershields.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.uku3lig.bettershields.BetterShields;

public class BetterShieldsFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BetterShields.onInitialize();
    }
}
