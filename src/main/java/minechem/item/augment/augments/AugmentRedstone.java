package minechem.item.augment.augments;

import minechem.registry.BlockRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class AugmentRedstone extends AugmentBase
{
    public AugmentRedstone()
    {
        super("redstone");
    }

    @Override
    public int getUsableLevel(ItemStack stack, int level)
    {
        return level;
    }

    @Override
    public int getMaxLevel()
    {
        return 15;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int level)
    {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        x += dir.offsetX;
        y += dir.offsetY;
        z += dir.offsetZ;
        if (!world.isRemote && player != null && player.canPlayerEdit(x, y, z, side, null))
        {
            if (world.isAirBlock(x, y, z))
            {
                world.setBlock(x, y, z, BlockRegistry.blockRedstone, level + 5, 7);
            } else if (world.getBlock(x, y, z) == BlockRegistry.blockRedstone)
            {
                world.setBlockMetadataWithNotify(x, y, z, level + 5, 4);
            }
        }
        return false;
    }
}
