package minechem.minetweaker;

import java.util.ArrayList;

import minechem.potion.PotionChemical;
import minechem.tileentity.decomposer.DecomposerFluidRecipe;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.decomposer.DecomposerRecipeChance;
import minechem.tileentity.decomposer.DecomposerRecipeSelect;
import minechem.tileentity.decomposer.DecomposerRecipeSuper;
import minechem.utils.InputHelper;
import minechem.utils.MinechemHelper;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenClass("mods.minechem.Decomposer")
public class Decomposer {
	
	/**
	 * Add Recipe
	 * @param input   as input stack
	 * @param outputs as chemical string array
	 */
	@ZenMethod
	public static void addRecipe(IIngredient input, String... outputs) 
	{
		ArrayList<PotionChemical> output = InputHelper.getChemicals(outputs);
		if (!output.isEmpty())
		{
			addRecipe(input,output);
		}
	}
	
	/**
	 * Add Recipe
	 * @param input   as input stack
	 * @param outputs as chemical stack array
	 */
	@ZenMethod
	public static void addRecipe(IIngredient input, IItemStack... outputs) 
	{
		ArrayList<PotionChemical> output = InputHelper.getChemicals(outputs);
		if (!output.isEmpty())
		{
			addRecipe(input,output);
		}
	}
	
	private static void addRecipe(IIngredient input, ArrayList<PotionChemical> output)
	{
		ArrayList<ItemStack> toAdd = InputHelper.getInputs(input);
		for (ItemStack addInput:toAdd)
			MineTweakerAPI.apply(new AddRecipeAction(addInput, output));
	}
	
	/**
	 * Add Fluid Recipe
	 * @param input   as input liquidstack
	 * @param outputs as chemical string array
	 */
	@ZenMethod
	public static void addRecipe(ILiquidStack input, String... outputs) 
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
	 * Add Fluid Recipe
	 * @param input   as input liquidstack
	 * @param outputs as chemical stack array
	 */
	@ZenMethod
	public static void addRecipe(ILiquidStack input, IItemStack... outputs) 
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
	 * Add Chance Recipe
	 * @param input   as input stack
	 * @param chance  as double between 0 and 1
	 * @param outputs as chemical string array
	 */
	@ZenMethod
	public static void addRecipe(IIngredient input, double chance, String... outputs) 
	{
		ArrayList<PotionChemical> output = InputHelper.getChemicals(outputs);
		if (!output.isEmpty() && chance>0 && chance<=1)
		{
			addRecipe(input, chance, output);
		}
	}
	
	/**
	 * Add Chance Recipe
	 * @param input   as input stack
	 * @param chance  as double between 0 and 1
	 * @param outputs as chemical stack array
	 */
	@ZenMethod
	public static void addRecipe(IIngredient input, double chance, IItemStack... outputs) 
	{
		ArrayList<PotionChemical> output = InputHelper.getChemicals(outputs);
		if (!output.isEmpty() && chance>0 && chance<=1)
		{
			addRecipe(input, chance, output);
		}
	}
	
	private static void addRecipe(IIngredient input, double chance, ArrayList<PotionChemical> output)
	{
		ArrayList<ItemStack> toAdd = InputHelper.getInputs(input);
		for (ItemStack addInput:toAdd)
			MineTweakerAPI.apply(new AddRecipeAction(addInput, (float) chance, InputHelper.getArray(output)));
	}
	
	/**
	 * Add Super Recipe
	 * @param input   as input stack
	 * @param outputs as recipe ingredients array
	 */
	@ZenMethod
	public static void addSuperRecipe(IIngredient input, IItemStack... recipe) 
	{
		ArrayList<ItemStack> output = new ArrayList<ItemStack>();
		for (IItemStack ingredient:recipe)
		{
			ItemStack in=InputHelper.getInput(ingredient);
			if (in!=null && DecomposerRecipe.get(in)!=null)
			{
				output.add(in);
			}
		}
		if (!output.isEmpty())
		{
			ArrayList<ItemStack> toAdd = InputHelper.getInputs(input);
			for (ItemStack addInput:toAdd)
				MineTweakerAPI.apply(new AddRecipeAction(addInput, InputHelper.getItemArray(output)));
		}
	}
	
