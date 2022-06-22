package fireopal.whostyping.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fireopal.whostyping.WhosTyping;
import net.minecraft.network.packet.c2s.play.RequestChatPreviewC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
	@Shadow @Final
	private MinecraftServer server;

	@Inject(method = "onRequestChatPreview", at = @At("HEAD"))
	private void onRequestChatPreview(RequestChatPreviewC2SPacket packet, CallbackInfo ci) {
		WhosTyping.getCurrentlyTypingPlayers().addPlayer(((ServerPlayNetworkHandler)(Object)this).getPlayer());

		WhosTyping.LOGGER.info(
			"ClientConnection: " + ((ServerPlayNetworkHandler)(Object)this).getConnection().toString() +
			"RequestChatPreviewC2SPacket:" + packet
		);
	}

	// @Inject(at = @At("HEAD"), method = "sendChatPreviewPacket", cancellable = true)
	// private void sendChatPreviewPacket(int queryId, Text preview, CallbackInfo ci) {
	// 	preview = WhosTyping.getCurrentlyTypingPlayers().getFormattedPlayerNames();

	// 	((ServerPlayNetworkHandler)(Object)this).sendPacket(new ChatPreviewS2CPacket(queryId, preview), future -> {
    //         if (!future.isSuccess()) {
    //             ((ServerPlayNetworkHandler)(Object)this).sendPacket(new ChatPreviewS2CPacket(queryId, null));
    //         }
    //     });

	// 	ci.cancel();
	// }
}
