package dk.sebsa.yaam.emi;

import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import dk.sebsa.yaam.YAAMRegistry;
import dk.sebsa.yaam.YetAnotherAdditionsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class YAAMEMI {
    public static final ResourceLocation YAAM_ICON = new ResourceLocation(YetAnotherAdditionsMod.MOD_ID, "yaam.png");
    public static final EmiStack DRYING_WORKSTATION = EmiStack.of(YAAMRegistry.DRYING_RACK.get());
    public static final EmiRecipeCategory DRYING_CATEGORY
            = new EmiRecipeCategory(new ResourceLocation(YetAnotherAdditionsMod.MOD_ID, "drying_workstation"),
            DRYING_WORKSTATION, new EmiTexture(YAAM_ICON, 0, 0, 512, 512));

    public static void register(EmiRegistry registry) {
        registry.addCategory(DRYING_CATEGORY);
        registry.addWorkstation(DRYING_CATEGORY, DRYING_WORKSTATION);
        registry.addRecipe(new DryingEMIRecipe(Items.ROTTEN_FLESH, Items.LEATHER));
    }
}
