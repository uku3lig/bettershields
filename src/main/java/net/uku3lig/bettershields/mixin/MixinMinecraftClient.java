package net.uku3lig.bettershields.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.uku3lig.bettershields.BetterShieldSounds;
import net.uku3lig.bettershields.config.ShieldConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    private static final Text ON = Text.literal("ON").formatted(Formatting.BOLD, Formatting.GREEN);
    private static final Text OFF = Text.literal("OFF").formatted(Formatting.BOLD, Formatting.RED);

    @Shadow public ClientPlayerEntity player;

    @Inject(method = "tick", at = @At("RETURN"))
    public void processKeybindings(CallbackInfo ci) {
        while (BetterShieldSounds.getToggle().wasPressed()) {
            ShieldConfig config = BetterShieldSounds.getManager().getConfig();
            config.setEnabled(!config.isEnabled());
            BetterShieldSounds.getManager().saveConfig();

            player.sendMessage(Text.literal("Shield Sounds ").append(config.isEnabled() ? ON : OFF), true);
        }
    }
}
