package minechem.item.prefab;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Set;

public abstract class WrapperItem extends BasicItem
{

    public abstract ItemStack getWrappedItemStack(ItemStack wrapper);

    public abstract void setWrappedItemStack(ItemStack wrapper, ItemStack stack);

    public Item getWrappedItem(ItemStack wrapper)
    {
        return getWrappedItemStack(wrapper).getItem();
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLivingBase)
    {
        return getWrappedItem(stack).onBlockDestroyed(getWrappedItemStack(stack), world, block, x, y, z, entityLivingBase);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int X, int Y, int Z, EntityPlayer player)
    {
        return getWrappedItem(stack).onBlockStartBreak(getWrappedItemStack(stack), X, Y, Z, player);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
    {
        return getWrappedItem(item).onDroppedByPlayer(getWrappedItemStack(item), player);
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        return getWrappedItem(stack).onEntitySwing(entityLiving, getWrappedItemStack(stack));
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        return getWrappedItem(stack).onItemUse(getWrappedItemStack(stack), player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        return getWrappedItem(stack).onItemUse(getWrappedItemStack(stack), player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        return getWrappedItem(stack).onLeftClickEntity(getWrappedItemStack(stack), player, entity);
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        return getWrappedItem(stack).onEaten(getWrappedItemStack(stack), world, player);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        return getWrappedItem(stack).onItemRightClick(getWrappedItemStack(stack), world, player);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        getWrappedItem(itemStack).onArmorTick(world, player, getWrappedItemStack(itemStack));
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int itemInUseCount)
    {
        getWrappedItem(stack).onPlayerStoppedUsing(getWrappedItemStack(stack), world, player, itemInUseCount);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        getWrappedItem(stack).onCreated(getWrappedItemStack(stack), world, player);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean bool)
    {
        getWrappedItem(stack).onUpdate(getWrappedItemStack(stack), world, entity, slot, bool);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
        getWrappedItem(stack).onUsingTick(getWrappedItemStack(stack), player, count);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return getWrappedItem(stack).isBookEnchantable(getWrappedItemStack(stack), book);
    }

    @Override
    public boolean isBeaconPayment(ItemStack stack)
    {
        return getWrappedItem(stack).isBeaconPayment(getWrappedItemStack(stack));
    }

    @Override
    public boolean isDamaged(ItemStack stack)
    {
        return getWrappedItem(stack).isDamaged(getWrappedItemStack(stack));
    }

    @Override
    public boolean isItemTool(ItemStack stack)
    {
        return getWrappedItem(stack).isItemTool(getWrappedItemStack(stack));
    }

    @Override
    public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
    {
        return getWrappedItem(stack).isValidArmor(getWrappedItemStack(stack), armorType, entity);
    }

    @Override
    public boolean isPotionIngredient(ItemStack stack)
    {
        return getWrappedItem(stack).isPotionIngredient(getWrappedItemStack(stack));
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return getToolClasses(getWrappedItemStack(stack));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return getWrappedItemStack(stack).getRarity();
    }

    @Override
    public int getItemEnchantability(ItemStack stack)
    {
        return getWrappedItem(stack).getItemEnchantability(getWrappedItemStack(stack));
    }

    @Override
    public float getDigSpeed(ItemStack itemstack, Block block, int metadata)
    {
        return getWrappedItem(itemstack).getDigSpeed(getWrappedItemStack(itemstack), block, metadata);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return getWrappedItem(stack).getItemUseAction(getWrappedItemStack(stack));
    }

    @Override
    public int getMaxDamage(ItemStack stack)
    {
        return getWrappedItem(stack).getMaxDamage(getWrappedItemStack(stack));
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return getWrappedItem(stack).getDurabilityForDisplay(getWrappedItemStack(stack));
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world)
    {
        return getWrappedItem(itemStack).getEntityLifespan(getWrappedItemStack(itemStack), world);
    }

    @Override
    public int getDamage(ItemStack stack)
    {
        return getWrappedItem(stack).getDamage(getWrappedItemStack(stack));
    }

    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return getWrappedItem(stack).getItemStackLimit(getWrappedItemStack(stack));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return getWrappedItem(stack).getUnlocalizedName(getWrappedItemStack(stack));
    }

    @Override
    public IIcon getIconIndex(ItemStack stack)
    {
        return getWrappedItem(stack).getIconIndex(getWrappedItemStack(stack));
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        return getWrappedItem(stack).getIcon(getWrappedItemStack(stack), pass);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        return getWrappedItem(stack).getIcon(getWrappedItemStack(stack), renderPass, player, usingItem, useRemaining);
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int p_82790_2_)
    {
        return getWrappedItem(stack).getColorFromItemStack(getWrappedItemStack(stack), p_82790_2_);
    }

    @Override
    public String getPotionEffect(ItemStack stack)
    {
        return getWrappedItem(stack).getPotionEffect(getWrappedItemStack(stack));
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass)
    {
        return getWrappedItem(stack).getHarvestLevel(getWrappedItemStack(stack), toolClass);
    }

    @Override
    public boolean getIsRepairable(ItemStack stack, ItemStack stack2)
    {
        return getWrappedItem(stack).getIsRepairable(getWrappedItemStack(stack), stack2);
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack stack)
    {
        return getWrappedItem(stack).getAttributeModifiers(getWrappedItemStack(stack));
    }

    @Override
    public FontRenderer getFontRenderer(ItemStack stack)
    {
        return getWrappedItem(stack).getFontRenderer(getWrappedItemStack(stack));
    }

    @Override
    public float getSmeltingExperience(ItemStack item)
    {
        return getWrappedItem(item).getSmeltingExperience(getWrappedItemStack(item));
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return getWrappedItem(stack).getMaxItemUseDuration(getWrappedItemStack(stack));
    }

    @Override
    public void setDamage(ItemStack stack, int damage)
    {
        getWrappedItem(stack).setDamage(getWrappedItemStack(stack), damage);
    }
}
