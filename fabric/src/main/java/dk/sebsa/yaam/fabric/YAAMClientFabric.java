package dk.sebsa.yaam.fabric;

import dk.sebsa.yaam.client.YAAMClient;
import net.fabricmc.api.ClientModInitializer;

public class YAAMClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        YAAMClient.init();
    }
}
