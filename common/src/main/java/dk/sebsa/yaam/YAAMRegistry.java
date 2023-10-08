package dk.sebsa.yaam;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dk.sebsa.yaam.blocks.DryingRack;
import dk.sebsa.yaam.blocks.entities.DryingRackEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class YAAMRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(YetAnotherAdditionsMod.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> YAAM_TAB = TABS.register(
            "yaam_tab", // Tab ID
            () -> CreativeTabRegistry.create(
                    Component.translatable("category.yaam_tab"), // Tab Name
                    () -> new ItemStack(Items.LANTERN) // Icon
            )
    );

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(YetAnotherAdditionsMod.MOD_ID, Registries.BLOCK);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(YetAnotherAdditionsMod.MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Block> DRYING_RACK = BLOCKS.register("drying_rack", () -> new DryingRack(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(YetAnotherAdditionsMod.MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    public static final RegistrySupplier<BlockEntityType<DryingRackEntity>> DRYING_RACK_ENTITY =
            BLOCK_ENTITIES.register("drying_rack_entity", () -> BlockEntityType.Builder.of(DryingRackEntity::new, DRYING_RACK.get()).build(null));

    public static void register() {
        BLOCKS.register();
        TABS.register();
        ITEMS.register("drying_rack", () -> new BlockItem(DRYING_RACK.get(), new Item.Properties().arch$tab(YAAM_TAB)));
        ITEMS.register();
        BLOCK_ENTITIES.register();
    }
}
