package fireopal.whostyping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;

public class WhosTyping implements ModInitializer {
	public static final String MODID = "whos_typing";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	private static CurrentlyTypingPlayers currentlyTypingPlayers = new CurrentlyTypingPlayers();

	public static CurrentlyTypingPlayers getCurrentlyTypingPlayers() {
		return currentlyTypingPlayers;
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
	}


}
