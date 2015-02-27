package minechem.tileentity.decomposer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import minechem.Settings;
import minechem.api.IDecomposerControl;
import minechem.potion.PotionChemical;
import minechem.utils.LogHelper;
import minechem.utils.MapKey;
import minechem.utils.MinechemUtil;
import minechem.utils.Recipe;
import net.minecraft.item.ItemStack;

public class DecomposerRecipeSuper extends DecomposerRecipe
{
//TODO seems to be overcounting the output chance of the stained glasspane recipe (0.4375 instead of 0.375) investigate when have time
    static Random random = new Random();
    public Map<MapKey, Double> recipes = new Hashtable<MapKey, Double>();

    public DecomposerRecipeSuper(ItemStack input, ItemStack[] components, int level)
    {
        this.input = input.copy();
        this.input.stackSize = 1;

        LogHelper.debug(input.toString());
        for (ItemStack component : components)
        {
            if (component != null && component.getItem() != null)
            {
                if (component.getItem() instanceof IDecomposerControl && ((IDecomposerControl) component.getItem()).getDecomposerMultiplier(component) == 0)
                {
                    continue;
                }
                DecomposerRecipe decompRecipe = DecomposerRecipe.get(component);
                if (decompRecipe != null)
                {
                    addDecompRecipe(decompRecipe, 1.0D / Math.max(input.stackSize, 1));
                } else if (!component.isItemEqual(input) || !(component.getItemDamage() == input.getItemDamage()))
                {
                    //Recursively generate recipe
                    Recipe recipe = Recipe.get(component);
                    if (recipe != null && level < Settings.recursiveDepth)
                    {
                        DecomposerRecipeSuper newSuper;
                        DecomposerRecipe.add(newSuper = new DecomposerRecipeSuper(recipe.output, recipe.inStacks, level + 1));
                        addDecompRecipe(newSuper, 1.0D / recipe.getOutStackSize());
                    }
                }
            }
        }
    }

    private void addDecompRecipe(DecomposerRecipe decompRecipe, double d)
    {
        MapKey key = MapKey.getKey(decompRecipe.input);
        if (key != null)
        {
            Double current = recipes.put(key, d);
            if (current != null)
            {
                recipes.put(key, d + current);
            }
        }
    }

    public DecomposerRecipeSuper(ItemStack input, ItemStack[] components)
    {
        this(input, components, 0);
    }

    public DecomposerRecipeSuper(ItemStack input, ItemStack[] components, ArrayList<PotionChemical> chemicals)
    {
        this(input, components, 0);
        addPotionChemical(chemicals);
    }

    private void addPotionChemical(ArrayList<PotionChemical> out)
    {
        if (out != null)
        {
            for (PotionChemical add : out)
            {
                super.addChemicals(add);
            }
        }
    }

    @Override
    public ArrayList<PotionChemical> getOutput()
    {
        ArrayList<PotionChemical> result = super.getOutput();
        for (MapKey currentKey : this.recipes.keySet())
        {
            DecomposerRecipe current = DecomposerRecipe.get(currentKey);
            if (current != null)
            {
                Double i = this.recipes.get(currentKey);
                LogHelper.debug("getOutput :" + currentKey + " chance: " + i);
                while (i >= 1)
                {
                    ArrayList<PotionChemical> partialResult = current.getOutput();
                    if (partialResult != null && partialResult.size()>0)
                    {
                        result.addAll(partialResult);
                    }
                    i--;
                }
                if (random.nextDouble() < i)
                {
                    ArrayList<PotionChemical> partialResult = current.getOutput();
                    if (partialResult != null)
                    {
                        result.addAll(partialResult);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public ArrayList<PotionChemical> getOutputRaw()
    {
        ArrayList<PotionChemical> result = super.getOutputRaw();
        for (MapKey currentKey : this.recipes.keySet())
        {
            DecomposerRecipe current = DecomposerRecipe.get(currentKey);
            LogHelper.debug("getOutputRaw: " + currentKey);
            if (current != null)
            {
                for (int i = 0; i < this.recipes.get(currentKey); i++)
                {
                    ArrayList<PotionChemical> partialResult = current.getOutputRaw();
                    partialResult = MinechemUtil.pushTogetherChemicals(partialResult);
                    if (partialResult != null)
                    {
                        result.addAll(partialResult);
                    }
                }
            }
        }
        return MinechemUtil.pushTogetherChemicals(result);
    }

    public ArrayList<PotionChemical> getGuaranteedOutput()
    {
        return super.getOutput();
    }

    @Override
    public boolean isNull()
    {
        return super.getOutput() == null || this.recipes == null || !hasOutput();
    }

    @Override
    public ArrayList<PotionChemical> getPartialOutputRaw(int f)
    {
        return super.getPartialOutputRaw(f);
    }

    @Override
    public boolean hasOutput()
    {
        if (!this.output.isEmpty()) return true;
        if (this.recipes.isEmpty()) return false;
        for (MapKey key : this.recipes.keySet())
        {
            DecomposerRecipe recipe = DecomposerRecipe.get(key);
            if (recipe!=null && !recipe.isNull()) return true;
        }
        return false;
    }

    @Override
    public boolean outputContains(PotionChemical potionChemical)
    {
        boolean contains;
        contains = super.outputContains(potionChemical);
        if (!contains)
        {
            for (MapKey key : recipes.keySet())
            {
                DecomposerRecipe dr = DecomposerRecipe.get(key);
                LogHelper.debug("outputContains: " + key);
                if (dr == null)
                {
                    continue;
                }
                contains = dr.outputContains(potionChemical);
                if (contains)
                {
                    break;
                }
            }
        }
        return contains;
    }

    @Override
    public float getChance()
    {
        float chances = 1;
        for (Map.Entry<MapKey, Double> entry : recipes.entrySet())
        {
            DecomposerRecipe dr = DecomposerRecipe.get(entry.getKey());
            if (dr != null)
            {
                chances *= dr.getChance() / entry.getValue();
            }
        }
        return chances;
    }
}
