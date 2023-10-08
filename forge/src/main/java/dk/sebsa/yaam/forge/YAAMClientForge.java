package dk.sebsa.yaam.forge;

import dk.sebsa.yaam.YetAnotherAdditionsMod;
import dk.sebsa.yaam.client.YAAMClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = YetAnotherAdditionsMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class YAAMClientForge {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        YAAMClient.init();
    }
}
