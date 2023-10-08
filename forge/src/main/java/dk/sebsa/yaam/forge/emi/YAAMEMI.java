package dk.sebsa.yaam.forge.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;

@EmiEntrypoint
public class YAAMEMI implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        dk.sebsa.yaam.emi.YAAMEMI.register(registry);
    }
}
