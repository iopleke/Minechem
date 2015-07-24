package minechem.fluid;

import java.util.Set;
import minechem.MinechemItemsRegistration;
import minechem.item.MinechemChemicalType;
import minechem.item.bucket.MinechemBucketHandler;
import minechem.item.bucket.MinechemBucketItem;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import minechem.radiation.RadiationFluidTileEntity;
import minechem.radiation.RadiationInfo;
import minechem.utils.MinechemUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public final class FluidDispenseHelper
{

    private FluidDispenseHelper()
    {
    }

    public static ItemStack dispenseOnItemUse(ItemStack itemStack, World world, EntityPlayer player, MovingObjectPosition movingObjectPosition, boolean isEmptyTube)
    {
        if (movingObjectPosition == null)
        {
            return itemStack;
        }

        if (movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        {
            int blockX = movingObjectPosition.blockX;
            int blockY = movingObjectPosition.blockY;
            int blockZ = movingObjectPosition.blockZ;

            if (!isEmptyTube)
            {
                ForgeDirection dir = ForgeDirection.getOrientation(movingObjectPosition.sideHit);
                blockX += dir.offsetX;
                blockY += dir.offsetY;
                blockZ += dir.offsetZ;
            }

            if (!player.canPlayerEdit(blockX, blockY, blockZ, movingObjectPosition.sideHit, itemStack))
            {
                return itemStack;
            }

            return FluidDispenseHelper.dispense(itemStack, player.inventory, blockX, blockY, blockZ, player.posX, player.posY, player.posZ, world, !player.capabilities.isCreativeMode);
        }

        return itemStack;
    }

    public static ItemStack dispenseOnDispenser(IBlockSource blockSource, ItemStack itemStack)
    {
        EnumFacing enumfacing = BlockDispenser.func_149937_b(blockSource.getBlockMetadata());
        World world = blockSource.getWorld();
        int targetX = blockSource.getXInt() + enumfacing.getFrontOffsetX();
        int targetY = blockSource.getYInt() + enumfacing.getFrontOffsetY();
        int targetZ = blockSource.getZInt() + enumfacing.getFrontOffsetZ();
        TileEntity te = blockSource.getBlockTileEntity();
        IInventory inventory = te instanceof IInventory ? (IInventory) te : null;
        return FluidDispenseHelper.dispense(itemStack, inventory, targetX, targetY, targetZ, blockSource.getX(), blockSource.getY(), blockSource.getZ(), world, true);
    }

    public static ItemStack dispense(ItemStack source, IInventory inventory, int targetX, int targetY, int targetZ, double sourceX, double sourceY, double sourceZ, World world, boolean doCostItems)
    {
        Item sourceType = source.getItem();
        if ((sourceType instanceof ElementItem) || (sourceType instanceof MoleculeItem))
        {
            if ((sourceType instanceof ElementItem) && (source.getItemDamage() == 0))
            {
                // empty tubes
                return dispenseEmptyTubes(source, inventory, targetX, targetY, targetZ, sourceX, sourceY, sourceZ, world, doCostItems);
            } else
            {
                // filled tubes
                return dispenseFilledTubes(source, inventory, targetX, targetY, targetZ, sourceX, sourceY, sourceZ, world, doCostItems);
            }
        } else if (sourceType instanceof MinechemBucketItem)
        {
            // filled buckets
            return dispenseFilledBucket(source, inventory, targetX, targetY, targetZ, sourceX, sourceY, sourceZ, world, doCostItems);
        } else if (sourceType instanceof ItemBucket)
        {
            // empty buckets
            return dispenseEmptyBucket(source, inventory, targetX, targetY, targetZ, sourceX, sourceY, sourceZ, world, doCostItems);
        } else
        {
            return source;
        }
    }

    private static ItemStack dispenseEmptyBucket(ItemStack source, IInventory inventory, int targetX, int targetY, int targetZ, double sourceX, double sourceY, double sourceZ, World world, boolean doCostItems)
    {
        Item chemicalBucket = MinechemBucketHandler.getInstance().getBucket(getDrainedChemical(world, targetX, targetY, targetZ));
        if (chemicalBucket == null)
        {
            return source;
        }

        if (doCostItems)
        {
            source.stackSize--;
        }

        ItemStack output = new ItemStack(chemicalBucket);
        RadiationInfo radioactivity = removeFluidBlockAt(world, targetX, targetY, targetZ);
        if (radioactivity != null)
        {
            RadiationInfo.setRadiationInfo(radioactivity, output);
        }

        if (doCostItems)
        {
            if (source.stackSize == 0)
            {
                return output;
            }
            MinechemUtil.throwItemStack(world, MinechemUtil.addItemToInventory(inventory, output), sourceX, sourceY, sourceZ);
        }
        return source;
    }

    private static ItemStack dispenseFilledBucket(ItemStack source, IInventory inventory, int targetX, int targetY, int targetZ, double sourceX, double sourceY, double sourceZ, World world, boolean doCostItems)
    {
        if (!canDispenseTo(world, targetX, targetY, targetZ))
        {
            return source;
        }

        Block dispensed = getDispensedBlock(source);
        if (dispensed == null)
        {
            return source;
        }

        if (doCostItems)
        {
            source.stackSize--;
        }

        dispenseBlockTo(world, targetX, targetY, targetZ, dispensed, RadiationInfo.getRadiationInfo(source, world));

        if (doCostItems)
        {
            ItemStack output = new ItemStack(Items.bucket, 1, 0);
            if (source.stackSize == 0)
            {
                return output;
            }
            MinechemUtil.throwItemStack(world, MinechemUtil.addItemToInventory(inventory, output), sourceX, sourceY, sourceZ);
        }
        return source;
    }

    private static ItemStack dispenseEmptyTubes(ItemStack source, IInventory inventory, int targetX, int targetY, int targetZ, double sourceX, double sourceY, double sourceZ, World world, boolean doCostItems)
    {
        ItemStack output = MinechemUtil.chemicalToItemStack(getDrainedChemical(world, targetX, targetY, targetZ), 8);
        if (output == null)
        {
            return source;
        }

        if (doCostItems)
        {
            if (source.stackSize >= 8)
            {
                source.stackSize -= 8;
            } else
            {
                int oldStackSize = source.stackSize;
                source.stackSize = 0;
                if (MinechemUtil.removeItemStacksFromInventory(inventory, MinechemItemsRegistration.element, 0, 8 - oldStackSize) == null)
                {
                    // no enough items
                    source.stackSize = oldStackSize; // restore stack size
                    return source;
                }
            }
        }

        RadiationInfo radioactivity = removeFluidBlockAt(world, targetX, targetY, targetZ);
        if (radioactivity != null)
        {
            RadiationInfo.setRadiationInfo(radioactivity, output);
        }

        if (doCostItems)
        {
            if (source.stackSize == 0)
            {
                return output;
            }
            MinechemUtil.throwItemStack(world, MinechemUtil.addItemToInventory(inventory, output), sourceX, sourceY, sourceZ);
        }
        return source;
    }

    private static ItemStack dispenseFilledTubes(ItemStack source, IInventory inventory, int targetX, int targetY, int targetZ, double sourceX, double sourceY, double sourceZ, World world, boolean doCostItems)
    {
        if (!canDispenseTo(world, targetX, targetY, targetZ))
        {
            return source;
        }

        Block dispensed = getDispensedBlock(source);
        if (dispensed == null)
        {
            return source;
        }

        RadiationInfo radioactivity = RadiationInfo.getRadiationInfo(source, world);

        if (doCostItems)
        {
            if (source.stackSize >= 8)
            {
                source.stackSize -= 8;
            } else
            {
                int oldStackSize = source.stackSize;
                source.stackSize = 0;
                Set<ItemStack> otherStacks = MinechemUtil.removeItemStacksFromInventory(inventory, source.getItem(), source.getItemDamage(), 8 - oldStackSize);
                if (otherStacks == null)
                {
                    // no enough items
                    source.stackSize = oldStackSize; // restore stack size
                    return source;
                }

                long now = world.getTotalWorldTime();
                long minLeftTime = radioactivity.getLeftTime(now);
                for (ItemStack anotherStack : otherStacks)
                {
                    long anotherLeftTime = RadiationInfo.getRadiationInfo(anotherStack, world).getLeftTime(now);
                    if (anotherLeftTime < minLeftTime)
                    {
                        minLeftTime = anotherLeftTime;
                    }
                }
                radioactivity.setLeftTime(now, minLeftTime);
            }
        }

        dispenseBlockTo(world, targetX, targetY, targetZ, dispensed, radioactivity);

        if (doCostItems)
        {
            ItemStack output = new ItemStack(MinechemItemsRegistration.element, 8, 0);
            if (source.stackSize == 0)
            {
                return output;
            }
            MinechemUtil.throwItemStack(world, MinechemUtil.addItemToInventory(inventory, output), sourceX, sourceY, sourceZ);
        }
        return source;
    }

    private static boolean canDispenseTo(World world, int x, int y, int z)
    {
        return world.isAirBlock(x, y, z) || !world.getBlock(x, y, z).getMaterial().isSolid();
    }

    private static Block getDispensedBlock(ItemStack itemStack)
    {
        MinechemChemicalType chemical = MinechemUtil.getChemical(itemStack);
        if (chemical == MoleculeEnum.water)
        {
            return Blocks.water;
        } else
        {
            return FluidHelper.getFluidBlock(MinechemUtil.getChemical(itemStack));
        }
    }

    private static MinechemChemicalType getDrainedChemical(World world, int x, int y, int z)
    {
        Block target = world.getBlock(x, y, z);
        MinechemChemicalType chemical = MinechemUtil.getChemical(target);
        if ((chemical != null) && MinechemUtil.canDrain(world, target, x, y, z))
        {
            return chemical;
        } else
        {
            return null;
        }
    }

    private static void dispenseBlockTo(World world, int x, int y, int z, Block block, RadiationInfo radioactivity)
    {
        if (!world.isAirBlock(x, y, z))
        {
            if (!world.getBlock(x, y, z).getMaterial().isLiquid())
            {
                world.func_147480_a(x, y, z, true);
            }
            world.setBlockToAir(x, y, z);
        }

        world.setBlock(x, y, z, block, 0, 3);
        TileEntity te = world.getTileEntity(x, y, z);
        if (radioactivity.isRadioactive() && (te instanceof RadiationFluidTileEntity))
        {
            ((RadiationFluidTileEntity) te).info = radioactivity;
        }
        world.notifyBlockOfNeighborChange(x, y, z, block);
    }

    private static RadiationInfo removeFluidBlockAt(World world, int x, int y, int z)
    {
        RadiationInfo radioactivity;
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof RadiationFluidTileEntity)
        {
            radioactivity = ((RadiationFluidTileEntity) tile).info;
        } else
        {
            radioactivity = null;
        }

        world.setBlockToAir(x, y, z);
        world.removeTileEntity(x, y, z);
        return radioactivity;
    }
}
