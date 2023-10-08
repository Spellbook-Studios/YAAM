package dk.sebsa.yaam.forge;

import dev.architectury.platform.forge.EventBuses;
import dk.sebsa.yaam.YetAnotherAdditionsMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(YetAnotherAdditionsMod.MOD_ID)
public class YetAnotherAdditionsModForge {
    public YetAnotherAdditionsModForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(YetAnotherAdditionsMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        YetAnotherAdditionsMod.init();
    }
}