	/**
	 * Add Select Recipe
	 * @param input   as input stack
	 * @param outputs as recipe output chemical array
	 */
	@ZenMethod
	public static void addRecipe(IIngredient input, String[]... recipes) 
	{
		ArrayList<DecomposerRecipe> decompRecipes = new ArrayList<DecomposerRecipe>();
		for (String[] recipe:recipes)
		{
			ArrayList<PotionChemical> output = InputHelper.getChemicals(recipe);
			if (!output.isEmpty())
			{
				decompRecipes.add(new DecomposerRecipe(InputHelper.getArray(output)));
			}
		}
		if (!decompRecipes.isEmpty())
		{
			addRecipe(input, decompRecipes, 1F);
		}
	}
	
	/**
	 * Add Select Recipe
	 * @param input   as input stack
	 * @param outputs as recipe output stack array
	 */
	@ZenMethod
	public static void addRecipe(IIngredient input, IItemStack[]... recipes) 
	{
		ArrayList<DecomposerRecipe> decompRecipes = new ArrayList<DecomposerRecipe>();
		for (IItemStack[] recipe:recipes)
		{
			ArrayList<PotionChemical> output = InputHelper.getChemicals(recipe);
			if (!output.isEmpty())
			{
				decompRecipes.add(new DecomposerRecipe(InputHelper.getArray(output)));
			}
		}
		if (!decompRecipes.isEmpty())
		{
			addRecipe(input, decompRecipes, 1F);
		}
	}
	
	/**
	 * Add Select Recipe
	 * @param input   as input stack
	 * @param chance  as chance between 0 and 1
	 * @param outputs as recipe output array
	 */
	@ZenMethod
	public static void addRecipe(IIngredient input, double chance, String[]... recipes) 
	{
		ArrayList<DecomposerRecipe> decompRecipes = new ArrayList<DecomposerRecipe>();
		for (String[] recipe:recipes)
		{
			ArrayList<PotionChemical> output = InputHelper.getChemicals(recipe);
			if (!output.isEmpty())
			{
				decompRecipes.add(new DecomposerRecipe(InputHelper.getArray(output)));
			}
		}
		if (!decompRecipes.isEmpty() && chance>0 && chance<=1)
		{
			addRecipe(input, decompRecipes, chance);
		}
	}
	
	/**
	 * Add Select Recipe
	 * @param input   as input stack
	 * @param outputs as recipe output stack array
	 */
	@ZenMethod
	public static void addRecipe(IIngredient input, double chance, IItemStack[]... recipes) 
	{
		ArrayList<DecomposerRecipe> decompRecipes = new ArrayList<DecomposerRecipe>();
		for (IItemStack[] recipe:recipes)
		{
			ArrayList<PotionChemical> output = InputHelper.getChemicals(recipe);
			if (!output.isEmpty())
			{
				decompRecipes.add(new DecomposerRecipe(InputHelper.getArray(output)));
			}
		}
		if (!decompRecipes.isEmpty() && chance>0 && chance<=1)
		{
			addRecipe(input, decompRecipes, chance);
		}
	}
	
	private static void addRecipe(IIngredient input, ArrayList<DecomposerRecipe> decompRecipes, double chance)
	{
		ArrayList<ItemStack> toAdd = InputHelper.getInputs(input);
		for (ItemStack addInput:toAdd)
			MineTweakerAPI.apply(new AddRecipeAction(addInput, (float) chance,InputHelper.getDecompArray(decompRecipes)));
	}

	@ZenMethod
	public static void removeRecipe(IIngredient input) {
		ArrayList<ItemStack> toRemove = InputHelper.getInputs(input);
		
		for (ItemStack recipe : toRemove) {
			if (DecomposerRecipe.get(recipe)!=null)
				MineTweakerAPI.apply(new RemoveRecipeAction(recipe));
		}
	}
	
	@ZenMethod
	public static void removeRecipe(ILiquidStack input) {
		FluidStack recipe = InputHelper.toFluid(input);
		if (DecomposerRecipe.get(recipe)!=null)
			MineTweakerAPI.apply(new RemoveRecipeAction(recipe));
	}
	
	
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

		public AddRecipeAction(ItemStack addInput, ItemStack[] itemArray) {
			recipe = new DecomposerRecipeSuper(addInput,itemArray);
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
			return "Adding decomposer recipe for " + recipe.getInput().getDisplayName()+"\n";
		}

		@Override
		public String describeUndo() {
			return "Removing decomposer recipe for " + recipe.getInput().getDisplayName()+"\n";
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
			return "Removing decomposer recipe for " + recipe.getInput().getDisplayName()+"\n";
		}

		@Override
		public String describeUndo() {
			return "Restoring decomposer recipe for " + recipe.getInput().getDisplayName()+"\n";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}