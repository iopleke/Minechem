package minechem.collections.strategy;

import gnu.trove.strategy.HashingStrategy;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class FlatItemStackHashingStrategy implements HashingStrategy<ItemStack>
{
    @Override
    public int computeHashCode(ItemStack stack)
    {
        return stack.getItem().hashCode() ^ (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE ? 0 : stack.getItemDamage()) ^ (stack.hasTagCompound() ? stack.stackTagCompound.hashCode() : 0);
    }

    @Override
    public boolean equals(ItemStack stack1, ItemStack stack2)
    {
        return stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == stack2.getItemDamage() || stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }
}
