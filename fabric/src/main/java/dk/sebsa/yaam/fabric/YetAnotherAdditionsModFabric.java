package dk.sebsa.yaam.fabric;

import dk.sebsa.yaam.YetAnotherAdditionsMod;
import net.fabricmc.api.ModInitializer;

public class YetAnotherAdditionsModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        YetAnotherAdditionsMod.init();
    }
}