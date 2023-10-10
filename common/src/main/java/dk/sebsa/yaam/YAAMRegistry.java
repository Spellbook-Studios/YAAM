package dk.sebsa.yaam;

import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import dk.sebsa.beholder.Beholder;
import dk.sebsa.yaam.blocks.DryingRack;
import dk.sebsa.yaam.blocks.entities.DryingRackEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class YAAMRegistry {
    public static final Beholder BEHOLDER = new Beholder(YetAnotherAdditionsMod.MOD_ID);
    public static final DeferredSupplier<CreativeModeTab> YAAM_TAB = BEHOLDER.registerTab("yaam_tab", Component.translatable("category.yaam_tab"), () -> new ItemStack(Items.LANTERN));
    public static final RegistrySupplier<Block> DRYING_RACK = BEHOLDER.registerBlockWithItem("drying_rack", () -> new DryingRack(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), YAAM_TAB);
    public static final RegistrySupplier<BlockEntityType<DryingRackEntity>> DRYING_RACK_ENTITY = BEHOLDER.registerBlockEntity("drying_rack_entity", DryingRackEntity::new, DRYING_RACK);

    public static void register() {
        BEHOLDER.register();
    }
}
