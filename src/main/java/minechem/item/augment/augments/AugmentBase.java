package minechem.item.augment.augments;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class AugmentBase implements IAugment
{
    private String key;
    protected int level;

    /**
     * @return Attribute Modifiers to the base tools attributes.
     */
    @Override
    public Multimap getAttributeModifiers()
    {
        return Items.diamond.getAttributeModifiers(null);
    }

    /**
     * @return int modifier to EntityItem lifespan (base 6000)
     */
    @Override
    public int getEntityLifespanModifier()
    {
        return 0;
    }

    /**
     * @param toolClass
     * @return modifier to tool level
     */
    @Override
    public int getHarvestLevelModifier(String toolClass)
    {
        return 0;
    }

    @Override
    public String getKey()
    {
        return key;
    }

    /**
     * @param prevDigSpeed unmodified dig speed
     * @return
     */
    @Override
    public float getModifiedDigSpeed(float prevDigSpeed)
    {
        return prevDigSpeed;
    }

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     *
     * @param stack
     * @param player
     * @param entityLivingBase
     */
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entityLivingBase)
    {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLivingBase)
    {
        return false;
    }

    /**
     * @param item
     * @param player
     * @return false to cancel drop
     */
    @Override
    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
    {
        return true;
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
    {
        return stack;
    }

    /**
     * Called when a entity tries to play the 'swing' animation.
     *
     * @param entityLiving The entity swinging the item.
     * @param stack        The Item stack
     * @return True to cancel any further processing by EntityLiving
     */
    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        return false;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     *
     * @param stack
     * @param world
     * @param player
     */
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        return stack;
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return True if something happen and false if it don't.
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
     */
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
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
     * @return true to prevent further processing
     */
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        return false;
    }

    /**
     * Called when the player Left Clicks (attacks) an entity. Processed before damage is done, if return value is true further processing is canceled and the entity is not attacked.
     *
     * @param stack  The Item being used
     * @param player The player that is attacking
     * @param entity The entity being attacked
     * @return True to cancel the rest of the interaction.
     */
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        return false;
    }

    /**
     * Called each tick as long the item is on a player inventory.
     *
     * @param stack
     * @param world
     * @param entity
     * @param slot
     * @param bool
     */
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean bool)
    {
    }

    /**
     * Called each tick while using an item.
     *
     * @param stack  The Item being used
     * @param player The Player using the item
     * @param count  The amount of time in tick the item has been used for continuously
     */
    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
    }

    /**
     * @return float value between 0 and 1 indicating probability of damage being applied to the tool
     */
    @Override
    public float setDamageChance()
    {
        return 1;
    }

    @Override
    public IAugment setLevel(int level)
    {
        this.level = level;
        return this;
    }
}
