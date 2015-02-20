package minechem.item.augment.augments;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AugmentTnt extends AugmentBase
{

    public AugmentTnt()
    {
        super("tnt");
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLivingBase, int level)
    {
        createExplosion(world, null, x + rand.nextDouble(), y + rand.nextDouble(), z + rand.nextDouble(), level + 3, stack, level);
        return false;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity, int level)
    {
        createExplosion(player.getEntityWorld(), null, entity.posX, entity.posY + entity.height / 2, entity.posZ, (level + 1), stack, level);
        return false;
    }

    public void createExplosion(World world, Entity entity, double x, double y, double z, float radius, ItemStack stack, int level)
    {
        if (!world.isRemote)
        {
            consumeAugment(stack, level);
            world.newExplosion(entity, x, y, z, radius, false, false);
        }
    }
}
