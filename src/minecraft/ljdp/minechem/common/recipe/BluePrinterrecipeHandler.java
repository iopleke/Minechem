package ljdp.minechem.common.recipe;

import java.util.ArrayList;

import ljdp.minechem.api.core.Chemical;
import ljdp.minechem.api.recipe.BluePrinterRecipe;
import ljdp.minechem.api.util.Util;
import ljdp.minechem.common.utils.MinechemHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class BluePrinterrecipeHandler {

    public static BluePrinterrecipeHandler instance = new BluePrinterrecipeHandler();

    public BluePrinterrecipeHandler() {

    }

    public BluePrinterRecipe getRecipeFromOutput(ItemStack output) {
        for (BluePrinterRecipe recipe : BluePrinterRecipe.recipes) {
            if (Util.stacksAreSameKind(output, recipe.getOutput()))
                return recipe;
        }
        return null;
    }

    public BluePrinterRecipe getRecipeFromInput(ItemStack[] input) {
        for (BluePrinterRecipe recipe : BluePrinterRecipe.recipes) {
            if (itemStacksMatchesRecipe(input, recipe))
                return recipe;
        }
        return null;
    }

    public boolean itemStacksMatchesRecipe(ItemStack[] stacks, BluePrinterRecipe recipe) {
        return itemStacksMatchesRecipe(stacks, recipe, 1);
    }

    public boolean itemStacksMatchesRecipe(ItemStack[] stacks, BluePrinterRecipe recipe, int factor) {
        if (recipe.isShaped())
            return itemStacksMatchesShapedRecipe(stacks, recipe, factor);
        else
            return itemStacksMatchesShapelessRecipe(stacks, recipe, factor);
    }

    private boolean itemStacksMatchesShapelessRecipe(ItemStack[] stacks, BluePrinterRecipe recipe, int factor) {
        ArrayList<ItemStack> stacksList = new ArrayList<ItemStack>();
        ArrayList<ItemStack> shapelessRecipe = MinechemHelper.convertChemicalsIntoItemStacks(recipe.getShapelessRecipe());
        for (ItemStack itemstack : stacks) {
            if (itemstack != null)
                stacksList.add(itemstack.copy());
        }
        for (ItemStack itemstack : stacksList) {
            int ingredientSlot = getIngredientSlotThatMatchesStack(shapelessRecipe, itemstack, 1);
            if (ingredientSlot != -1)
                shapelessRecipe.remove(ingredientSlot);
            else
                return false;
        }
        return shapelessRecipe.size() == 0;
    }

    private boolean itemStacksMatchesShapedRecipe(ItemStack[] stacks, BluePrinterRecipe recipe, int factor) {
        Object[] chemicals = recipe.getShapedRecipe();
        for (int i = 0; i < chemicals.length; i++) {
            if (stacks[i] == null && chemicals[i] != null)
                return false;
            if (chemicals[i] == null && stacks[i] != null)
                return false;
            if (stacks[i] == null || chemicals[i] == null)
                continue;
            else
                return false;
        }
        return true;
    }

    private int getIngredientSlotThatMatchesStack(ArrayList<ItemStack> ingredients, ItemStack itemstack, int factor) {
        for (int slot = 0; slot < ingredients.size(); slot++) {
            ItemStack ingredientStack = ingredients.get(slot);
            if (ingredientStack != null && Util.stacksAreSameKind(itemstack, ingredientStack) && itemstack.stackSize == ingredientStack.stackSize)
                return slot;
        }
        return -1;
    }

    /**
     * Clears the crafting inventory.
     */
    public static boolean takeFromCraftingInventory(BluePrinterRecipe recipe, final IInventory inv) {
        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
            inv.setInventorySlotContents(slot, null);
        }
        return true;
    }
}
