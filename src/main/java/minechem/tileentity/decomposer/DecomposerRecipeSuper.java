package minechem.tileentity.decomposer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import minechem.potion.PotionChemical;
import net.minecraft.item.ItemStack;

public class DecomposerRecipeSuper extends DecomposerRecipe {
	
	public Map<DecomposerRecipeSelect,Integer> selectRecipes = new Hashtable<DecomposerRecipeSelect,Integer>();
	
	public DecomposerRecipeSuper(ItemStack input, ItemStack[] components)
	{
		this.input = input;
		for (ItemStack component:components)
		{
			if (component!=null)
			{
				DecomposerRecipe recipe = DecomposerRecipe.get(component);
				if (recipe!=null)
				{
					if (recipe instanceof DecomposerRecipeSelect)
					{
						addSelectRecipe((DecomposerRecipeSelect)recipe,1);
					}
					else if (recipe instanceof DecomposerRecipeSuper)
					{
						addPotionChemical(((DecomposerRecipeSuper)recipe).getGuaranteedOutput());
						Map<DecomposerRecipeSelect,Integer> newSelectRecipes = ((DecomposerRecipeSuper)recipe).getSelectRecipes();
						for (DecomposerRecipeSelect recipeSelect:newSelectRecipes.keySet())
						{
							addSelectRecipe(recipeSelect,newSelectRecipes.get(recipeSelect));
						}
					}
					else if (recipe instanceof DecomposerRecipeChance)
					{
						addSelectRecipe(new DecomposerRecipeSelect(recipe.getInput(),((DecomposerRecipeChance)recipe).getChance(),new DecomposerRecipe(recipe.getInput(), recipe.getOutputRaw())),1);
					}
					else
					{
						addPotionChemical(recipe.getOutput());
					}
				}
				else
				{
					//Recursively generate recipe
					System.out.println("no recipe");
				}
			}
		}
	}
	
	private void addPotionChemical(ArrayList<PotionChemical> out)
	{
		if (out!=null)
			for (PotionChemical add:out)
				super.addChemicals(add);
	}
	
	private void addSelectRecipe(DecomposerRecipeSelect recipe, int number)
	{
		Integer current = this.selectRecipes.put((DecomposerRecipeSelect)recipe,number);
		if (current!=null)
			this.selectRecipes.put((DecomposerRecipeSelect)recipe,current+number);
	}
	
	@Override
	public ArrayList<PotionChemical> getOutput() 
	{
		ArrayList<PotionChemical> result = super.getOutput();
		for (DecomposerRecipeSelect current:this.selectRecipes.keySet())
		{
			for (int i=0;i<this.selectRecipes.get(current);i++)
			{
				ArrayList<PotionChemical> partialResult = current.getOutput();
				if (partialResult!=null)result.addAll(partialResult);
			}
		}
		return result;
	}
	
	@Override
	public ArrayList<PotionChemical> getOutputRaw() {
		ArrayList<PotionChemical> result = super.getOutput();
		for (DecomposerRecipeSelect current:this.selectRecipes.keySet())
		{
			for (int i=0;i<this.selectRecipes.get(current);i++)
			{
				result.addAll(current.getOutputRaw());
			}
		}
		return result;
	}
	
	public ArrayList<PotionChemical> getGuaranteedOutput()
	{
		return super.getOutput();
	}
	
	public Map<DecomposerRecipeSelect,Integer> getSelectRecipes()
	{
		return this.selectRecipes;
	}
	
	
	@Override
	public ArrayList<PotionChemical> getPartialOutputRaw(int f) {
		return super.getPartialOutputRaw(f);
	}
}
