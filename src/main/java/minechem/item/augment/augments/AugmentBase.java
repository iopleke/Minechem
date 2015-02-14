package minechem.item.augment.augments;

import com.google.common.collect.Multimap;
import minechem.Compendium;
import minechem.item.augment.AugmentedItem;
import minechem.item.augment.IAugmentItem;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidContainerItem;

public abstract class AugmentBase implements IAugment
{
    private final String key;

    public AugmentBase(String key)
    {
        this.key = key;
    }

    @Override
    public final String getKey()
    {
        return key;
    }

    /**
     * @param stack
     * @param level
     * @return max Level of damage can be applied
     */
    @Override
    public int getUsableLevel(ItemStack stack, int level)
    {
        NBTTagCompound augment = stack.getTagCompound().getCompoundTag(this.getKey());
        ItemStack augmentItem = ItemStack.loadItemStackFromNBT(augment.getCompoundTag(Compendium.NBTTags.item));
        if (augmentItem == null || augmentItem.getItem() == null) return -1;
        return dischargeAugment(augmentItem, level, false);
    }

    /**
     * @param stack
     * @param level
     * @return int level of augment applied
     */
    @Override
    public int consumeAugment(ItemStack stack, int level)
    {
        NBTTagCompound augment = stack.getTagCompound().getCompoundTag(this.getKey());
        ItemStack augmentItem = ItemStack.loadItemStackFromNBT(augment.getCompoundTag(Compendium.NBTTags.item));
        if (augmentItem == null || augmentItem.getItem() == null) return -1;
        int discharged = dischargeAugment(augmentItem, level, true);
        augment.setTag(Compendium.NBTTags.item,augmentItem.writeToNBT(new NBTTagCompound()));
        return discharged;
    }

    /**
     * Actually handles the draining/damaging of Augments from the stored container
     *
     * @param stack
     * @param level
     * @param discharge
     * @return int: max level <= {@param level} that can be used
     */
    public int dischargeAugment(ItemStack stack, int level, boolean discharge)
    {
        if (stack.getItem() instanceof IFluidContainerItem)
        {
            while (!this.drain((IFluidContainerItem)stack.getItem(), stack, this.getVolumeConsumed(level), false) && level>=0) level--;
            if (discharge && level>=0) drain((IFluidContainerItem)stack.getItem(),stack,this.getVolumeConsumed(level),true);
            return level;
        }
        else if (stack.getItem() instanceof IAugmentItem)
        {
            if (discharge) return ((IAugmentItem)stack.getItem()).consumeLevel(stack, this, this.getVolumeConsumed(level));
            return ((IAugmentItem)stack.getItem()).getMaxLevel(stack, this, this.getVolumeConsumed(level));
        }
        else if (stack.isItemStackDamageable())
        {
            while (this.getDamageDone(level)>stack.getItemDamage() && level>=0) level--;
            if (discharge && level>=0) stack.attemptDamageItem(this.getDamageDone(level), AugmentedItem.rand);
            return level;
        }
        return -1;
    }

    /**
     * Attempts to drain passed ItemStack for FluidContainer augments
     * @param item
     * @param stack
     * @param volume
     * @param doDrain
     * @return true for volume is drainable
     */
    public boolean drain(IFluidContainerItem item, ItemStack stack, int volume, boolean doDrain)
    {
        return volume == item.drain(stack,volume,doDrain).amount;
    }

    /**
     * @param level
     * @return Fluid to drain from FluidContainer Augment for given Augment level
     */
    public int getVolumeConsumed(int level)
    {
        return level*10;
    }

    /**
     * @param level
     * @return Damage to do to an ItemStack augment for given Augment level
     */
    public int getDamageDone(int level)
    {
        return level;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLivingBase, int level)
    {
        return false;
    }

    /**
     * @param stack
     * @param player
     * @param level
     * @return false to cancel drop
     */
    @Override
    public boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player, int level)
    {
        return true;
    }

    /**
     * Called by the default implemetation of EntityItem's onUpdate method, allowing for cleaner
     * control over the update of the item without having to write a subclass.
     *
     * @param stack
     * @param entityItem The entity Item
     * @param level
     * @return Return true to skip any further update code.
     */
    @Override
    public boolean onEntityItemUpdate(ItemStack stack, EntityItem entityItem, int level)
    {
        return false;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't.
     *
     * @param stack
     * @param player
     * @param world
     * @param x
     * @param y
     * @param z
     * @param side
     * @param hitX
     * @param hitY
     * @param hitZ
     * @param level
     */
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int level)
    {
        return false;
    }

    /**
     * @param stack
     * @param player
     * @param world
     * @param x
     * @param y
     * @param z
     * @param side
     * @param hitX
     * @param hitY
     * @param hitZ
     * @param level
     * @return true to prevent further processing
     */
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int level)
    {
        return false;
    }

    /**
     * Called when a entity tries to play the 'swing' animation.
     *
     * @param stack        The Item stack
     * @param entityLiving The entity swinging the item.
     * @param level
     * @return True to cancel any further processing by EntityLiving
     */
    @Override
    public boolean onEntitySwing(ItemStack stack, EntityLivingBase entityLiving, int level)
    {
        return false;
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     *
     * @param stack
     * @param player
     * @param entityLivingBase
     * @param level
     */
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entityLivingBase, int level)
    {
        return false;
    }

    /**
     * Called when the player Left Clicks (attacks) an entity. Processed before damage is done, if return value is true further processing is canceled and the entity is not attacked.
     *
     * @param stack  The Item being used
     * @param player The player that is attacking
     * @param entity The entity being attacked
     * @param level
     * @return True to cancel the rest of the interaction.
     */
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity, int level)
    {
        return false;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     *
     * @param stack
     * @param world
     * @param player
     * @param level
     */
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player, int level)
    {
        return stack;
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player, int level)
    {
        return stack;
    }

    /**
     * Called each tick as long the item is on a player inventory.
     *
     * @param stack
     * @param world
     * @param entity
     * @param slot
     * @param bool
     * @param level
     */
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean bool, int level)
    {

    }

    /**
     * Called each tick while using an item.
     *
     * @param stack  The Item being used
     * @param player The Player using the item
     * @param count  The amount of time in tick the item has been used for continuously
     * @param level
     */
    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count, int level)
    {

    }

    /**
     * @param stack
     * @param prevDigSpeed unmodified dig speed
     * @param block
     * @param metadata
     * @param level        @return
     */
    @Override
    public float getModifiedDigSpeed(ItemStack stack, float prevDigSpeed, Block block, int metadata, int level)
    {
        return prevDigSpeed;
    }

    /**
     * @param stack
     * @param toolClass
     * @param level
     * @return modifier to tool level
     */
    @Override
    public int getHarvestLevelModifier(ItemStack stack, String toolClass, int level)
    {
        return 0;
    }

    /**
     * @param stack
     * @param level
     * @return Attribute Modifiers to the base tools attributes.
     */
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack, int level)
    {
        return Items.diamond.getAttributeModifiers(null);
    }

    /**
     * @param stack
     * @param level
     * @return float value between 0 and 1 indicating probability of damage not being applied to the tool
     */
    @Override
    public float setDamageChance(ItemStack stack, int level)
    {
        return 0;
    }

    /**
     * @param stack
     * @param level
     * @return int modifier to EntityItem lifespan (base 6000)
     */
    @Override
    public int getEntityLifespanModifier(ItemStack stack, int level)
    {
        return 0;
    }
}
