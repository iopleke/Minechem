package ljdp.minechem.common.recipe;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import ljdp.minechem.api.recipe.DecomposerRecipe;
import ljdp.minechem.api.util.Util;
import ljdp.minechem.common.utils.MinechemHelper;

public class DecomposerRecipeHandler {
    public static DecomposerRecipeHandler instance = new DecomposerRecipeHandler();

    private DecomposerRecipeHandler() {

    }

    public DecomposerRecipe getRecipe(ItemStack input) {
        for (DecomposerRecipe recipe : DecomposerRecipe.recipes) {
            if (Util.stacksAreSameKind(input, recipe.getInput()))
                return recipe;
        }
        return null;
    }

    public ArrayList<ItemStack> getRecipeOutputForInput(ItemStack input) {
        DecomposerRecipe recipe = getRecipe(input);
        if (recipe != null) {
            ArrayList<ItemStack> stacks = MinechemHelper.convertChemicalsIntoItemStacks(recipe.getOutput());
            return stacks;
        }
        return null;
    }

}
