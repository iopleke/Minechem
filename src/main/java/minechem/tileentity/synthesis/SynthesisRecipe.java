package minechem.tileentity.synthesis;

import minechem.Settings;
import minechem.potion.PotionChemical;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SynthesisRecipe
{
	public static Map<String, SynthesisRecipe> recipes = new HashMap<String, SynthesisRecipe>();
	private ItemStack output;
	private PotionChemical[] shapedRecipe;
	private ArrayList<PotionChemical> unshapedRecipe = new ArrayList<PotionChemical>();
	private int energyCost;
	private boolean isShaped;

	public static SynthesisRecipe add(SynthesisRecipe recipe)
	{
		if (recipe.getOutput() != null && recipe.getOutput().getItem() != null)
		{
			if (isBlacklisted(recipe.getOutput()))
			{
				return null;
			}
			recipes.put(getKey(recipe.output), recipe);
		}

		return recipe;
	}

	public static void createAndAddRecipeSafely(String item, boolean shaped, int energyCost, PotionChemical... chemicals)
	{
		for (ItemStack i : OreDictionary.getOres(item))
		{
			SynthesisRecipe.add(new SynthesisRecipe(new ItemStack(i.getItem(), 1, i.getItemDamage()), shaped, energyCost, chemicals));
		}
	}

	public static void remove(ItemStack itemStack)
	{
		ArrayList<SynthesisRecipe> recipes = SynthesisRecipe.search(itemStack);

		for (SynthesisRecipe recipe : recipes)
		{
			SynthesisRecipe.recipes.remove(getKey(recipe.output));
		}
	}

	public static void removeRecipeSafely(String item)
	{
		for (ItemStack i : OreDictionary.getOres(item))
		{
			SynthesisRecipe.remove(i);
		}
	}

	public static SynthesisRecipe remove(String string)
	{
		if (recipes.containsKey(string))
		{
			return recipes.remove(string);
		}
		return null;
	}

	public static ArrayList<SynthesisRecipe> search(ItemStack itemStack)
	{
		ArrayList<SynthesisRecipe> results = new ArrayList<SynthesisRecipe>();

		for (SynthesisRecipe recipe : SynthesisRecipe.recipes.values())
		{
			if (itemStack.isItemEqual(recipe.output))
			{
				results.add(recipe);
			}
		}

		return results;

	}

	public static String getKey(ItemStack itemStack)
	{
        String unlocalizedName = itemStack.getItem().getUnlocalizedName(itemStack);
        if (itemStack.getItem() instanceof ItemRecord) unlocalizedName += ((ItemRecord) itemStack.getItem()).recordName;
		return unlocalizedName + "@" + itemStack.getItemDamage();
	}

    public SynthesisRecipe(ItemStack output, boolean isShaped, int energyCost, PotionChemical... recipe)
    {
        this.output = output;
        this.isShaped = isShaped;
        this.energyCost = energyCost;
        if (isShaped) this.shapedRecipe = recipe;
        else
        {
            this.unshapedRecipe = new ArrayList<PotionChemical>();
            for (PotionChemical chemical : recipe)
            {
                if (chemical != null)
                    unshapedRecipe.add(chemical);
            }
        }
    }

    public SynthesisRecipe(ItemStack output, boolean shaped, int energyCost, ArrayList<PotionChemical> recipe)
    {
        this.output = output;
        this.isShaped = shaped;
        this.energyCost = energyCost;
        if (shaped) this.shapedRecipe = recipe.toArray(new PotionChemical[recipe.size()]);
        else this.unshapedRecipe = recipe;
    }

	public ItemStack getOutput()
	{
		return this.output;
	}

	public boolean isShaped()
	{
		return this.isShaped;
	}

	public int energyCost()
	{
		return this.energyCost * Settings.synthesisMultiplier;
	}

	public PotionChemical[] getShapedRecipe()
	{
		return this.shapedRecipe;
	}


	public PotionChemical[] getShapelessRecipe()
	{
		return this.unshapedRecipe.toArray(new PotionChemical[0]);
	}

	public int getIngredientCount()
	{
		int var1 = 0;

		PotionChemical var3;
		for (Iterator<PotionChemical> var2 = this.unshapedRecipe.iterator(); var2.hasNext(); var1 += var3.amount)
		{
			var3 = var2.next();
		}

		return var1;
	}
	
	public static boolean isBlacklisted(ItemStack itemStack)
	{
		for (ItemStack stack:Settings.synthesisBlacklist)
			if (stack.getItem()==itemStack.getItem()&&(stack.getItemDamage()==Short.MAX_VALUE||stack.getItemDamage()==itemStack.getItemDamage())) return true;
		return false;
	}

}
