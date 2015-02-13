package minechem.item.augment;

import com.google.common.collect.Multimap;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import minechem.Compendium;
import minechem.item.augment.augments.IAugment;
import minechem.item.prefab.WrapperItem;
import minechem.registry.AugmentRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;

public class AugmentItem extends WrapperItem implements IAugmentItem
{
    public static final String augmentList = "augments";
    public static final String wrappedItem = "item";
    public static final Random rand = new Random(System.currentTimeMillis());

    @Override
    public boolean isWrappable(ItemStack stack)
    {
        return stack.getItem().isItemTool(stack) && !(stack.getItem() instanceof WrapperItem);
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
    public void setWrappedItemStack(ItemStack wrapper, ItemStack stack)
    {
        if (!wrapper.hasTagCompound())
        {
            wrapper.setTagCompound(new NBTTagCompound());
        }
        wrapper.getTagCompound().setTag(wrappedItem, stack.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public boolean hasAugment(ItemStack item, IAugment augment)
    {
        return item.hasTagCompound() && item.getTagCompound().hasKey(augment.getKey(), Compendium.NBTTags.tagByte);
    }

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
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLivingBase)
    {
        boolean result = super.onBlockDestroyed(stack, world, block, x, y, z, entityLivingBase);
        if (result)
        {
            return true;
        }
        for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet())
        {
            result |= entry.getKey().onBlockDestroyed(stack, world, block, x, y, z, entityLivingBase, entry.getValue());
        }
        return result;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
    {
        boolean result = super.onDroppedByPlayer(item, player);
        if (result)
        {
            return true;
        }
        for (Map.Entry<IAugment, Integer> entry : getAugments(item).entrySet())
        {
            result |= entry.getKey().onDroppedByPlayer(item, player, entry.getValue());
        }
        return result;
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
        boolean result = super.onEntityItemUpdate(entityItem);
        for (Map.Entry<IAugment, Integer> entry : getAugments(entityItem.getEntityItem()).entrySet())
        {
            result |= entry.getKey().onEntityItemUpdate(entityItem, entry.getValue());
        }
        return result;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        boolean result = super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
        if (result)
        {
            return true;
        }
        for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet())
        {
            result |= entry.getKey().onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ, entry.getValue());
        }
        return result;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        boolean result = super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
        if (result)
        {
            return true;
        }
        for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet())
        {
            result |= entry.getKey().onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ, entry.getValue());
        }
        return result;
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        boolean result = super.onEntitySwing(entityLiving, stack);
        if (result)
        {
            return true;
        }
        for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet())
        {
            result |= entry.getKey().onEntitySwing(entityLiving, stack, entry.getValue());
        }
        return result;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entityLivingBase)
    {
        boolean result = super.itemInteractionForEntity(stack, player, entityLivingBase);
        for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet())
        {
            result |= entry.getKey().itemInteractionForEntity(stack, player, entityLivingBase, entry.getValue());
        }
        return result;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        boolean result = super.onLeftClickEntity(stack, player, entity);
        if (result)
        {
            return true;
        }
        for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet())
        {
            result |= entry.getKey().onLeftClickEntity(stack, player, entity, entry.getValue());
        }
        return result;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        ItemStack result = super.onItemRightClick(stack, world, player);
        for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet())
        {
            result = entry.getKey().onItemRightClick(stack, world, player, entry.getValue());
        }
        return result;
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        ItemStack result = super.onEaten(stack, world, player);
        for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet())
        {
            result = entry.getKey().onEaten(stack, world, player, entry.getValue());
        }
        return result;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean bool)
    {
        super.onUpdate(stack, world, entity, slot, bool);
        for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet())
        {
            entry.getKey().onUpdate(stack, world, entity, slot, bool, entry.getValue());
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
        super.onUsingTick(stack, player, count);
        for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet())
        {
            entry.getKey().onUsingTick(stack, player, count, entry.getValue());
        }
    }

    @Override
    public float getDigSpeed(ItemStack itemstack, Block block, int metadata)
    {
        float result = super.getDigSpeed(itemstack, block, metadata);
        for (Map.Entry<IAugment, Integer> entry : getAugments(itemstack).entrySet())
        {
            result = entry.getKey().getModifiedDigSpeed(result, block, metadata, entry.getValue());
        }
        return result;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass)
    {
        int result = super.getHarvestLevel(stack, toolClass);
        for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet())
        {
            result += entry.getKey().getHarvestLevelModifier(toolClass, entry.getValue());
        }
        return result;
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack stack)
    {
        Multimap result = super.getAttributeModifiers(stack);
        for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet())
        {
            result.putAll(entry.getKey().getAttributeModifiers(entry.getValue()));
        }
        return result;
    }

    @Override
    public void setDamage(ItemStack stack, int damage)
    {
        for (Map.Entry<IAugment, Integer> entry : getAugments(stack).entrySet())
        {
            if (rand.nextFloat() < entry.getKey().setDamageChance(entry.getValue()))
            {
                return;
            }
        }
        super.setDamage(stack, damage);
    }

    @Override
    public int getEntityLifespan(ItemStack item, World world)
    {
        int lifespan = super.getEntityLifespan(item, world);
        for (Map.Entry<IAugment, Integer> entry : getAugments(item).entrySet())
        {
            lifespan += entry.getKey().getEntityLifespanModifier(entry.getValue());
        }
        return lifespan;
    }
}
