package dk.sebsa.yaam.fabric.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;

public class YAAMEMI implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        dk.sebsa.yaam.emi.YAAMEMI.register(registry);
    }
}
