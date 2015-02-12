package minechem.item.augment.augments;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IAugment
{
    /**
     * @return Attribute Modifiers to the base tools attributes.
     */
    Multimap<String, AttributeModifier> getAttributeModifiers();

    /**
     * @return int modifier to EntityItem lifespan (base 6000)
     */
    int getEntityLifespanModifier();

    /**
     * @param toolClass
     * @return modifier to tool level
     */
    int getHarvestLevelModifier(String toolClass);

    String getKey();

    /**
     * @param prevDigSpeed unmodified dig speed
     * @return
     */
    float getModifiedDigSpeed(float prevDigSpeed);

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entityLivingBase);

    boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLivingBase);

    /**
     * @return false to cancel drop
     */
    boolean onDroppedByPlayer(ItemStack item, EntityPlayer player);

    ItemStack onEaten(ItemStack stack, World world, EntityPlayer player);

    /**
     * Called when a entity tries to play the 'swing' animation.
     *
     * @param entityLiving The entity swinging the item.
     * @param stack        The Item stack
     * @return True to cancel any further processing by EntityLiving
     */
    boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack);

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player);

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return True if something happen and false if it don't.
     */
    boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ);

    /**
     * @return true to prevent further processing
     */
    boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ);

    /**
     * Called when the player Left Clicks (attacks) an entity. Processed before damage is done, if return value is true further processing is canceled and the entity is not attacked.
     *
     * @param stack  The Item being used
     * @param player The player that is attacking
     * @param entity The entity being attacked
     * @return True to cancel the rest of the interaction.
     */
    boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity);

    /**
     * Called each tick as long the item is on a player inventory.
     */
    void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean bool);

    /**
     * Called each tick while using an item.
     *
     * @param stack  The Item being used
     * @param player The Player using the item
     * @param count  The amount of time in tick the item has been used for continuously
     */
    void onUsingTick(ItemStack stack, EntityPlayer player, int count);

    /**
     * @return float value between 0 and 1 indicating probability of damage being applied to the tool
     */
    float setDamageChance();

    IAugment setLevel(int level);
}
