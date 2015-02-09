package minechem.item.augment.chemicals;

import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IChemicalAugment
{
    boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLivingBase);

    boolean onDroppedByPlayer(ItemStack item, EntityPlayer player);

    boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ);

    boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ);

    boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack);

    boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity);

    ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player);

    void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean bool);

    void onUsingTick(ItemStack stack, EntityPlayer player, int count);

    float getModifiedDigSpeed(ItemStack itemstack, float prevDigSpeed);

    int getHarvestLevelModifier(ItemStack stack, String toolClass);

    Multimap getAttributeModifiers();

    float setDamageChance();

    int getEntityLifespanModifier();
}
