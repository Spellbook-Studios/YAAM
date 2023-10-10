package dk.sebsa.beholder;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class Beholder {
    private final String modId;
    public final DeferredRegister<Block> BLOCKS_REGISTRY;
    public final DeferredRegister<Item> ITEMS_REGISTRY;
    public final DeferredRegister<RecipeType<?>> RECIPE_TYPES_REGISTRY;
    public final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS_REGISTRY;
    public final DeferredRegister<CreativeModeTab> TABS_REGISTRY;
    public final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTRY;

    public Beholder(String modId) {
        this.modId = modId;

        ITEMS_REGISTRY = DeferredRegister.create(modId, Registries.ITEM);
        BLOCKS_REGISTRY = DeferredRegister.create(modId, Registries.BLOCK);
        TABS_REGISTRY = DeferredRegister.create(modId, Registries.CREATIVE_MODE_TAB);
        BLOCK_ENTITY_REGISTRY = DeferredRegister.create(modId, Registries.BLOCK_ENTITY_TYPE);
        RECIPE_TYPES_REGISTRY = DeferredRegister.create(modId, Registries.RECIPE_TYPE);
        RECIPE_SERIALIZERS_REGISTRY = DeferredRegister.create(modId, Registries.RECIPE_SERIALIZER);
    }

    public RegistrySupplier<Item> registerItem(String id, Supplier<? extends Item> item) {
        return ITEMS_REGISTRY.register(id, item);
    }

    public RegistrySupplier<Block> registerblock(String id, Supplier<? extends Block> block) {
        return BLOCKS_REGISTRY.register(id, block);
    }

    public DeferredSupplier<CreativeModeTab> registerTab(String id, Component title, Supplier<ItemStack> icon) {
        return TABS_REGISTRY.register(id, () -> CreativeTabRegistry.create(title, icon));
    }

    public RegistrySupplier<Block> registerBlockWithItem(String id, Supplier<? extends Block> block, DeferredSupplier<CreativeModeTab> tab) {
        var b = registerblock(id, block);
        if(tab != null)
            registerItem(id, () -> new BlockItem(b.get(), new Item.Properties().arch$tab(tab)));
        else
            registerItem(id, () -> new BlockItem(b.get(), new Item.Properties()));
        return b;
    }

    public <R extends BlockEntity, T extends BlockEntityType<R>> RegistrySupplier<BlockEntityType<R>> registerBlockEntity(String id, BlockEntityType.BlockEntitySupplier<R> be, RegistrySupplier<Block> b) {
        return BLOCK_ENTITY_REGISTRY.register(id, () -> BlockEntityType.Builder.of(be, b.get()).build(null));
    }

    public RegistrySupplier<? extends RecipeType<?>> registerRecipeType(ResourceLocation id, RecipeSerializer<?> recipeSerializer, String typeId, RecipeType<?> instance) {
        RECIPE_SERIALIZERS_REGISTRY.register(id, () -> recipeSerializer);
        return RECIPE_TYPES_REGISTRY.register(new ResourceLocation(modId, typeId), () -> instance);
    }

    public void register() {
        BLOCKS_REGISTRY.register();
        TABS_REGISTRY.register();
        ITEMS_REGISTRY.register();
        BLOCK_ENTITY_REGISTRY.register();
        RECIPE_SERIALIZERS_REGISTRY.register();
        RECIPE_TYPES_REGISTRY.register();
    }
}
