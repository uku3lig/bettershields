package net.uku3lig.bettershields.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
