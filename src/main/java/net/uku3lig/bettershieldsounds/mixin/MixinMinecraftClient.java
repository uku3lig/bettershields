package net.uku3lig.bettershieldsounds.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.uku3lig.bettershieldsounds.BetterShieldSounds;
import net.uku3lig.bettershieldsounds.config.ShieldConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    private static final Text ON = new LiteralText("ON").formatted(Formatting.BOLD, Formatting.GREEN);
    private static final Text OFF = new LiteralText("OFF").formatted(Formatting.BOLD, Formatting.RED);

    @Shadow public ClientPlayerEntity player;

    @Inject(method = "tick", at = @At("RETURN"))
    public void processKeybindings(CallbackInfo ci) {
        while (BetterShieldSounds.getToggle().wasPressed()) {
            ShieldConfig config = BetterShieldSounds.getManager().getConfig();
            config.setEnabled(!config.isEnabled());
            BetterShieldSounds.getManager().saveConfig();

            player.sendMessage(new LiteralText("Shield Sounds ").append(config.isEnabled() ? ON : OFF), true);
        }
    }
}
