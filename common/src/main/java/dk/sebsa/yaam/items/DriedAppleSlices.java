package dk.sebsa.yaam.items;

import dk.sebsa.yaam.YAAMRegistry;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class DriedAppleSlices extends Item {
    public DriedAppleSlices() {
        super(new Properties().arch$tab(YAAMRegistry.YAAM_TAB).food(new FoodProperties.Builder()
                        .meat()
                        .nutrition(4)
                        .saturationMod(1.357f)
                        .build()));
    }
}
