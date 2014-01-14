package pixlepix.minechem.common.recipe;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import pixlepix.minechem.api.recipe.DecomposerRecipe;
import pixlepix.minechem.api.util.Util;
import pixlepix.minechem.common.utils.MinechemHelper;

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
            System.out.println(recipe.getOutput());
            return stacks;
        }
        return null;
    }

}
