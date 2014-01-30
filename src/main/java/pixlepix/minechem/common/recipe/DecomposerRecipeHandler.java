package pixlepix.minechem.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import pixlepix.minechem.api.core.Chemical;
import pixlepix.minechem.api.recipe.DecomposerRecipe;
import pixlepix.minechem.api.util.Util;
import pixlepix.minechem.common.utils.MinechemHelper;

import java.util.ArrayList;
import java.util.Random;

public class DecomposerRecipeHandler {
	public static DecomposerRecipeHandler instance = new DecomposerRecipeHandler();

	private DecomposerRecipeHandler() {

	}

	public DecomposerRecipe getRecipe(ItemStack input) {
		return this.getRecipe(input, 0);
	}

	public DecomposerRecipe getRecipe(ItemStack input, int level) {
		for (DecomposerRecipe recipe : DecomposerRecipe.recipes) {
			if (Util.stacksAreSameKind(input, recipe.getInput()))
				return recipe;
		}
		if (level < 5) {
			ArrayList<Chemical> output = new ArrayList<Chemical>();
			int foundRecipies = 0;
			for (Object recipe : CraftingManager.getInstance().getRecipeList()) {

				if (recipe instanceof IRecipe) {

					if (((IRecipe) recipe).getRecipeOutput() != null && ((IRecipe) recipe).getRecipeOutput().isItemEqual(input)) {

						ItemStack[] components = null;

						if (recipe instanceof ShapelessOreRecipe && ((ShapelessOreRecipe) recipe).getInput().size() > 0) {
							ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
							for (Object o : ((ShapelessOreRecipe) recipe).getInput()) {
								if (o instanceof ItemStack) {
									inputs.add((ItemStack) o);
								}
							}
							components = inputs.toArray(new ItemStack[inputs.size()]);
						}
						if (recipe instanceof ShapedOreRecipe) {
							ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
							for (Object o : ((ShapedOreRecipe) recipe).getInput()) {
								if (o instanceof ItemStack) {
									inputs.add((ItemStack) o);
								} else if (o instanceof String) {
									inputs.add(OreDictionary.getOres((String) o).get(0));
								} else if (o instanceof ArrayList) {
									inputs.add((ItemStack) ((ArrayList) o).get(0));
								}
							}
							components = inputs.toArray(new ItemStack[inputs.size()]);
						}
						if (recipe instanceof ShapelessRecipes && ((ShapelessRecipes) recipe).recipeItems.toArray() instanceof ItemStack[]) {
							components = (ItemStack[]) ((ShapelessRecipes) recipe).recipeItems.toArray();
						}
						if (recipe instanceof ShapedRecipes && ((ShapedRecipes) recipe).recipeItems instanceof ItemStack[]) {
							components = ((ShapedRecipes) recipe).recipeItems;
						}

						if (components != null) {
							ArrayList<Chemical> sum = new ArrayList<Chemical>();
							for (ItemStack item : components) {
								if (item != null) {
									DecomposerRecipe decompRecipe = this.getRecipe(item, level + 1);
									if (decompRecipe != null) {
										sum.addAll(decompRecipe.getOutputRaw());
									}
								}

							}
							if (!sum.isEmpty()) {
								Random rand = new Random();
								if (sum.containsAll(output)) {
									output = sum;
								} else if (!output.containsAll(sum) && (foundRecipies < 1 || rand.nextInt(foundRecipies) == 0)) {
									output = sum;
									foundRecipies += 1;
								}
							}
						}

					}
				}
			}

			if (!output.isEmpty()) {
				return new DecomposerRecipe(input, output.toArray(new Chemical[output.size()]));
			}
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
