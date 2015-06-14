package minechem.item.augment.augments;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AugmentLightning extends AugmentBase
{
    public AugmentLightning()
    {
        super("lightning");
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLivingBase, int level)
    {
        if (rand.nextInt(75) <= level)
        {
            spawnLightning(world, x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), stack, level);
        }
        return false;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase user, int level)
    {
        if (rand.nextInt(15) <= level)
        {
            spawnLightning(user.worldObj, target.posX, target.posY, target.posZ, stack, level);
        }
        return false;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean bool, int level)
    {
        if (rand.nextInt(24000) <= level && entity instanceof EntityLivingBase)
        {
            spawnLightning(world, entity.posX, entity.posY, entity.posZ, stack, level);
        }
    }

    public void spawnLightning(World world, double x, double y, double z, ItemStack stack, int level)
    {
        if (!world.isRemote)
        {
            level = getUsableLevel(stack, level);
            if (level >= 0)
            {
                consumeAugment(stack, level);
                world.addWeatherEffect(new EntityLightningBolt(world, x, y, z));
            }
        }
    }
}
