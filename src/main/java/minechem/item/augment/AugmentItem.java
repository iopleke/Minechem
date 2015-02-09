package minechem.item.augment;

import minechem.item.prefab.WrapperItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class AugmentItem extends WrapperItem
{
    public static final String wrappedItem = "item";

    @Override
    public ItemStack getWrappedItemStack(ItemStack wrapper)
    {
        if (!wrapper.hasTagCompound()) return null;
        return ItemStack.loadItemStackFromNBT(wrapper.getTagCompound().getCompoundTag(wrappedItem));
    }

    @Override
    public void setWrappedItemStack(ItemStack wrapper, ItemStack stack)
    {
        if (!wrapper.hasTagCompound()) wrapper.setTagCompound(new NBTTagCompound());
        wrapper.getTagCompound().setTag(wrappedItem,stack.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public boolean isWrappable(ItemStack stack)
    {
        return stack.getItem().isItemTool(stack) && !(stack.getItem() instanceof WrapperItem);
    }
}
