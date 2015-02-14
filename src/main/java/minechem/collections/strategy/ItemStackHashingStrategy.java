package minechem.collections.strategy;

import gnu.trove.strategy.HashingStrategy;
import net.minecraft.item.ItemStack;

public class ItemStackHashingStrategy implements HashingStrategy<ItemStack>
{
    @Override
    public int computeHashCode(ItemStack stack)
    {
        return stack.getItem().hashCode() ^ stack.getItemDamage() ^ (stack.stackSize << 16) ^ (stack.hasTagCompound() ? stack.stackTagCompound.hashCode() : 0);
    }

    @Override
    public boolean equals(ItemStack stack1, ItemStack stack2)
    {
        return ItemStack.areItemStacksEqual(stack1, stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }
}
