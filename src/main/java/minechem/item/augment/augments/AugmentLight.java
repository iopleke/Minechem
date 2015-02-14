package minechem.item.augment.augments;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import minechem.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent;

public class AugmentLight extends AugmentBase
{
    public AugmentLight()
    {
        super("light");
    }

//    @Override
//    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entityLivingBase, int level)
//    {
//        if (!world.isRemote && world.getLightBrightness(x, y, z) < level + 8)
//        {
//            consumeAugment(stack, level);
//            world.setBlock(x,y,z, BlockRegistry.blockLight,level,3);
//        }
//        return false;
//    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int level)
    {
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        x+=dir.offsetX;
        y+=dir.offsetY;
        z+=dir.offsetZ;
        if (!world.isRemote && world.getBlockLightValue(x, y, z) < level + 8)
        {
            if (world.isAirBlock(x,y,z))
            {
                consumeAugment(stack, level);
                world.setBlock(x, y, z, BlockRegistry.blockLight, level, 3);
            }
        }
        return true;
    }
}
