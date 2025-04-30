package net.uku3lig.bettershields.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.util.TranslatableOption;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShieldConfig implements Serializable {
    private boolean soundsEnabled = true;
    private boolean coloredShields = true;
    private int disabledColor = 0xFF0000;
    private int risingColor = 0xFFCC00;
    private RisingAnimation risingAnimation = RisingAnimation.NORMAL;

    @Getter
    @AllArgsConstructor
    public enum RisingAnimation implements TranslatableOption {
        NORMAL(0, "bettershields.rising.normal"),
        SKIP(1, "bettershields.rising.skip"),
        WAIT_DELAY(2, "bettershields.rising.waitDelay"),
        ;

        private final int id;
        private final String translationKey;
    }
}
