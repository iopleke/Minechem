package minechem.common;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class UnbondingRecipe {
    protected ArrayList<ItemStack> outputStacks;
    protected ItemStack inputStack;

    public UnbondingRecipe() {
    }

    public UnbondingRecipe(ItemStack inputStack, ArrayList<ItemStack> outputStacks) {
        this.inputStack = inputStack;
        this.outputStacks = outputStacks;
    }

    public UnbondingRecipe(ArrayList<ItemStack> outputStacks) {
        this.outputStacks = outputStacks;
    }

    public ArrayList<ItemStack> getOutput() {
        ArrayList<ItemStack> output = new ArrayList<ItemStack>();
        for (ItemStack itemstack : outputStacks) {
            output.add(itemstack.copy());
        }
        return output;
    }

}
