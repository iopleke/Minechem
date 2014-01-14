package pixlepix.minechem.client.nei;

import java.util.ArrayList;
import java.util.List;

import pixlepix.minechem.api.core.Chemical;
import pixlepix.minechem.api.recipe.DecomposerRecipe;
import pixlepix.minechem.api.util.Util;
import pixlepix.minechem.common.recipe.DecomposerRecipeHandler;
import pixlepix.minechem.common.utils.ConstantValue;
import pixlepix.minechem.common.utils.MinechemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class DecomposerNEIRecipeHandler extends TemplateRecipeHandler {

    private static final String MINECHEM_DECOMPOSER_RECIPES_ID =
            "minechem.decomposer";
    
    // TODO: The Gui's class itself should really expose this.
    private ResourceLocation texture = new ResourceLocation(
            ConstantValue.MOD_ID,ConstantValue.DECOMPOSER_GUI);
    
    // GUI slot offsets, in GUI-relative pixel values.
    private static final int INPUT_X_OFS = 75;
    private static final int INPUT_Y_OFS = 5;
    private static final int OUTPUT_X_OFS = 2;
    private static final int OUTPUT_X_SCALE = 18;
    private static final int OUTPUT_Y_OFS = 51;

    @Override
    public String getRecipeName() {
        return "Chemical Decomposer";
    }

    @Override
    public String getGuiTexture() {
        return texture.toString();
    }
    
    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(MINECHEM_DECOMPOSER_RECIPES_ID)) {
            // Add all decomposer recipes to local arecipes array.
            for (DecomposerRecipe dr: DecomposerRecipe.recipes) {
                registerDecomposerRecipe(dr);
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }
    
    @Override
    public void loadCraftingRecipes(ItemStack result) {
        // If the result isn't a Minechem chemical, no decomposer recipe
        // can yield it.
        if (!Util.isStackAChemical(result)) {
            return;
        }
        // Add all decomposer recipes that can yield the result.
        Chemical resultChemical = MinechemHelper.itemStackToChemical(result);
        if (resultChemical == null) {
            return;
        }
        for (DecomposerRecipe dr: DecomposerRecipe.recipes) {
            ArrayList<Chemical> rawOutputs = dr.getOutputRaw();
            if (rawOutputs != null) {
                for (Chemical c: rawOutputs) {
                    if (resultChemical.sameAs(c)) {
                        registerDecomposerRecipe(dr);
                        break;
                    }
                }
            }
        }
    }
    
    @Override 
    public void loadUsageRecipes(ItemStack ingredient) {
        // Add all decomposer recipes that take the ingredient as an input.
        DecomposerRecipe dr = DecomposerRecipeHandler.instance.getRecipe(ingredient);
        if (dr != null) {
            registerDecomposerRecipe(dr);
        }
    }
    
    /**
     * Registers a decomposer recipe with NEI. Anything that adds a new
     * decomposer recipe after startup should call this to have the recipe
     * reflected in NEI.
     * 
     * @param dr Decomposer recipe to add.
     */
    public void registerDecomposerRecipe(DecomposerRecipe dr) {
        if (dr == null) {
            return;
        }
        CachedDecomposerRecipe cdr = new CachedDecomposerRecipe(dr.getInput(), dr);
        arecipes.add(cdr);
    }
    
    public class CachedDecomposerRecipe extends TemplateRecipeHandler.CachedRecipe {
        
        private PositionedStack input;
        // The first item in the multi-output decomposer recipe.
        private PositionedStack output1;
        // Any other items in the multi-output decomposer recipe.
        private List<PositionedStack> otherOutputs;
        
        public CachedDecomposerRecipe(ItemStack inputItem, ItemStack resultItem) {
            super();
            input = new PositionedStack(inputItem, INPUT_X_OFS, INPUT_Y_OFS);
            output1 = new PositionedStack(resultItem, OUTPUT_X_OFS, OUTPUT_Y_OFS);
            otherOutputs = null;
        }
        
        public CachedDecomposerRecipe(ItemStack input, DecomposerRecipe dr) {
            super();
            ArrayList<ItemStack> outputs = 
                    MinechemHelper.convertChemicalsIntoItemStacks(dr.getOutputRaw());
            this.input = new PositionedStack(input, INPUT_X_OFS, INPUT_Y_OFS);
            output1 = new PositionedStack(outputs.get(0), OUTPUT_X_OFS, OUTPUT_Y_OFS);
            otherOutputs = new ArrayList<PositionedStack>();
            if (outputs.size() > 1) {
                for (int idx = 1; idx < outputs.size(); idx++) {
                    ItemStack o = outputs.get(idx);
                    otherOutputs.add(new PositionedStack(o,
                            OUTPUT_X_OFS + OUTPUT_X_SCALE * idx, OUTPUT_Y_OFS));
                }
            }
        }
        
        @Override
        public PositionedStack getIngredient() {
            return input;
        }

        @Override
        public PositionedStack getResult() {
            return output1;
        }
        
        @Override
        public List<PositionedStack> getOtherStacks() {
            return otherOutputs;
        }
        
    }

}
