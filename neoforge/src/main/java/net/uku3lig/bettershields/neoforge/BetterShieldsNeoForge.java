package net.uku3lig.bettershields.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.uku3lig.bettershields.BetterShields;
import net.uku3lig.bettershields.UkulibHook;
import net.uku3lig.ukulib.neoforge.UkulibNFProvider;

@Mod(value = "bettershields", dist = Dist.CLIENT)
public class BetterShieldsNeoForge {
    public BetterShieldsNeoForge(ModContainer container) {
        BetterShields.onInitialize();
        container.registerExtensionPoint(UkulibNFProvider.class, UkulibHook::new);
    }
}
