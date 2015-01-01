package minechem.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import minechem.gui.GuiDraw;
import minechem.potion.PotionChemical;
import minechem.reference.Resources;
import minechem.tileentity.decomposer.DecomposerGui;
import minechem.tileentity.decomposer.DecomposerRecipe;
import minechem.tileentity.decomposer.DecomposerRecipeChance;
import minechem.tileentity.decomposer.DecomposerRecipeHandler;
import minechem.tileentity.decomposer.DecomposerRecipeSelect;
import minechem.tileentity.decomposer.DecomposerRecipeSuper;
import minechem.utils.Compare;
import minechem.utils.LogHelper;
import minechem.utils.MinechemUtil;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class DecomposerNEIRecipeHandler extends TemplateRecipeHandler
{

    private static final String MINECHEM_DECOMPOSER_RECIPES_ID = "minechem.decomposer";

    // GUI slot offsets, in GUI-relative pixel values.
    private static final int INPUT_X_OFS = 75;
    private static final int INPUT_Y_OFS = 5;
    private static final int OUTPUT_X_OFS = 5;
    private static final int OUTPUT_Y_SCALE = 18;
    private static final int OUTPUT_Y_OFS = 51;
    private static final int INPUT_ARROW_Y_OFS = 20;

    @Override
    public String getRecipeName()
    {
        return MinechemUtil.getLocalString("gui.title.decomposer");
    }

    @Override
    public String getGuiTexture()
    {
        return Resources.Gui.NEI_DECOMPOSER.toString();
    }

    @Override
    public int recipiesPerPage()
    {
        return 1;
    }

    @Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 11, 166, 131);
    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(INPUT_X_OFS, INPUT_ARROW_Y_OFS, 16, 30), MINECHEM_DECOMPOSER_RECIPES_ID));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(MINECHEM_DECOMPOSER_RECIPES_ID))
        {
            // Add all decomposer recipes to local arecipes array.
            for (DecomposerRecipe dr : DecomposerRecipe.recipes.values())
            {
                ItemStack input = dr.getInput().copy();
                if (input.getItemDamage() == Short.MAX_VALUE)
                {
                    input.setItemDamage(0); // Handle OreDict wildcard
                }
                LogHelper.debug(input.getDisplayName());
                registerDecomposerRecipe(dr);
            }
            arecipes = sortList(arecipes);
        } else
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        // If the result isn't a Minechem chemical, no decomposer recipe
        // can yield it.
        if (!Compare.isStackAChemical(result))
        {
            return;
        }
        // Add all decomposer recipes that can yield the result.
        PotionChemical resultChemical = MinechemUtil.itemStackToChemical(result);
        if (resultChemical == null)
        {
            return;
        }
        for (DecomposerRecipe dr : DecomposerRecipe.recipes.values())
        {
            if (dr.outputContains(resultChemical))
            {
                registerDecomposerRecipe(dr);
            }
        }
        arecipes = sortList(arecipes);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        // Add all decomposer recipes that take the ingredient as an input.
        DecomposerRecipe dr = DecomposerRecipeHandler.instance.getRecipe(ingredient);
        if (dr != null)
        {
            registerDecomposerRecipe(dr);
        }
        arecipes = sortList(arecipes);
    }

    @Override
    public Class getGuiClass()
    {
        return DecomposerGui.class;
    }

    /**
     * Registers a decomposer recipe with NEI. Anything that adds a new decomposer recipe after startup should call this to have the recipe reflected in NEI.
     *
     * @param dr Decomposer recipe to add.
     */
    public void registerDecomposerRecipe(DecomposerRecipe dr)
    {
        if (dr != null)
        {
            if (dr.hasOutput())
            {
                BaseCachedDecomposerRecipe cdr = buildCachedRecipe(dr);

                arecipes.add(cdr);
            }
        }
    }

    private ArrayList<CachedRecipe> sortList(ArrayList<CachedRecipe> list)
    {
        ArrayList<BaseCachedDecomposerRecipe> drList = new ArrayList<BaseCachedDecomposerRecipe>();
        for (CachedRecipe recipe : list)
        {
            drList.add((BaseCachedDecomposerRecipe) recipe);
        }
        Collections.sort(drList);
        list.clear();
        for (BaseCachedDecomposerRecipe recipe : drList)
        {
            list.add(recipe);
        }
        return list;
    }

    @Override
    public void drawExtras(int recipeIdx)
    {
        BaseCachedDecomposerRecipe cdr = (BaseCachedDecomposerRecipe) arecipes.get(recipeIdx);
        // Render the chance next to the down arrow in the GUI, as a percent.
        float chance = cdr.getChance();
        if (chance < 1.0f)
        {
            String format = chance > 0.09F ? "%2.0f%%" : "%.3f%%";
            String chanceStr = String.format(format, chance * 100.0);
            int xPos = INPUT_X_OFS - GuiDraw.getStringWidth(chanceStr) - 5;
            GuiDraw.drawString(chanceStr, xPos, INPUT_ARROW_Y_OFS + 10, 8, false);
        }

        // Potentially update the outputs that will be displayed on the next
        // tick, for variable multi-output recipes. This is done here and not
        // in onUpdate() to avoid cycling outputs of recipes that are not
        // visible.
        cdr.cycleOutput(cycleticks);
    }

    private BaseCachedDecomposerRecipe buildCachedRecipe(DecomposerRecipe dr)
    {
        // As Java uses the static type for method dispatch, we have to check
        // the runtime type information. It must be checked from most to least
        // specific type, as well.
        if (dr instanceof DecomposerRecipeSelect)
        {
            return new CachedDecomposerRecipeSelect((DecomposerRecipeSelect) dr);
        } else if (dr instanceof DecomposerRecipeChance)
        {
            return new CachedDecomposerRecipeChance((DecomposerRecipeChance) dr);
        } else if (dr instanceof DecomposerRecipeSuper)
        {
            return new CachedDecomposerRecipeSuper((DecomposerRecipeSuper) dr);
        } else
        {
            return new CachedDecomposerRecipe(dr);
        }
    }

    public abstract class BaseCachedDecomposerRecipe extends TemplateRecipeHandler.CachedRecipe implements Comparable
    {
        // The recipe's input item.
        protected PositionedStack input;
        // The first item to be rendered from the multi-output decomposer recipe.
        protected PositionedStack output1;
        // Other items to be rendered from the multi-output decomposer recipe.
        protected List<PositionedStack> otherOutputs = new ArrayList<PositionedStack>();

        protected BaseCachedDecomposerRecipe(ItemStack input)
        {
            this.input = new PositionedStack(input, INPUT_X_OFS, INPUT_Y_OFS);
        }

        @Override
        public PositionedStack getIngredient()
        {
            return input;
        }

        @Override
        public PositionedStack getResult()
        {
            return output1;
        }

        @Override
        public List<PositionedStack> getOtherStacks()
        {
            return otherOutputs;
        }

        @Override
        public int compareTo(Object o)
        {
            if (o instanceof BaseCachedDecomposerRecipe)
            {
                BaseCachedDecomposerRecipe recipe = (BaseCachedDecomposerRecipe) o;
                if (recipe instanceof CachedDecomposerRecipeSuper && !(this instanceof CachedDecomposerRecipeSuper))
                {
                    return -1;
                } else if (!(recipe instanceof CachedDecomposerRecipeSuper) && this instanceof CachedDecomposerRecipeSuper)
                {
                    return 1;
                }

                if (recipe.getOtherStacks().size() < this.getOtherStacks().size())
                {
                    return 1;
                } else if (recipe.getOtherStacks().size() > this.getOtherStacks().size())
                {
                    return -1;
                } else
                {
                    int countThis = 0;
                    if (getResult() != null)
                    {
                        countThis += getResult().item.stackSize;
                    }
                    for (PositionedStack stack : this.getOtherStacks())
                    {
                        countThis += stack.item.stackSize;
                    }

                    int countOther = 0;
                    if (getResult() != null)
                    {
                        countOther += getResult().item.stackSize;
                    }
                    for (PositionedStack stack : recipe.getOtherStacks())
                    {
                        countOther += stack.item.stackSize;
                    }

                    if (countOther < countThis)
                    {
                        return 1;
                    } else if (countOther > countThis)
                    {
                        return -1;
                    }
                }
            }
            return 0;
        }

        /**
         * Returns the chance that this recipe yields any output. Chance is in [0, 1], with 1 (always return output) being the default.
         */
        public float getChance()
        {
            return 1.0f;
        }

        /**
         * Cycles the output that this recipe will display, based on the given tick number. Does nothing if this recipe has no variable output.
         */
        public void cycleOutput(long tick)
        {
        }

        protected void setOutputs(List<ItemStack> outputs)
        {
            if (outputs != null && !outputs.isEmpty())
            {
                outputs = MinechemUtil.pushTogetherStacks(outputs);
                int digits = MinechemUtil.getNumberOfDigits(outputs.get(0).stackSize);
                int digitOffset = digits > 3 ? (digits - 3) * 5 : 0;
                int x = OUTPUT_X_OFS + digitOffset;
                output1 = new PositionedStack(outputs.get(0), x, OUTPUT_Y_OFS);
                otherOutputs = new ArrayList<PositionedStack>();
                if (outputs.size() > 1)
                {
                    int row = 0;
                    for (int idx = 1; idx < outputs.size(); idx++)
                    {
                        ItemStack o = outputs.get(idx);
                        digits = MinechemUtil.getNumberOfDigits(o.stackSize);
                        digitOffset = digits > 3 ? (digits - 3) * 5 : 0;
                        x += digitOffset + OUTPUT_Y_SCALE + OUTPUT_X_OFS;
                        if (x > 147)
                        {
                            x = OUTPUT_X_OFS + digitOffset;
                            row++;
                        }
                        int y = OUTPUT_Y_OFS + (row * OUTPUT_Y_SCALE);
                        otherOutputs.add(new PositionedStack(o, x, y));
                    }
                }
            }
        }
    }

    public class CachedDecomposerRecipe extends BaseCachedDecomposerRecipe
    {
        public CachedDecomposerRecipe(DecomposerRecipe dr)
        {
            super(dr.getInput());
            ArrayList<ItemStack> outputs;
            if (dr instanceof DecomposerRecipeSuper)
            {
                outputs = MinechemUtil.convertChemicalsIntoItemStacks(dr.getOutput());
            } else
            {
                outputs = MinechemUtil.convertChemicalsIntoItemStacks(dr.getOutputRaw());
            }
            setOutputs(outputs);
        }
    }

    public class CachedDecomposerRecipeChance extends CachedDecomposerRecipe
    {
        // The fractional chance [0, 1] that this recipe will yield any output.
        private float chance;

        public CachedDecomposerRecipeChance(DecomposerRecipeChance dr)
        {
            super(dr);
            this.chance = dr.getChance();
        }

        @Override
        public float getChance()
        {
            return chance;
        }
    }

    public class CachedDecomposerRecipeSelect extends CachedDecomposerRecipeChance
    {
        // Number of possible output sets.
        private int numOutputSets;
        // Which set of output to be shown.
        private int outputSetToShow;
        // Which tick to cycle at. This is initialized lazily, so
        // the first output set is shown for the normal duration.
        private long cycleAtTick;

        private DecomposerRecipeSelect decomposerRecipeSelect;

        public CachedDecomposerRecipeSelect(DecomposerRecipeSelect dr)
        {
            // This picks only the first potential output set to be displayed.
            super(dr);
            numOutputSets = dr.getAllPossibleRecipes().size();
            outputSetToShow = -1;
            decomposerRecipeSelect = dr;
            cycleAtTick = 0;
        }

        @Override
        public void cycleOutput(long tick)
        {
            if (outputSetToShow == -1)
            {
                cycleAtTick = tick + 20;
                outputSetToShow = 0;
                return;
            }
            if (tick >= cycleAtTick)
            {
                cycleAtTick = tick + 20;
                outputSetToShow++;
                if (outputSetToShow >= numOutputSets)
                {
                    outputSetToShow = 0;
                }
                ArrayList<DecomposerRecipe> possibleRecipes = decomposerRecipeSelect.getAllPossibleRecipes();
                ArrayList<ItemStack> outputsToShow = MinechemUtil.convertChemicalsIntoItemStacks(possibleRecipes.get(outputSetToShow).getOutputRaw());
                setOutputs(outputsToShow);
            }
        }
    }

    public class CachedDecomposerRecipeSuper extends CachedDecomposerRecipe
    {
        // Which tick to cycle at. This is initialized lazily, so
        // the first output set is shown for the normal duration.
        private long cycleAtTick;

        // The fractional chance [0, 1] that this recipe will yield any output.
        private float chance;

        private DecomposerRecipeSuper decomposerRecipeSuper;

        public CachedDecomposerRecipeSuper(DecomposerRecipeSuper dr)
        {
            super(dr);
            cycleAtTick = -1;
            decomposerRecipeSuper = dr;
            chance = dr.getChance();
        }

        @Override
        public void cycleOutput(long tick)
        {
            if (cycleAtTick == -1)
            {
                cycleAtTick = tick + 20;
            }
            if (tick >= cycleAtTick)
            {
                cycleAtTick = tick + 20;
                ArrayList<ItemStack> outputsToShow;
                do
                {
                    outputsToShow = MinechemUtil.convertChemicalsIntoItemStacks(decomposerRecipeSuper.getOutput());
                }
                while (outputsToShow == null || outputsToShow.isEmpty());
                setOutputs(outputsToShow);
            }
        }

        /*@Override
         public float getChance()
         {
         return chance;
         }*/
    }

}
