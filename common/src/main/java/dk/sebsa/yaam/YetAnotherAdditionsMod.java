package dk.sebsa.yaam;

import dev.architectury.platform.Platform;
import dk.sebsa.yaam.client.YAAMClient;
import net.fabricmc.api.EnvType;

public class YetAnotherAdditionsMod {
	public static final String MOD_ID = "yaam";

	public static void init() {
		YAAMRegistry.register();
	}
}
