package minechem.item.augment.augments;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class AugmentBase implements IAugment
{
    private final String key;

    protected AugmentBase(String key)
    {
        this.key = key;
    }

    @Override
    public final String getKey()
    {
        return key;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLivingBase, int level)
    {
        return true;
    }

    /**
     * @param item
     * @param player
     * @param level
     * @return false to cancel drop
     */
    @Override
    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player, int level)
    {
        return true;
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
     * @param entityLiving The entity swinging the item.
     * @param stack        The Item stack
     * @param level
     * @return True to cancel any further processing by EntityLiving
     */
    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack, int level)
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
     * @param prevDigSpeed unmodified dig speed
     * @param level
     * @return
     */
    @Override
    public float getModifiedDigSpeed(float prevDigSpeed, Block block, int metadata, int level)
    {
        return prevDigSpeed;
    }

    /**
     * @param toolClass
     * @param level
     * @return modifier to tool level
     */
    @Override
    public int getHarvestLevelModifier(String toolClass, int level)
    {
        return 0;
    }

    /**
     * @param level
     * @return Attribute Modifiers to the base tools attributes.
     */
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(int level)
    {
        return Items.diamond.getAttributeModifiers(null);
    }

    /**
     * @param level
     * @return float value between 0 and 1 indicating probability of damage not being applied to the tool
     */
    @Override
    public float setDamageChance(int level)
    {
        return 0;
    }

    /**
     * @param level
     * @return int modifier to EntityItem lifespan (base 6000)
     */
    @Override
    public int getEntityLifespanModifier(int level)
    {
        return 0;
    }
}
