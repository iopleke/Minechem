package pixlepix.minechem.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import pixlepix.minechem.api.core.Chemical;
import pixlepix.minechem.api.recipe.DecomposerRecipe;

import java.util.ArrayList;

public class DecomposerFluidRecipe extends DecomposerRecipe {

	public FluidStack inputFluid;

	public DecomposerFluidRecipe(FluidStack fluid, Chemical... chemicals) {
		super(chemicals);
		this.inputFluid = fluid;
	}

	public DecomposerFluidRecipe(String fluid, int amount, Chemical[] chemicals) {

		this(new FluidStack(FluidRegistry.getFluid(fluid), amount), chemicals);
	}

	public static void createAndAddFluidRecipeSafely(String fluid, int amount, Chemical... chemicals) {
		if (FluidRegistry.isFluidRegistered(fluid)) {
			DecomposerRecipe.add(new DecomposerFluidRecipe(fluid, amount, chemicals));
		}
	}

	public ItemStack getInput() {
		return new ItemStack(this.inputFluid.getFluid().getBlockID(), 1, 0);
	}

	public ArrayList<Chemical> getOutput() {
		return this.output;
	}

	public ArrayList<Chemical> getOutputRaw() {
		return this.output;
	}

}
