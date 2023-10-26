package dk.sebsa.yaam;

import dev.architectury.registry.fuel.FuelRegistry;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import dk.sebsa.beholder.Beholder;
import dk.sebsa.yaam.blocks.DryingRack;
import dk.sebsa.yaam.blocks.entities.DryingRackEntity;
import dk.sebsa.yaam.items.DriedAppleSlices;
import dk.sebsa.yaam.items.Jerky;
import dk.sebsa.yaam.recipe.DryingRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class YAAMRegistry {
    public static final Beholder BEHOLDER = new Beholder(YetAnotherAdditionsMod.MOD_ID);
    public static final DeferredSupplier<CreativeModeTab> YAAM_TAB = BEHOLDER.registerTab("yaam_tab", Component.translatable("category.yaam_tab"), () -> new ItemStack(Items.LANTERN));
    public static final RegistrySupplier<Block> DRYING_RACK = BEHOLDER.registerBlockWithItem("drying_rack", () -> new DryingRack(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), YAAM_TAB);
    public static final RegistrySupplier<BlockEntityType<DryingRackEntity>> DRYING_RACK_ENTITY = BEHOLDER.registerBlockEntity("drying_rack_entity", DryingRackEntity::new, DRYING_RACK);
    public static final RegistrySupplier<? extends RecipeType<?>> DRYING_RECIPE_TYPE = BEHOLDER.registerRecipeType(DryingRecipe.DryingRecipeSerializer.ID, DryingRecipe.DryingRecipeSerializer.INSTANCE,
            DryingRecipe.Type.ID, DryingRecipe.Type.INSTANCE);

    public static final RegistrySupplier<Item> JERKY = BEHOLDER.registerItem("jerky", Jerky::new);
    public static final RegistrySupplier<Item> DRIED_APPLE_SLICES = BEHOLDER.registerItem("dried_apple_slices", DriedAppleSlices::new);
    public static final RegistrySupplier<Block> FIREWOOD_BLOCK = BEHOLDER.registerBlockWithItem("firewood", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), YAAM_TAB);

    public static void register() {
        BEHOLDER.register();
        ;
        FuelRegistry.register(1600, BEHOLDER.ITEMS_REGISTRY.getRegistrar().get(new ResourceLocation(YetAnotherAdditionsMod.MOD_ID, "firewood")));
    }
}
