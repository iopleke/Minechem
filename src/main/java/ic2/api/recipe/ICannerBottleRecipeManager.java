package ic2.api.recipe;

import java.util.Map;

import net.minecraft.item.ItemStack;

public interface ICannerBottleRecipeManager {
	/**
	 * Adds a recipe to the machine.
	 * 
	 * @param container Container to be filled
	 * @param fill Item to fill into the container
	 * @param output Filled container
	 */
	public void addRecipe(IRecipeInput container, IRecipeInput fill, ItemStack output);

	/**
	 * Gets the recipe output for the given input.
	 * 
	 * @param container Container to be filled
	 * @param fill Item to fill into the container
	 * @param adjustInput modify the input according to the recipe's requirements
	 * @param acceptTest allow either container or fill to be null to see if either of them is part of a recipe
	 * @return Recipe output, or null if none
	 */
	public RecipeOutput getOutputFor(ItemStack container, ItemStack fill, boolean adjustInput, boolean acceptTest);

	/**
	 * Gets a list of recipes.
	 * 
	 * You're a mad evil scientist if you ever modify this.
	 * 
	 * @return List of recipes
	 */
	public Map<Input, RecipeOutput> getRecipes();


	public static class Input {
		public Input(IRecipeInput container1, IRecipeInput fill1) {
			this.container = container1;
			this.fill = fill1;
		}

		public boolean matches(ItemStack container1, ItemStack fill1) {
			return this.container.matches(container1) && this.fill.matches(fill1);
		}

		public final IRecipeInput container;
		public final IRecipeInput fill;
	}
}
