package net.uku3lig.bettershields.config;

import lombok.*;
import net.uku3lig.ukulib.config.IConfig;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShieldConfig implements IConfig<ShieldConfig> {
    private boolean soundsEnabled = true;
    private boolean coloredShields = true;
    private int disabledColor = 0xFF0000;
    private int risingColor = 0xFFCC00;

    @Override
    public ShieldConfig defaultConfig() {
        return new ShieldConfig();
    }
}
