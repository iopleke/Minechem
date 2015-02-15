package minechem.item.augment.augments;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AugmentFlint extends AugmentBase
{
    public AugmentFlint()
    {
        super("flint");
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int level)
    {
        if (!world.isRemote && player.isSneaking())
        {
            consumeAugment(stack,level);
        }
        return false;
    }
}
