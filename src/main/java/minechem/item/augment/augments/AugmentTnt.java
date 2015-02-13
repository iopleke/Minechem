package minechem.item.augment.augments;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
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
            world.createExplosion(entityLivingBase, x + 0.5F, y + 0.5F, z + 0.5F, (level + 3), true);
        }
        return false;
    }
}
