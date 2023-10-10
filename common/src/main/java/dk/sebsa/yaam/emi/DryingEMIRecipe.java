package dk.sebsa.yaam.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import dev.emi.emi.api.render.EmiTexture;
import dk.sebsa.yaam.YetAnotherAdditionsMod;
import dk.sebsa.yaam.recipe.DryingRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class DryingEMIRecipe extends BasicEmiRecipe {
    private final String dryingTimeS;

    public DryingEMIRecipe(DryingRecipe dryingRecipe) {
        super(YAAMEMI.DRYING_CATEGORY, dryingRecipe.getId(), 76, 26);
        this.inputs.add(EmiIngredient.of(dryingRecipe.getInput()));
        this.outputs.add(EmiStack.of(dryingRecipe.getOutput()));

        int dryingTime = dryingRecipe.getTimeMax();
        if(dryingTime >= 20*60) dryingTimeS = Math.round((float) dryingTime / (60*20))+"m";
        else if(dryingTime >= 20) dryingTimeS = Math.round((float) dryingTime /20)+"s";
        else dryingTimeS = dryingTime +"t";
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        // Add an arrow texture to indicate processing
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 26, 1);

        // Adds an input slot on the left
        widgets.addSlot(inputs.get(0), 0, 0);

        widgets.addText(Component.literal(dryingTimeS), 26,18,0,false);

        // Adds an output slot on the right
        // Note that output slots need to call `recipeContext` to inform EMI about their recipe context
        // This includes being able to resolve recipe trees, favorite stacks with recipe context, and more
        widgets.addSlot(outputs.get(0), 58, 0).recipeContext(this);
    }
}
