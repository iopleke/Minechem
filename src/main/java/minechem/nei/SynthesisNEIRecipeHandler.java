package minechem.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import minechem.ModMinechem;
import minechem.potion.Chemical;
import minechem.tileentity.synthesis.SynthesisGui;
import minechem.tileentity.synthesis.SynthesisRecipe;
import minechem.utils.MinechemHelper;
import minechem.utils.Reference;
import minechem.utils.Compare;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class SynthesisNEIRecipeHandler extends TemplateRecipeHandler
{

    private static final String MINECHEM_SYNTHESIS_RECIPES_ID = "minechem.synthesis";

    // TODO: The Gui's class itself should really expose this.
    private ResourceLocation texture = new ResourceLocation(ModMinechem.ID, Reference.SYNTHESIS_GUI);

    // GUI slot offsets, in GUI-relative pixel values.
    private static final int INPUT_X_OFS = 57;
    private static final int INPUT_Y_OFS = 7;
    private static final int INPUT_X_SCALE = 18;
    private static final int INPUT_Y_SCALE = 18;
    private static final int OUTPUT_X_OFS = 129;
    private static final int OUTPUT_Y_OFS = 7;

    @Override
    public String getRecipeName()
    {
        return MinechemHelper.getLocalString("gui.title.synthesis");
    }

    @Override
    public String getGuiTexture()
    {
        return texture.toString();
    }

    @Override
    public void loadTransferRects()
    {
        // Use the right-arrow pointing at the output.
        transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(OUTPUT_X_OFS - 16, OUTPUT_Y_OFS, 14, 16), MINECHEM_SYNTHESIS_RECIPES_ID, new Object[0]));
    }

    @Override
    public Class getGuiClass()
    {
        return SynthesisGui.class;
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(MINECHEM_SYNTHESIS_RECIPES_ID))
        {
            // Add all synthesis recipes to local arecipes array.
            for (SynthesisRecipe sr : SynthesisRecipe.recipes)
            {
                registerSynthesisRecipe(sr);
            }
        }
        else
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        // Add all synthesis recipes that can yield the result.
        for (SynthesisRecipe sr : SynthesisRecipe.recipes)
        {
            ItemStack recipeOutput = sr.getOutput();
            if (NEIServerUtils.areStacksSameTypeCrafting(result, recipeOutput))
            {
                registerSynthesisRecipe(sr);
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        // If ingredient isn't a Minechem chemical, no synthesis recipe can
        // use it.
        if (!Compare.isStackAChemical(ingredient))
        {
            return;
        }
        Chemical ingredientChemical = MinechemHelper.itemStackToChemical(ingredient);
        if (ingredientChemical == null)
        {
            return;
        }
        // Add all synthesis recipes that take the ingredient as an input.
        for (SynthesisRecipe sr : SynthesisRecipe.recipes)
        {
            if (sr.isShaped())
            {
                Chemical[] recipeInputs = sr.getShapedRecipe();
                for (Chemical c : recipeInputs)
                {
                    if (ingredientChemical.sameAs(c))
                    {
                        registerSynthesisRecipe(sr);
                        break;
                    }
                }
            }
            else
            {
                for (Object o : sr.getShapelessRecipe())
                {
                    Chemical c = (Chemical) o;
                    if (ingredientChemical.sameAs(c))
                    {
                        registerSynthesisRecipe(sr);
                        break;
                    }
                }
            }
        }
    }

    /** Registers a synthesis recipe with NEI. Anything that adds a new synthesis recipe after startup should call this to have the recipe reflected in NEI.
     * 
     * @param sr Synthesis recipe to add. */
    public void registerSynthesisRecipe(SynthesisRecipe sr)
    {
        if (sr == null)
        {
            return;
        }
        CachedSynthesisRecipe csr = new CachedSynthesisRecipe(sr);
        arecipes.add(csr);
    }

    public class CachedSynthesisRecipe extends TemplateRecipeHandler.CachedRecipe
    {

        // The list of inputs.
        private List<PositionedStack> inputs;
        // Recipe's output.
        private PositionedStack output;

        public CachedSynthesisRecipe(SynthesisRecipe sr)
        {
            super();
            inputs = new ArrayList<PositionedStack>();
            if (sr.isShaped())
            {
                // Input elements go into specified positions.
                Chemical[] inputChemicals = sr.getShapedRecipe();
                int xSlot = 0;
                int ySlot = 0;
                for (Chemical c : inputChemicals)
                {
                    if (c != null)
                    {
                        ItemStack inputItem = MinechemHelper.chemicalToItemStack(c, c.amount);
                        inputs.add(new PositionedStack(inputItem, INPUT_X_OFS + xSlot * INPUT_X_SCALE, INPUT_Y_OFS + ySlot * INPUT_Y_SCALE));
                    }
                    xSlot++;
                    if (xSlot > 2)
                    {
                        xSlot = 0;
                        ySlot++;
                    }
                }
            }
            else
            {
                // Just put input elements into first slots, starting at
                // upper left.
                ArrayList inputChemicals = sr.getShapelessRecipe();
                int xSlot = 0;
                int ySlot = 0;
                for (Object o : inputChemicals)
                {
                    Chemical c = (Chemical) o;
                    ItemStack inputItem = MinechemHelper.chemicalToItemStack(c, c.amount);
                    inputs.add(new PositionedStack(inputItem, INPUT_X_OFS + xSlot * INPUT_X_SCALE, INPUT_Y_OFS + ySlot * INPUT_Y_SCALE));
                    xSlot++;
                    if (xSlot > 2)
                    {
                        xSlot = 0;
                        ySlot++;
                    }
                }
            }
            output = new PositionedStack(sr.getOutput(), OUTPUT_X_OFS, OUTPUT_Y_OFS);

            // TODO: Store energy cost?
        }

        @Override
        public PositionedStack getIngredient()
        {
            return null;
        }

        @Override
        public List<PositionedStack> getIngredients()
        {
            return inputs;
        }

        @Override
        public PositionedStack getResult()
        {
            return output;
        }
    }

}