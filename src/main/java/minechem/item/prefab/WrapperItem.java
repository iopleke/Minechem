package minechem.item.prefab;

import com.google.common.collect.Multimap;

import java.util.Set;

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

public abstract class WrapperItem extends BasicItem
{
    public WrapperItem(String name)
    {
        super(name);
        this.setMaxDamage(Short.MAX_VALUE);
    }

    /**
     * @param wrapper Wrapped ItemStack
     * @return ItemStack contained
     */
    public abstract ItemStack getWrappedItemStack(ItemStack wrapper);

    /**
     * @param wrapper Wrapped ItemStack
     * @return Item contained
     */
    public Item getWrappedItem(ItemStack wrapper)
    {
        return getWrappedItemStack(wrapper).getItem();
    }

    /**
     * @param stack ItemStack to be wrapped
     * @return true if ItemStack is valid
     */
    public abstract boolean isWrappable(ItemStack stack);

    /**
     * @param wrapper ItemStack
     * @param stack   ItemStack to be wrapped
     */
    public abstract void setWrappedItemStack(ItemStack wrapper, ItemStack stack);


    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped != null) return wrapped.getItem().getItemStackDisplayName(wrapped);
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getAttributeModifiers(stack);
        return wrapped.getItem().getAttributeModifiers(wrapped);
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int colour)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getColorFromItemStack(stack, colour);
        return wrapped.getItem().getColorFromItemStack(wrapped, colour);
    }

    @Override
    public int getDamage(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getDamage(stack);
        return wrapped.getItem().getDamage(wrapped);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int metadata)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getDigSpeed(stack, block, metadata);
        return wrapped.getItem().getDigSpeed(wrapped, block, metadata);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getDurabilityForDisplay(stack);
        return wrapped.getItem().getDurabilityForDisplay(wrapped);
    }

    @Override
    public int getEntityLifespan(ItemStack stack, World world)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getEntityLifespan(stack, world);
        return wrapped.getItem().getEntityLifespan(wrapped, world);
    }

    @Override
    public FontRenderer getFontRenderer(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getFontRenderer(stack);
        return wrapped.getItem().getFontRenderer(wrapped);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getHarvestLevel(stack, toolClass);
        return wrapped.getItem().getHarvestLevel(wrapped, toolClass);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getIcon(stack, pass);
        return wrapped.getItem().getIcon(wrapped, pass);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getIcon(stack, renderPass, player, usingItem, useRemaining);
        return wrapped.getItem().getIcon(wrapped, renderPass, player, usingItem, useRemaining);
    }

    @Override
    public IIcon getIconIndex(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getIconIndex(stack);
        return wrapped.getItem().getIconIndex(wrapped);
    }

    @Override
    public boolean getIsRepairable(ItemStack stack, ItemStack stack2)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getIsRepairable(stack, stack2);
        return wrapped.getItem().getIsRepairable(wrapped, stack2);
    }

    @Override
    public int getItemEnchantability(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getItemEnchantability(stack);
        return wrapped.getItem().getItemEnchantability(wrapped);
    }

    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getItemStackLimit(stack);
        return wrapped.getItem().getItemStackLimit(wrapped);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getItemUseAction(stack);
        return wrapped.getItem().getItemUseAction(wrapped);
    }

    @Override
    public int getMaxDamage(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getMaxDamage(stack);
        return wrapped.getItem().getMaxDamage(wrapped);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getMaxItemUseDuration(stack);
        return wrapped.getItem().getMaxItemUseDuration(wrapped);
    }

    @Override
    public String getPotionEffect(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getPotionEffect(stack);
        return wrapped.getItem().getPotionEffect(wrapped);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getRarity(stack);
        return wrapped.getRarity();
    }

    @Override
    public float getSmeltingExperience(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getSmeltingExperience(stack);
        return wrapped.getItem().getSmeltingExperience(wrapped);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getToolClasses(stack);
        return getToolClasses(wrapped);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.getUnlocalizedName(stack);
        return wrapped.getItem().getUnlocalizedName(wrapped);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase user)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.hitEntity(stack, target, user);
        boolean result = wrapped.getItem().hitEntity(wrapped, target, user);
        setDamage(stack, wrapped.getItemDamage());
        if (stack.stackSize < 1 && user instanceof EntityPlayer) ((EntityPlayer)user).destroyCurrentEquippedItem();
        return result;
    }

    @Override
    public boolean isBeaconPayment(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.isBeaconPayment(stack);
        return wrapped.getItem().isBeaconPayment(wrapped);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.isBookEnchantable(stack, book);
        return wrapped.getItem().isBookEnchantable(wrapped, book);
    }

    @Override
    public boolean isDamaged(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.isDamaged(stack);
        return wrapped.getItem().isDamaged(wrapped);
    }

    @Override
    public boolean isItemTool(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.isItemTool(stack);
        return wrapped.getItem().isItemTool(wrapped);
    }

    @Override
    public boolean isPotionIngredient(ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.isPotionIngredient(stack);
        return wrapped.getItem().isPotionIngredient(wrapped);
    }

    @Override
    public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.isValidArmor(stack, armorType, entity);
        return wrapped.getItem().isValidArmor(wrapped, armorType, entity);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entityLivingBase)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.itemInteractionForEntity(stack, player, entityLivingBase);
        boolean result = wrapped.getItem().itemInteractionForEntity(wrapped, player, entityLivingBase);
        if (stack.stackSize < 1 && entityLivingBase instanceof EntityPlayer)
            ((EntityPlayer)entityLivingBase).destroyCurrentEquippedItem();
        return result;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) onArmorTick(world, player, stack);
        else wrapped.getItem().onArmorTick(world, player, wrapped);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLivingBase)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.onBlockDestroyed(stack, world, block, x, y, z, entityLivingBase);
        boolean result = wrapped.getItem().onBlockDestroyed(wrapped, world, block, x, y, z, entityLivingBase);
        setDamage(stack, wrapped.getItemDamage());
        if (stack.stackSize < 1 && entityLivingBase instanceof EntityPlayer)
            ((EntityPlayer)entityLivingBase).destroyCurrentEquippedItem();
        return result;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int X, int Y, int Z, EntityPlayer player)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.onBlockStartBreak(stack, X, Y, Z, player);
        boolean result = wrapped.getItem().onBlockStartBreak(wrapped, X, Y, Z, player);
        setDamage(stack, wrapped.getItemDamage());
        if (stack.stackSize < 1) player.destroyCurrentEquippedItem();
        return result;
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) onCreated(stack, world, player);
        else wrapped.getItem().onCreated(wrapped, world, player);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.onDroppedByPlayer(stack, player);
        return wrapped.getItem().onDroppedByPlayer(wrapped, player);
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.onEaten(stack, world, player);
        return wrapped.getItem().onEaten(wrapped, world, player);
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.onEntitySwing(entityLiving, stack);
        return wrapped.getItem().onEntitySwing(entityLiving, wrapped);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.onItemRightClick(stack, world, player);
        wrapped = wrapped.getItem().onItemRightClick(wrapped, world, player);
        if (wrapped == null || wrapped.stackSize == 0) return null;
        setWrappedItemStack(stack, wrapped);
        return stack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
        boolean result = wrapped.getItem().onItemUse(wrapped, player, world, x, y, z, side, hitX, hitY, hitZ);
        setDamage(stack, wrapped.getItemDamage());
        if (stack.stackSize < 1) player.destroyCurrentEquippedItem();
        return result;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
        boolean result = wrapped.getItem().onItemUse(wrapped, player, world, x, y, z, side, hitX, hitY, hitZ);
        setDamage(stack, wrapped.getItemDamage());
        if (stack.stackSize < 1) player.destroyCurrentEquippedItem();
        return result;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) return super.onLeftClickEntity(stack, player, entity);
        return wrapped.getItem().onLeftClickEntity(wrapped, player, entity);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int itemInUseCount)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) super.onPlayerStoppedUsing(stack, world, player, itemInUseCount);
        else wrapped.getItem().onPlayerStoppedUsing(wrapped, world, player, itemInUseCount);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean bool)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) super.onUpdate(stack, world, entity, slot, bool);
        else wrapped.getItem().onUpdate(wrapped, world, entity, slot, bool);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) super.onUsingTick(stack, player, count);
        else wrapped.getItem().onUsingTick(wrapped, player, count);
    }

    @Override
    public void setDamage(ItemStack stack, int damage) //TODO: Damage stuff is a little screwy at the moment
    {
        ItemStack wrapped = getWrappedItemStack(stack);
        if (wrapped == null) super.setDamage(stack, damage);
        else
        {
            wrapped.getItem().setDamage(wrapped, damage);
            if (wrapped.getMaxDamage() <= damage) stack.stackSize--;
            setWrappedItemStack(stack, wrapped);
        }
    }
}
