package minechem.minetweaker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import minechem.potion.PotionChemical;
import minechem.tileentity.synthesis.SynthesisRecipe;
import minechem.tileentity.synthesis.SynthesisRecipeHandler;
import minechem.utils.InputHelper;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.minechem.Synthesiser")
public class Synthesiser
{

    @ZenMethod
    public static void addRecipe(IIngredient[] inputs, IIngredient outputStack, boolean shaped, int energy)
    {
        boolean someValue = false;
        PotionChemical[] inputFixed = new PotionChemical[9];
        if (shaped)
        {
            for (int i = 0; i < inputFixed.length && i < inputs.length; i++)
            {
                if (inputs[i] != null)
                {
                    inputFixed[i] = InputHelper.getChemical(inputs[i]);
                    if (inputFixed[i] != null) someValue = true;
                }
            }
        }
        else
        {
            List<PotionChemical> input = InputHelper.getChemicals(inputs);
            inputFixed = Arrays.copyOf(input.toArray(new PotionChemical[0]),9);
            someValue = !input.isEmpty();
        }
        if (someValue)
        {
            ItemStack output = InputHelper.getInput(outputStack);
            if (output != null)
            {
                MineTweakerAPI.apply(new AddRecipeAction(output, shaped, energy, inputFixed));
            }
        }
    }

    @ZenMethod
    public static void removeRecipe(IIngredient input)
    {
        ArrayList<ItemStack> toRemove = InputHelper.getInputs(input);
        for (ItemStack remove : toRemove)
        {
            SynthesisRecipe recipe = SynthesisRecipeHandler.instance.getRecipeFromOutput(remove);
            if (recipe != null)
            {
                MineTweakerAPI.apply(new RemoveRecipeAction(recipe));
            }
        }
    }

    // ######################
    // ### Action classes ###
    // ######################
    private static class AddRecipeAction implements IUndoableAction
    {
        private final SynthesisRecipe recipe;

        public AddRecipeAction(ItemStack output, boolean shaped, int energy, PotionChemical[] input)
        {
            recipe = new SynthesisRecipe(output, shaped, energy, input);
        }

        @Override
        public void apply()
        {
            SynthesisRecipe.add(recipe);
        }

        @Override
        public boolean canUndo()
        {
            return true;
        }

        @Override
        public void undo()
        {
            SynthesisRecipe.remove(recipe.getOutput());
        }

        @Override
        public String describe()
        {
            return "Adding synthesiser recipe for " + recipe.getOutput().getDisplayName();
        }

        @Override
        public String describeUndo()
        {
            return "Removing synthesiser recipe for " + recipe.getOutput().getDisplayName();
        }

        @Override
        public Object getOverrideKey()
        {
            return null;
        }
    }

    private static class RemoveRecipeAction implements IUndoableAction
    {
        private final SynthesisRecipe recipe;

        public RemoveRecipeAction(SynthesisRecipe recipe)
        {
            this.recipe = recipe;
        }

        @Override
        public void apply()
        {
            SynthesisRecipe.remove(recipe.getOutput());
        }

        @Override
        public boolean canUndo()
        {
            return true;
        }

        @Override
        public void undo()
        {
            SynthesisRecipe.add(recipe);
        }

        @Override
        public String describe()
        {
            return "Removing synthesis recipe for " + recipe.getOutput().getDisplayName();
        }

        @Override
        public String describeUndo()
        {
            return "Restoring synthesis recipe for " + recipe.getOutput().getDisplayName();
        }

        @Override
        public Object getOverrideKey()
        {
            return null;
        }
    }
}
