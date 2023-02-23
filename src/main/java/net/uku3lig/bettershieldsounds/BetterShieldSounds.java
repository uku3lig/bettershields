package net.uku3lig.bettershieldsounds;

import lombok.Getter;
import net.uku3lig.bettershieldsounds.config.ShieldConfig;
import net.uku3lig.ukulib.config.ConfigManager;

public class BetterShieldSounds {
    @Getter
    private static final ConfigManager<ShieldConfig> manager = ConfigManager.create(ShieldConfig.class, "bettershieldsounds");

    private BetterShieldSounds() {}
}
