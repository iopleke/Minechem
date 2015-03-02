package minechem.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import minechem.MinechemItemsRegistration;
import minechem.item.bucket.MinechemBucketHandler;
import minechem.item.bucket.MinechemBucketItem;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import minechem.utils.Compare;
import minechem.utils.MinechemUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ChemicalBucketNEIRecipeHandler extends ShapelessRecipeHandler
{
    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        if (result.getItem() instanceof MinechemBucketItem)
        {
            MinechemBucketItem bucket = (MinechemBucketItem) result.getItem();
            List<ItemStack> recipe = new ArrayList<ItemStack>();
            Item type = bucket.chemical instanceof ElementEnum ? MinechemItemsRegistration.element : MinechemItemsRegistration.molecule;
            int meta = bucket.chemical instanceof ElementEnum ? ((ElementEnum) bucket.chemical).atomicNumber() : ((MoleculeEnum)bucket.chemical).id();
            for (int i = 0 ; i < 4 ; i++)
                recipe.add(new ItemStack(type, 1, meta));
            recipe.add(new ItemStack(Items.bucket));
            for (int i = 0 ; i < 4 ; i++)
                recipe.add(new ItemStack(type, 1, meta));
            arecipes.add(new CachedShapelessRecipe(recipe,result));
        } else if (Compare.isStackAnElement(result))
        {
            ItemStack recipe = new ItemStack(MinechemBucketHandler.getInstance().getBucket(ElementItem.getElement(result)));
            arecipes.add(new CachedShapelessRecipe(new ItemStack[] { recipe }, new ItemStack(result.getItem(), 8, result.getItemDamage())));
        } else if (Compare.isStackAMolecule(result))
        {
            ItemStack recipe = new ItemStack(MinechemBucketHandler.getInstance().getBucket(MoleculeItem.getMolecule(result)));
            arecipes.add(new CachedShapelessRecipe(new ItemStack[] { recipe }, new ItemStack(result.getItem(), 8, result.getItemDamage())));
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        if (ingredient.getItem() == Items.bucket)
        {
            arecipes.add(new CachedChemicalBucketRecipe());
        } else if (Compare.isStackAnElement(ingredient))
        {
            MinechemBucketItem bucketItem = MinechemBucketHandler.getInstance().getBucket(ElementItem.getElement(ingredient));
            if (bucketItem == null) return;
            ItemStack result = new ItemStack(bucketItem);
            List<ItemStack> recipe = new ArrayList<ItemStack>();
            for (int i = 0 ; i < 4 ; i++)
                recipe.add(new ItemStack(ingredient.getItem(), 1, ingredient.getItemDamage()));
            recipe.add(new ItemStack(Items.bucket));
            for (int i = 0 ; i < 4 ; i++)
                recipe.add(new ItemStack(ingredient.getItem(), 1, ingredient.getItemDamage()));
            arecipes.add(new CachedShapelessRecipe(recipe,result));
        } else if (Compare.isStackAMolecule(ingredient))
        {
            MinechemBucketItem bucketItem = MinechemBucketHandler.getInstance().getBucket(MoleculeItem.getMolecule(ingredient));
            if (bucketItem == null) return;
            ItemStack result = new ItemStack(bucketItem);
            List<ItemStack> recipe = new ArrayList<ItemStack>();
            for (int i = 0 ; i < 4 ; i++)
                recipe.add(new ItemStack(ingredient.getItem(), 1, ingredient.getItemDamage()));
            recipe.add(new ItemStack(Items.bucket));
            for (int i = 0 ; i < 4 ; i++)
                recipe.add(new ItemStack(ingredient.getItem(), 1, ingredient.getItemDamage()));
            arecipes.add(new CachedShapelessRecipe(recipe,result));
        }
    }

    @Override
    public void drawExtras(int recipe)
    {
        CachedRecipe cachedRecipe = arecipes.get(recipe);
        if (cachedRecipe instanceof CachedChemicalBucketRecipe) ((CachedChemicalBucketRecipe) cachedRecipe).doCycle(cycleticks);
    }

    private class CachedChemicalBucketRecipe extends CachedShapelessRecipe
    {
        private long cycleAtTick;
        
        public CachedChemicalBucketRecipe()
        {
            cycleAtTick = -1;
        }
        
        @Override
        public List<PositionedStack> getIngredients()
        {
            return super.getIngredients();
        }

        @Override
        public PositionedStack getResult()
        {
            return super.getResult();
        }

        private void cycleIngredients()
        {
            MinechemBucketItem bucket = MinechemBucketHandler.getInstance().buckets.values().toArray(new MinechemBucketItem[MinechemBucketHandler.getInstance().buckets.size()])[MinechemUtil.random.nextInt(MinechemBucketHandler.getInstance().buckets.size())];
            List<ItemStack> recipe = new ArrayList<ItemStack>();
            Item type = bucket.chemical instanceof ElementEnum ? MinechemItemsRegistration.element : MinechemItemsRegistration.molecule;
            int meta = bucket.chemical instanceof ElementEnum ? ((ElementEnum) bucket.chemical).atomicNumber() : ((MoleculeEnum)bucket.chemical).id();
            for (int i = 0 ; i < 4 ; i++)
                recipe.add(new ItemStack(type, 1, meta));
            recipe.add(new ItemStack(Items.bucket));
            for (int i = 0 ; i < 4 ; i++)
                recipe.add(new ItemStack(type, 1, meta));
            setIngredients(recipe);
            setResult(new ItemStack(bucket));
        }
        
        public void doCycle(long tick)
        {
            if (tick >= cycleAtTick)
            {
                cycleAtTick = tick + 20;
                cycleIngredients();
            }  
        }
    }
}
