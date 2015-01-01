package minechem.fluid;

import minechem.item.bucket.MinechemBucketHandler;
import minechem.item.bucket.MinechemBucketItem;
import minechem.radiation.RadiationEnum;
import minechem.radiation.RadiationFluidTileEntity;
import minechem.radiation.RadiationInfo;
import minechem.utils.MinechemUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class MinechemBucketReceiver implements IBehaviorDispenseItem
{

    public static void init()
    {
        IBehaviorDispenseItem source = (IBehaviorDispenseItem) BlockDispenser.dispenseBehaviorRegistry.getObject(Items.bucket);
        MinechemBucketReceiver receiver = new MinechemBucketReceiver(source);
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, receiver);
    }

    public final IBehaviorDispenseItem source;

    public MinechemBucketReceiver(IBehaviorDispenseItem source)
    {
        this.source = source;
    }

    @Override
    public ItemStack dispense(IBlockSource blockSource, ItemStack itemstack)
    {
        EnumFacing enumfacing = BlockDispenser.func_149937_b(blockSource.getBlockMetadata());
        World world = blockSource.getWorld();
        int x = blockSource.getXInt() + enumfacing.getFrontOffsetX();
        int y = blockSource.getYInt() + enumfacing.getFrontOffsetY();
        int z = blockSource.getZInt() + enumfacing.getFrontOffsetZ();
        Block front = world.getBlock(x, y, z);

        if (front instanceof MinechemFluidBlock)
        {
            MinechemBucketItem item = MinechemBucketHandler.getInstance().buckets.get(front);
            ItemStack newstack = new ItemStack(item);

            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile != null && item.chemical.radioactivity() != RadiationEnum.stable)
            {
                RadiationInfo.setRadiationInfo(((RadiationFluidTileEntity) tile).info, newstack);
            }

            world.func_147480_a(x, y, z, true);
            itemstack.stackSize--;

            if (itemstack.stackSize <= 0)
            {
                return newstack;
            } else
            {
                TileEntity inventoryTile = blockSource.getBlockTileEntity();
                if (inventoryTile instanceof IInventory)
                {
                    ItemStack stack = MinechemUtil.addItemToInventory((IInventory) blockSource.getBlockTileEntity(), newstack);
                    if (stack != null)
                    {
                        MinechemUtil.throwItemStack(world, stack, x, y, z);
                    }
                } else
                {
                    MinechemUtil.throwItemStack(world, newstack, x, y, z);
                }
            }

            return itemstack;
        }

        return source.dispense(blockSource, itemstack);
    }

}
