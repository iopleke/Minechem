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
	@ZenMethod
	public static void addRecipe(IIngredient input, String... outputs) 
	{
		ArrayList<PotionChemical> output = new ArrayList<PotionChemical>();
		for (String outputString:outputs)
		{
			PotionChemical out = InputHelper.getPotionChemical(outputString);
			if (out!=null) output.add(out);
		}
		if (!output.isEmpty())
		{
			ArrayList<ItemStack> toAdd = new ArrayList<ItemStack>();
			if(input instanceof IOreDictEntry)
				toAdd=OreDictionary.getOres(((IOreDictEntry) input).getName());
			else if(input instanceof IItemStack)
				toAdd.add(InputHelper.toStack((IItemStack) input));
			for (ItemStack addInput:toAdd)
				MineTweakerAPI.apply(new AddRecipeAction(addInput, output));
		}
	}
	
	@ZenMethod
	public static void addRecipe(ILiquidStack input, String... outputs) 
	{
		ArrayList<PotionChemical> output = new ArrayList<PotionChemical>();
		for (String outputString:outputs)
		{
			PotionChemical out = InputHelper.getPotionChemical(outputString);
			if (out!=null) output.add(out);
		}
		if (!output.isEmpty())
		{
			FluidStack addInput = InputHelper.toFluid(input);
			if (addInput!=null)
				MineTweakerAPI.apply(new AddRecipeAction(addInput, getArray(output)));
		}
	}
	
	
	@ZenMethod
	public static void addRecipe(IIngredient input, double chance, String... outputs) 
	{
		ArrayList<PotionChemical> output = new ArrayList<PotionChemical>();
		for (String outputString:outputs)
		{
			PotionChemical out = InputHelper.getPotionChemical(outputString);
			if (out!=null) output.add(out);
		}
		if (!output.isEmpty() && chance>0 && chance<=1)
		{
			ArrayList<ItemStack> toAdd = new ArrayList<ItemStack>();
			if(input instanceof IOreDictEntry)
				toAdd=OreDictionary.getOres(((IOreDictEntry) input).getName());
			else if(input instanceof IItemStack)
				toAdd.add(InputHelper.toStack((IItemStack) input));
			for (ItemStack addInput:toAdd)
				MineTweakerAPI.apply(new AddRecipeAction(addInput, (float) chance, getArray(output)));
		}
	}
	
	@ZenMethod
	public static void addRecipe(IIngredient input, IItemStack... recipe) 
	{
		ArrayList<ItemStack> output = new ArrayList<ItemStack>();
		for (IItemStack ingredient:recipe)
		{
			ItemStack in=null;
			if(ingredient instanceof IOreDictEntry)
				in=OreDictionary.getOres(((IOreDictEntry) ingredient).getName()).get(0);
			else if(ingredient instanceof IItemStack)
				in=(InputHelper.toStack((IItemStack) ingredient));
			if (in!=null && DecomposerRecipe.get(in)!=null)
			{
				output.add(in);
			}
		}
		if (!output.isEmpty())
		{
			ArrayList<ItemStack> toAdd = new ArrayList<ItemStack>();
			if(input instanceof IOreDictEntry)
				toAdd=OreDictionary.getOres(((IOreDictEntry) input).getName());
			else if(input instanceof IItemStack)
				toAdd.add(InputHelper.toStack((IItemStack) input));
			for (ItemStack addInput:toAdd)
				MineTweakerAPI.apply(new AddRecipeAction(addInput, getItemArray(output)));
		}
	}
	
	@ZenMethod
	public static void addRecipe(IIngredient input, String[]... recipes) 
	{
		ArrayList<DecomposerRecipe> decompRecipes = new ArrayList<DecomposerRecipe>();
		for (String[] recipe:recipes)
		{
			ArrayList<PotionChemical> output = new ArrayList<PotionChemical>();
			for (String outputString:recipe)
			{
				PotionChemical out = InputHelper.getPotionChemical(outputString);
				if (out!=null) output.add(out);
			}
			if (!output.isEmpty())
			{
				decompRecipes.add(new DecomposerRecipe(getArray(output)));
			}
		}
		if (!decompRecipes.isEmpty())
		{
			ArrayList<ItemStack> toAdd = new ArrayList<ItemStack>();
			if(input instanceof IOreDictEntry)
				toAdd=OreDictionary.getOres(((IOreDictEntry) input).getName());
			else if(input instanceof IItemStack)
				toAdd.add(InputHelper.toStack((IItemStack) input));
			for (ItemStack addInput:toAdd)
				MineTweakerAPI.apply(new AddRecipeAction(addInput, 1F,getDecompArray(decompRecipes)));
		}
		
	}
	
	@ZenMethod
	public static void addRecipe(IIngredient input, double chance, String[]... recipes) 
	{
		ArrayList<DecomposerRecipe> decompRecipes = new ArrayList<DecomposerRecipe>();
		for (String[] recipe:recipes)
		{
			ArrayList<PotionChemical> output = new ArrayList<PotionChemical>();
			for (String outputString:recipe)
			{
				PotionChemical out = InputHelper.getPotionChemical(outputString);
				if (out!=null) output.add(out);
			}
			if (!output.isEmpty())
			{
				decompRecipes.add(new DecomposerRecipe(getArray(output)));
			}
		}
		if (!decompRecipes.isEmpty() && chance>0 && chance<=1)
		{
			ArrayList<ItemStack> toAdd = new ArrayList<ItemStack>();
			if(input instanceof IOreDictEntry)
				toAdd=OreDictionary.getOres(((IOreDictEntry) input).getName());
			else if(input instanceof IItemStack)
				toAdd.add(InputHelper.toStack((IItemStack) input));
			for (ItemStack addInput:toAdd)
				MineTweakerAPI.apply(new AddRecipeAction(addInput, (float) chance,getDecompArray(decompRecipes)));
		}
	}
	
	
	private static DecomposerRecipe[] getDecompArray(ArrayList<DecomposerRecipe> arrayList) {
		DecomposerRecipe[] result = new DecomposerRecipe[arrayList.size()];
		for (int i=0;i<arrayList.size();i++)
			result[i] = arrayList.get(i);
		return result;
	}
	
	private static ItemStack[] getItemArray(ArrayList<ItemStack> arrayList) {
		ItemStack[] result = new ItemStack[arrayList.size()];
		for (int i=0;i<arrayList.size();i++)
			result[i] = arrayList.get(i);
		return result;
	}
	
	private static PotionChemical[] getArray(ArrayList<PotionChemical> arrayList) {
		PotionChemical[] result = new PotionChemical[arrayList.size()];
		for (int i=0;i<arrayList.size();i++)
			result[i] = arrayList.get(i);
		return result;
	}
	
	

	@ZenMethod
	public static void removeRecipe(IIngredient input) {
		ArrayList<ItemStack> toRemove = new ArrayList<ItemStack>();
		if(input instanceof IOreDictEntry)
			toRemove=OreDictionary.getOres(((IOreDictEntry) input).getName());
		else
			toRemove.add(InputHelper.toStack((IItemStack) input));
		
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