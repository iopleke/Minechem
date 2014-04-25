package ic2.api.recipe;

import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface ICannerEnrichRecipeManager {
	/**
	 * Adds a recipe to the machine.
	 * 
	 * @param input Fluid input
	 * @param additive Item to enrich the fluid with
	 * @param output Output fluid
	 */
	public void addRecipe(FluidStack input, IRecipeInput additive, FluidStack output);

	/**
	 * Gets the recipe output for the given input.
	 * 
	 * @param input Fluid input
	 * @param additive Item to enrich the fluid with
	 * @param adjustInput modify the input according to the recipe's requirements
	 * @param acceptTest allow input or additive to be null to see if either of them is part of a recipe
	 * @return Recipe output, or null if none, output fluid in nbt
	 */
	public RecipeOutput getOutputFor(FluidStack input, ItemStack additive, boolean adjustInput, boolean acceptTest);

	/**
	 * Gets a list of recipes.
	 * 
	 * You're a mad evil scientist if you ever modify this.
	 * 
	 * @return List of recipes
	 */
	public Map<Input, FluidStack> getRecipes();


	public static class Input {
		public Input(FluidStack fluid1, IRecipeInput additive1) {
			this.fluid = fluid1;
			this.additive = additive1;
		}

		public boolean matches(FluidStack fluid1, ItemStack additive1) {
			return (this.fluid == null || this.fluid.isFluidEqual(fluid1)) &&
					this.additive.matches(additive1);
		}

		public final FluidStack fluid;
		public final IRecipeInput additive;
	}
}
