package minechem.minetweaker;

import java.util.ArrayList;

import minechem.potion.PotionChemical;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.utils.InputHelper;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
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
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction implements IUndoableAction {
		private final DecomposerRecipe recipe;
		
		public AddRecipeAction(ItemStack input, ArrayList<PotionChemical> output) {
			recipe = new DecomposerRecipe(input,output);
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