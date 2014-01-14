package pixlepix.minechem.api.recipe;

import java.util.ArrayList;

import pixlepix.minechem.api.core.Chemical;

import net.minecraft.item.ItemStack;

public class DecomposerRecipe {

    public static ArrayList<DecomposerRecipe> recipes = new ArrayList<DecomposerRecipe>();

    ItemStack input;
    public ArrayList<Chemical> output = new ArrayList<Chemical>();

    public static DecomposerRecipe add(DecomposerRecipe recipe) {
        recipes.add(recipe);
        return recipe;
    }

    public DecomposerRecipe(ItemStack input, Chemical... chemicals) {
        this(chemicals);
        this.input = input;
    }

    public DecomposerRecipe(Chemical... chemicals) {
        for (Chemical chemical : chemicals)
            this.output.add(chemical);
    }

    public ItemStack getInput() {
        return this.input;
    }

    public ArrayList<Chemical> getOutput() {
        return this.output;
    }

    public ArrayList<Chemical> getOutputRaw() {
        return this.output;
    }
}
