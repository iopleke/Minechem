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
        if (!world.isRemote)
        {
            consumeAugment(stack, level);
            world.createExplosion(null, x + 0.5F, y + 0.5F, z + 0.5F, (level + 3), true);
        }
        return false;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity, int level)
    {
        if (!player.worldObj.isRemote)
        {
            consumeAugment(stack, level);
            player.worldObj.createExplosion(null, entity.posX, entity.posY + entity.height / 2, entity.posZ, (level + 1), false);
        }
        return false;
    }
}
