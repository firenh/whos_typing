package fireopal.whostyping;

import net.minecraft.server.network.ServerPlayerEntity;

public class CurrentlyTypingPlayer {
    private ServerPlayerEntity player;
    private int ticksSinceType;

    public CurrentlyTypingPlayer(ServerPlayerEntity player) {
        this.player = player;
        this.ticksSinceType = 50;
    }

    public ServerPlayerEntity getPlayer() {
        return player;
    }

    public int getTicksSinceType() {
        return ticksSinceType;
    }

    public void setTicksSinceType(int ticksSinceType) {
        this.ticksSinceType = ticksSinceType;
    }

    public void tick(int ticks) {
        if (stillValid()) this.ticksSinceType -= ticks;
    }
    
    public boolean stillValid() {
        return this.getTicksSinceType() > 0;
    }
}
