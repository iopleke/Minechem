package minechem.minetweaker;

import java.util.ArrayList;

import minechem.potion.PotionChemical;
import minechem.tileentity.decomposer.DecomposerFluidRecipe;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.decomposer.DecomposerRecipeChance;
import minechem.tileentity.decomposer.DecomposerRecipeSelect;
import minechem.tileentity.decomposer.DecomposerRecipeSuper;
import minechem.utils.InputHelper;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenClass("mods.minechem.Decomposer")
public class Decomposer {
	

	/**
	 * Add Recipe
	 * @param input   	as input stack
	 * @param chance	chance of output (Optional)
	 * @param outputs 	as Ingredient stack array
	 */
	@ZenMethod
	public static void addRecipe(IIngredient input, @Optional double chance, IIngredient[]... multiOutputs) 
	{
		if (multiOutputs.length==1)
		{
			IIngredient[] outputs = multiOutputs[0];
			ArrayList<PotionChemical> output = InputHelper.getChemicals(outputs);
			if (output.size()==outputs.length)
			{
				ArrayList<ItemStack> toAdd = InputHelper.getInputs(input);
				for (ItemStack addInput:toAdd)
				{
					if (chance==0||chance>=1)
						MineTweakerAPI.apply(new AddRecipeAction(addInput, output));
					else if (chance<1)
						MineTweakerAPI.apply(new AddRecipeAction(addInput, (float)chance, InputHelper.getArray(output)));
				}
			}
			else addSuperRecipe(input, outputs, output);
		}
		else
			addMultiRecipe(input,multiOutputs,chance);
	}

	public static void addSuperRecipe(IIngredient input,IIngredient[] recipe, ArrayList<PotionChemical> chemicals) 
	{ 
		ArrayList<ItemStack> output = new ArrayList<ItemStack>();
		for (IIngredient ingredient:recipe)
		{
			ItemStack in=InputHelper.getItem(ingredient);
			if (in!=null && DecomposerRecipe.get(in)!=null)
			{
				output.add(in);
			}
		}
		if (!output.isEmpty())
		{
			ArrayList<ItemStack> toAdd = InputHelper.getInputs(input);
			for (ItemStack addInput:toAdd)
				MineTweakerAPI.apply(new AddRecipeAction(addInput, InputHelper.getItemArray(output),chemicals));
		}
	}
	
	/**
	 * Add Recipe
	 * @param input   as input stack
	 * @param outputs as chemical stack array
	 */
	@ZenMethod
	public static void addFluid(ILiquidStack input,IIngredient... outputs) 
	{
		ArrayList<PotionChemical> output = InputHelper.getChemicals(outputs);
		if (!output.isEmpty())
		{
			FluidStack addInput = InputHelper.toFluid(input);
			if (addInput!=null)
				MineTweakerAPI.apply(new AddRecipeAction(addInput, InputHelper.getArray(output)));
		}
	}
	
	/**
	 * Add Select Recipe
	 * @param input   		as input stack
	 * @param multioutputs 	as recipe output stack array
	 * @param chance 		chance of any output
	 */
	//@ZenMethod
	public static void addMultiRecipe(IIngredient input, IIngredient[][] multioutputs, @Optional double chance)
	{
		if (chance<=0||chance>1)chance=1;
		ArrayList<DecomposerRecipe> decompRecipes = new ArrayList<DecomposerRecipe>();
		for (IIngredient[] recipe:multioutputs)
		{
			ArrayList<PotionChemical> output = InputHelper.getChemicals(recipe);
			if (!output.isEmpty())
			{
				decompRecipes.add(new DecomposerRecipe(InputHelper.getArray(output)));
			}
		}
		if (!decompRecipes.isEmpty())
		{
			ArrayList<ItemStack> toAdd = InputHelper.getInputs(input);
			for (ItemStack addInput:toAdd)
				MineTweakerAPI.apply(new AddRecipeAction(addInput, (float) chance, InputHelper.getDecompArray(decompRecipes)));
		}
	}
	
	@ZenMethod
	public static void removeRecipe(@Optional IIngredient input, @Optional ILiquidStack fluid) {
		if (input!=null)
		{
			ArrayList<ItemStack> toRemove = InputHelper.getInputs(input);
			
			for (ItemStack recipe : toRemove) {
				if (DecomposerRecipe.get(recipe)!=null)
					MineTweakerAPI.apply(new RemoveRecipeAction(recipe));
			}
		}
		else if (fluid!=null)
		{
			FluidStack recipe = InputHelper.toFluid(fluid);
			if (DecomposerRecipe.get(recipe)!=null)
				MineTweakerAPI.apply(new RemoveRecipeAction(recipe));
		}
	}
	
//	@ZenMethod
//	public static void removeRecipe(ILiquidStack input) {
//		FluidStack recipe = InputHelper.toFluid(input);
//		if (DecomposerRecipe.get(recipe)!=null)
//			MineTweakerAPI.apply(new RemoveRecipeAction(recipe));
//	}
	
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction implements IUndoableAction {
		private final DecomposerRecipe recipe;
		
		public AddRecipeAction(ItemStack input, ArrayList<PotionChemical> output) {
			recipe = new DecomposerRecipe(input,output);
		}
		
		public AddRecipeAction(FluidStack input, PotionChemical... output) {
			recipe = new DecomposerFluidRecipe(input,output);
		}
		
		public AddRecipeAction(ItemStack input, float chance, PotionChemical... output) {
			recipe = new DecomposerRecipeChance(input, chance, output);
		}

		public AddRecipeAction(ItemStack input, float chance, DecomposerRecipe... recipes) {
			recipe = new DecomposerRecipeSelect(input,chance,recipes);
		}

		public AddRecipeAction(ItemStack addInput, ItemStack[] itemArray, ArrayList<PotionChemical> chemicals) {
			recipe = new DecomposerRecipeSuper(addInput,itemArray,chemicals);
		}

		@Override
		public void apply() {
			DecomposerRecipe.add(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			DecomposerRecipe.remove(recipe.getInput());
		}

		@Override
		public String describe() {
			return "Adding decomposer recipe for " + recipe.getInput().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing decomposer recipe for " + recipe.getInput().getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveRecipeAction implements IUndoableAction {
		private final DecomposerRecipe recipe;
		
		public RemoveRecipeAction(ItemStack input) {
			this.recipe = DecomposerRecipe.get(input);
		}

		public RemoveRecipeAction(FluidStack input) {
			this.recipe = DecomposerRecipe.get(input);
		}

		@Override
		public void apply() {
			DecomposerRecipe.remove(recipe.getInput());
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			DecomposerRecipe.add(recipe);
		}

		@Override
		public String describe() {
			return "Removing decomposer recipe for " + recipe.getInput().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring decomposer recipe for " + recipe.getInput().getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}