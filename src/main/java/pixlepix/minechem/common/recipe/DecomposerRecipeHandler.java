package pixlepix.minechem.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import pixlepix.minechem.api.recipe.DecomposerRecipe;
import pixlepix.minechem.api.util.Util;
import pixlepix.minechem.common.utils.MinechemHelper;

import java.util.ArrayList;

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

	public ArrayList<ItemStack> getRecipeOutputForFluidInput(FluidStack input) {
		DecomposerFluidRecipe fluidRecipe = null;
		for (DecomposerRecipe recipe : DecomposerRecipe.recipes) {
			if (recipe instanceof DecomposerFluidRecipe && input.isFluidEqual(((DecomposerFluidRecipe) recipe).inputFluid)) {
				fluidRecipe = (DecomposerFluidRecipe) recipe;
			}
		}
		if (fluidRecipe != null) {
			ArrayList<ItemStack> stacks = MinechemHelper.convertChemicalsIntoItemStacks(fluidRecipe.getOutput());
			return stacks;
		}
		return null;
	}

}
