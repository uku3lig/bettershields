package net.uku3lig.bettershields.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.uku3lig.ukulib.config.option.StringTranslatable;

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
    private boolean colorOtherPlayers = true;

    @Getter
    @AllArgsConstructor
    public enum RisingAnimation implements StringTranslatable {
        NORMAL("normal", "bettershields.rising.normal"),
        SKIP("skip", "bettershields.rising.skip"),
        WAIT_DELAY("wait_delay", "bettershields.rising.waitDelay"),
        ;

        private final String name;
        private final String translationKey;
    }
}
