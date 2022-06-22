package fireopal.whostyping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.Formatting;

public class CurrentlyTypingPlayers {
    private Set<CurrentlyTypingPlayer> typing;

    public CurrentlyTypingPlayers() {
        this.typing = new HashSet<>();
    }

    public boolean tick(int ticks) {

        try {
            if (Objects.isNull(typing)) return false;

            for (CurrentlyTypingPlayer c : typing) {
                if (Objects.isNull(c)) {
                    typing.remove(c);
                    continue;
                }
                
                c.tick(ticks);

                if (!c.stillValid()) {
                    typing.remove(c);
                }
            }

            return !typing.isEmpty();
        } catch (Exception e) {
            // e.printStackTrace();
        }

        return false;
    }

    public Set<Text> getPlayerNames() {
        Set<Text> names = new HashSet<>();

        for (CurrentlyTypingPlayer c : typing) {
            names.add(c.getPlayer().getDisplayName());
        }

        return names;
    }

    public boolean addPlayer(ServerPlayerEntity player) {
        return this.typing.add(
            new CurrentlyTypingPlayer(player)
        );
    }

    public void notifyPlayers(PlayerManager manager) {
        Text text = format(getPlayerNames());

        for (ServerPlayerEntity p : manager.getPlayerList()) {
            p.sendMessage(text, true);
        }
    }

    public Text getFormattedPlayerNames() {
        return format(this.getPlayerNames());
    }

    private static Text format(Set<Text> playerNames) {
        MutableText text = MutableText.of(TextContent.EMPTY);

        List<Text> playerNamesList = new ArrayList<>();

        for (Text t : playerNames) {
            playerNamesList.add(t);
        }
        
        final int size = playerNamesList.size();

        if (size == 0) {
            return Text.of("Nobody is typing");
        }

        for (int i = 0; i < size; i += 1) {
            text.append(playerNamesList.get(i));
            
            if (i < size - 2) {
                text.append(", ");
            } else if (i == size - 2) {
                text.append(" & ");
            }
        }

        if (size == 1) {
            text.append(" is typing");
        } else {
            text.append(" are typing");
        }

        return text.styled(style -> style.withColor(Formatting.GRAY));
    }
}
