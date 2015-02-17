package minechem.item.augment.augments;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        x += dir.offsetX;
        y += dir.offsetY;
        z += dir.offsetZ;
        if (!world.isRemote && player != null && player.isSneaking() && player.canPlayerEdit(x, y, z, side, null) && world.isAirBlock(x, y, z) && Blocks.fire.canPlaceBlockAt(world, x, y, z))
        {
            if (consumeAugment(stack, level) > -1)
            {
                world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                world.setBlock(x, y, z, Blocks.fire);
            }
        }
        return false;
    }
}
