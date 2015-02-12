package minechem.item.augment;

import java.util.HashMap;
import java.util.Map;
import minechem.Compendium;
import minechem.item.augment.augments.IAugment;
import minechem.item.prefab.WrapperItem;
import minechem.registry.AugmentRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class AugmentItem extends WrapperItem implements IAugmentItem
{
    public static final String augmentList = "augments";
    public static final String wrappedItem = "item";

    @Override
    public Map<IAugment, Integer> getAugments(ItemStack stack)
    {
        Map<IAugment, Integer> result = new HashMap<IAugment, Integer>();
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(augmentList, Compendium.NBTTags.tagList))
        {
            NBTTagList augments = stack.getTagCompound().getTagList(augmentList, Compendium.NBTTags.tagString);
            for (int i = 0; i < augments.tagCount(); i++)
            {
                String key = augments.getStringTagAt(i);
                result.put(AugmentRegistry.getAugment(key), stack.getTagCompound().getInteger(key));
            }
        }
        return result;
    }

    /**
     *
     * @param item
     * @return int modifier to EntityItem lifespan (base 6000)
     */
    public int getEntityLifespanModifier(ItemStack item)
    {
        int lifespan = 0;
        for (Map.Entry<IAugment, Integer> entry : getAugments(item).entrySet())
        {
            lifespan += entry.getKey().setLevel(entry.getValue()).getEntityLifespanModifier();
        }
        return lifespan;
    }

    @Override
    public ItemStack getWrappedItemStack(ItemStack wrapper)
    {
        if (wrapper.hasTagCompound())
        {
            return ItemStack.loadItemStackFromNBT(wrapper.getTagCompound().getCompoundTag(wrappedItem));
        }
        return null;
    }

    @Override
    public boolean hasAugment(ItemStack item, IAugment augment)
    {
        return item.hasTagCompound() && item.getTagCompound().hasKey(augment.getKey(), Compendium.NBTTags.tagByte);
    }

    @Override
    public boolean isWrappable(ItemStack stack)
    {
        return stack.getItem().isItemTool(stack) && !(stack.getItem() instanceof WrapperItem);
    }

    @Override
    public boolean removeAugment(ItemStack item, IAugment augment)
    {
        if (!item.hasTagCompound())
        {
            return false;
        }
        NBTTagCompound tagCompound = item.getTagCompound();
        String augmentKey = augment.getKey();
        if (!tagCompound.hasKey(augmentKey))
        {
            return false;
        }
        tagCompound.removeTag(augmentKey);
        NBTTagList augments = tagCompound.getTagList(augmentList, Compendium.NBTTags.tagString);
        for (int i = 0; i < augments.tagCount(); i++)
        {
            if (augments.getStringTagAt(i).equals(augmentKey))
            {
                augments.removeTag(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void setAugment(ItemStack item, IAugment augment, int level)
    {
        if (!item.hasTagCompound())
        {
            item.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound tagCompound = item.getTagCompound();
        String augmentKey = augment.getKey();
        if (tagCompound.hasKey(augmentKey))
        {
            tagCompound.setByte(augmentKey, (byte) level);
        } else
        {
            NBTTagList augments = tagCompound.getTagList(augmentList, Compendium.NBTTags.tagString);
            augments.appendTag(new NBTTagString(augmentKey));
            tagCompound.setByte(augmentKey, (byte) level);
            tagCompound.setTag(augmentList, augments);
        }
    }

    @Override
    public void setWrappedItemStack(ItemStack wrapper, ItemStack stack)
    {
        if (!wrapper.hasTagCompound())
        {
            wrapper.setTagCompound(new NBTTagCompound());
        }
        wrapper.getTagCompound().setTag(wrappedItem, stack.writeToNBT(new NBTTagCompound()));
    }
}
