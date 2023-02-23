package net.uku3lig.bettershieldsounds.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.uku3lig.ukulib.config.IConfig;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShieldConfig implements IConfig<ShieldConfig> {
    private boolean enabled = true;

    @Override
    public ShieldConfig defaultConfig() {
        return new ShieldConfig();
    }
}
