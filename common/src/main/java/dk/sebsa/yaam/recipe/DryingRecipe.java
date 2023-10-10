package dk.sebsa.yaam.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DryingRecipe implements Recipe<Container> {
    private final Ingredient input;
    private final int timeMax;
    private final ItemStack result;
    private final ResourceLocation id;

    public static class Type implements RecipeType<DryingRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "drying_recipe";
    }

    @Override
    public RecipeType<DryingRecipe> getType() {
        return Type.INSTANCE;
    }


    public DryingRecipe(Ingredient inputA, ItemStack result, int timeMax, ResourceLocation id) {
        this.input = inputA;
        this.result = result;
        this.timeMax = timeMax;
        this.id = id;
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return result;
    }

    public int getTimeMax() {
        return timeMax;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return input.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.getOutput();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DryingRecipeSerializer.INSTANCE;
    }

    class DryingRecipeJsonFormat {
        JsonObject input;
        String output;
        int timeMax;
    }

    public static class DryingRecipeSerializer implements RecipeSerializer<DryingRecipe>{
        public DryingRecipeSerializer() {
        }

        public static final DryingRecipeSerializer INSTANCE = new DryingRecipeSerializer();
        public static final ResourceLocation ID = new ResourceLocation("yaam:drying_recipe");

        @Override
        // Turns json into Recipe
        public DryingRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe) {
            DryingRecipeJsonFormat recipeJson = new Gson().fromJson(serializedRecipe, DryingRecipeJsonFormat.class);
            if(recipeJson.input == null || recipeJson.output == null) throw new JsonSyntaxException("A required attribute is missing!");
            if (recipeJson.timeMax == 0) recipeJson.timeMax = 20;

            Ingredient input = Ingredient.fromJson(recipeJson.input);
            Item outputItem = BuiltInRegistries.ITEM.getOptional(ResourceLocation.tryParse(recipeJson.output)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + recipeJson.output + "'"));;
            ItemStack output = new ItemStack(outputItem, 1);

            return new DryingRecipe(input, output, recipeJson.timeMax, recipeId);
        }

        @Override
        // Turns PacketByteBuf into Recipe
        public DryingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            int timeMax = buffer.readInt();
            return new DryingRecipe(input, output, timeMax, recipeId);
        }

        @Override
        // Turns Recipe into PacketByteBuf
        public void toNetwork(FriendlyByteBuf buffer, DryingRecipe recipe) {
            recipe.getInput().toNetwork(buffer);
            buffer.writeItem(recipe.getOutput());
            buffer.writeInt(recipe.getTimeMax());
        }
    }
}
