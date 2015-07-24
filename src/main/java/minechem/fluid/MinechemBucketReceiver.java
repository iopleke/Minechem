package minechem.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class MinechemBucketReceiver implements IBehaviorDispenseItem
{

    public static void init()
    {
        IBehaviorDispenseItem proxied = (IBehaviorDispenseItem) BlockDispenser.dispenseBehaviorRegistry.getObject(Items.bucket);
        MinechemBucketReceiver receiver = new MinechemBucketReceiver(proxied);
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, receiver);
    }

    public final IBehaviorDispenseItem proxied;

    public MinechemBucketReceiver(IBehaviorDispenseItem proxied)
    {
        this.proxied = proxied;
    }

    @Override
    public ItemStack dispense(IBlockSource blockSource, ItemStack itemStack)
    {
        EnumFacing enumfacing = BlockDispenser.func_149937_b(blockSource.getBlockMetadata());
        World world = blockSource.getWorld();
        int targetX = blockSource.getXInt() + enumfacing.getFrontOffsetX();
        int targetY = blockSource.getYInt() + enumfacing.getFrontOffsetY();
        int targetZ = blockSource.getZInt() + enumfacing.getFrontOffsetZ();
        Block front = world.getBlock(targetX, targetY, targetZ);

        if (front instanceof MinechemFluidBlock)
        {
            return FluidDispenseHelper.dispenseOnDispenser(blockSource, itemStack);
        }

        return proxied.dispense(blockSource, itemStack);
    }

}
