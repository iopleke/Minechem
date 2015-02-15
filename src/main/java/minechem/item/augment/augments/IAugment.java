package minechem.item.augment.augments;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IAugment
{
    String getKey();

    /**
     * @param stack
     * @param level
     * @return max Level of damage can be applied
     */
    int getUsableLevel(ItemStack stack, int level);

    /**
     * @param stack
     * @param level
     * @return int level of augment applied
     */
    int consumeAugment(ItemStack stack, int level);


    /**
     * @return true to make the block drop
     */
    boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLivingBase, int level);

    /**
     * @return false to cancel drop
     */
    boolean onDroppedByPlayer(ItemStack stack, EntityPlayer player, int level);

    /**
     * Called by the default implemetation of EntityItem's onUpdate method, allowing for cleaner control over the update of the item without having to write a subclass.
     *
     * @param entityItem The entity Item
     * @return Return true to skip any further update code.
     */
    boolean onEntityItemUpdate(ItemStack stack, EntityItem entityItem, int level);

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return True if something happen and false if it don't.
     */
    boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int level);

    /**
     * @return true to prevent further processing
     */
    boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int level);

    /**
     * Called when a entity tries to play the 'swing' animation.
     *
     * @param entityLiving The entity swinging the item.
     * @param stack        The Item stack
     * @return True to cancel any further processing by EntityLiving
     */
    boolean onEntitySwing(ItemStack stack, EntityLivingBase entityLiving, int level);

    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entityLivingBase, int level);

    /**
     * Called when the player Left Clicks (attacks) an entity. Processed before damage is done, if return value is true further processing is canceled and the entity is not attacked.
     *
     * @param stack  The Item being used
     * @param player The player that is attacking
     * @param entity The entity being attacked
     * @return True to cancel the rest of the interaction.
     */
    boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity, int level);

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player, int level);

    ItemStack onEaten(ItemStack stack, World world, EntityPlayer player, int level);

    /**
     * Called each tick as long the item is on a player inventory.
     */
    void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean bool, int level);

    /**
     * Called each tick while using an item.
     *
     * @param stack  The Item being used
     * @param player The Player using the item
     * @param count  The amount of time in tick the item has been used for continuously
     */
    void onUsingTick(ItemStack stack, EntityPlayer player, int count, int level);

    /**
     * @param prevDigSpeed unmodified dig speed
     * @return
     */
    float getModifiedDigSpeed(ItemStack stack, float prevDigSpeed, Block block, int metadata, int level);

    /**
     * @param toolClass
     * @return modifier to tool level
     */
    int getHarvestLevelModifier(ItemStack stack, String toolClass, int level);

    /**
     * @return Attribute Modifiers to the base tools attributes.
     */
    Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack, int level);

    /**
     * @return float value between 0 and 1 indicating probability of damage not being applied to the tool
     */
    float setDamageChance(ItemStack stack, int level);

    /**
     * @return int modifier to EntityItem lifespan (base 6000)
     */
    int getEntityLifespanModifier(ItemStack stack, int level);

    boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase user, int level);
}
