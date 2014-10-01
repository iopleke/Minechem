package minechem.tileentity.decomposer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import minechem.potion.PotionChemical;
import minechem.utils.Recipe;
import net.minecraft.item.ItemStack;

public class DecomposerRecipeSuper extends DecomposerRecipe {
	
	public Map<DecomposerRecipeSelect,Integer> selectRecipes = new Hashtable<DecomposerRecipeSelect,Integer>();
	
	public DecomposerRecipeSuper(ItemStack input, ItemStack[] components, int level)
	{
		this.input = input;
		System.out.println(input.toString());
		for (ItemStack component:components)
		{
			if (component!=null)
			{
				//TODO: Scale based on recipe output (4 stone bricks from 4 stone, etc)
				DecomposerRecipe decompRecipe = DecomposerRecipe.get(component);
				if (decompRecipe!=null)
				{
					if (decompRecipe instanceof DecomposerRecipeSelect)
					{
						addSelectRecipe((DecomposerRecipeSelect)decompRecipe,1);
					}
					else if (decompRecipe instanceof DecomposerRecipeSuper)
					{
						addDecompRecipeSuper((DecomposerRecipeSuper)decompRecipe);
					}
					else if (decompRecipe instanceof DecomposerRecipeChance)
					{
						addSelectRecipe(new DecomposerRecipeSelect(decompRecipe.getInput(),((DecomposerRecipeChance)decompRecipe).getChance(),new DecomposerRecipe(decompRecipe.getInput(), decompRecipe.getOutputRaw())),1);
					}
					else
					{
						addPotionChemical(decompRecipe.getOutput());
					}
				}
				else
				{
					//Recursively generate recipe
					//System.out.println("no recipe");
					Recipe recipe = Recipe.get(component);
					if (recipe!=null && level<10)
					{
						DecomposerRecipeSuper newSuper;
						DecomposerRecipe.add(newSuper = new DecomposerRecipeSuper(recipe.output,recipe.inStacks,level+1));
						addDecompRecipeSuper(newSuper);
					}
				}
			}
		}
	}
	
	public DecomposerRecipeSuper(ItemStack input, ItemStack[] components)
	{
		this(input,components,0);
	}
	
	private void addDecompRecipeSuper(DecomposerRecipeSuper recipeSuper)
	{
		addPotionChemical(recipeSuper.getGuaranteedOutput());
		Map<DecomposerRecipeSelect,Integer> newSelectRecipes = recipeSuper.getSelectRecipes();
		for (DecomposerRecipeSelect recipeSelect:newSelectRecipes.keySet())
		{
			addSelectRecipe(recipeSelect,newSelectRecipes.get(recipeSelect));
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
	
	public boolean isNull()
	{
		if (super.getOutput()==null && this.selectRecipes==null) return true;
		return false;
	}
	
	@Override
	public ArrayList<PotionChemical> getPartialOutputRaw(int f) {
		return super.getPartialOutputRaw(f);
	}
}
