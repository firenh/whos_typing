package fireopal.whostyping.mixin;

import java.util.function.BooleanSupplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fireopal.whostyping.WhosTyping;
import net.minecraft.server.MinecraftServer;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if (((MinecraftServer)(Object)this).getTicks() % 5 == 0) {
            if (WhosTyping.getCurrentlyTypingPlayers().tick(5)) {
                WhosTyping.getCurrentlyTypingPlayers().notifyPlayers(((MinecraftServer)(Object)this).getPlayerManager());
            }
        }
    }
}
