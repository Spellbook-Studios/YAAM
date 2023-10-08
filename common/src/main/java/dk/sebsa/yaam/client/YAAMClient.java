package dk.sebsa.yaam.client;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dk.sebsa.yaam.YAAMRegistry;
import dk.sebsa.yaam.YetAnotherAdditionsMod;
import dk.sebsa.yaam.blocks.entities.DryingRackEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

@Environment(EnvType.CLIENT)
public class YAAMClient {
    @Environment(EnvType.CLIENT)
    public static void init() {
        BlockEntityRendererRegistry.register(YAAMRegistry.DRYING_RACK_ENTITY.get(), DryingRackEntityRenderer::new);
    }
}
