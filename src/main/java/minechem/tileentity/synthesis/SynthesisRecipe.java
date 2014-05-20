package minechem.tileentity.synthesis;

import java.util.ArrayList;
import java.util.Iterator;

import minechem.potion.PotionChemical;
import net.minecraft.item.ItemStack;

public class SynthesisRecipe
{

    public static ArrayList<SynthesisRecipe> recipes = new ArrayList<SynthesisRecipe>();
    private ItemStack output;
    private PotionChemical[] shapedRecipe;
    private ArrayList unshapedRecipe;
    private int energyCost;
    private boolean isShaped;

    public static SynthesisRecipe add(SynthesisRecipe var0)
    {
        recipes.add(var0);
        return var0;
    }

    public static void remove(ItemStack itemStack)
    {
        ArrayList<SynthesisRecipe> recipes = SynthesisRecipe.search(itemStack);

        for (SynthesisRecipe recipe : recipes)
        {
            SynthesisRecipe.recipes.remove(recipe);
        }
    }

    public static ArrayList<SynthesisRecipe> search(ItemStack itemStack)
    {
        ArrayList<SynthesisRecipe> results = new ArrayList<SynthesisRecipe>();

        for (SynthesisRecipe recipe : SynthesisRecipe.recipes)
        {
            if (itemStack.isItemEqual(recipe.output))
            {
                results.add(recipe);
            }
        }

        return results;

    }

    public SynthesisRecipe(ItemStack var1, boolean var2, int var3, PotionChemical... var4)
    {
        this.output = var1;
        this.isShaped = var2;
        this.energyCost = var3;
        this.shapedRecipe = var4;
        this.unshapedRecipe = new ArrayList();
        PotionChemical[] var5 = var4;
        int var6 = var4.length;

        for (int var7 = 0; var7 < var6; ++var7)
        {
            PotionChemical var8 = var5[var7];
            if (var8 != null)
            {
                this.unshapedRecipe.add(var8);
            }
        }

    }

    public SynthesisRecipe(ItemStack var1, boolean var2, int var3, ArrayList var4)
    {
        this.output = var1;
        this.isShaped = var2;
        this.energyCost = var3;
        this.shapedRecipe = (PotionChemical[]) var4.toArray(new PotionChemical[var4.size()]);
        this.unshapedRecipe = var4;
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
        return this.energyCost * 10;
    }

    public PotionChemical[] getShapedRecipe()
    {
        return this.shapedRecipe;
    }

    public ArrayList getShapelessRecipe()
    {
        return this.unshapedRecipe;
    }

    public int getIngredientCount()
    {
        int var1 = 0;

        PotionChemical var3;
        for (Iterator var2 = this.unshapedRecipe.iterator(); var2.hasNext(); var1 += var3.amount)
        {
            var3 = (PotionChemical) var2.next();
        }

        return var1;
    }

}
