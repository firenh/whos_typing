package fireopal.whostyping.mixinhelper;

import net.minecraft.server.network.ServerPlayerEntity;

public interface MinecraftServerInterface {
    public void addCurrentlyTypingPlayer(ServerPlayerEntity player);
}
