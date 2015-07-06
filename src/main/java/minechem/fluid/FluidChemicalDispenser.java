package minechem.fluid;

import minechem.MinechemItemsRegistration;
import minechem.item.MinechemChemicalType;
import minechem.item.element.ElementItem;
import minechem.radiation.RadiationFluidTileEntity;
import minechem.radiation.RadiationInfo;
import minechem.utils.MinechemUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class FluidChemicalDispenser implements IBehaviorDispenseItem
{

    public static void init()
    {
        FluidChemicalDispenser dispenser = new FluidChemicalDispenser();
        BlockDispenser.dispenseBehaviorRegistry.putObject(MinechemItemsRegistration.element, dispenser);
        BlockDispenser.dispenseBehaviorRegistry.putObject(MinechemItemsRegistration.molecule, dispenser);
    }

    @Override
    public ItemStack dispense(IBlockSource blockSource, ItemStack itemStack)
    {
        EnumFacing enumfacing = BlockDispenser.func_149937_b(blockSource.getBlockMetadata());
        World world = blockSource.getWorld();
        int x = blockSource.getXInt() + enumfacing.getFrontOffsetX();
        int y = blockSource.getYInt() + enumfacing.getFrontOffsetY();
        int z = blockSource.getZInt() + enumfacing.getFrontOffsetZ();
        TileEntity inventoryTile = blockSource.getBlockTileEntity();

        if ((itemStack.getItem() instanceof ElementItem) && (itemStack.getItemDamage() != 0))
        {
            Block frontBlock = world.getBlock(x, y, z);
            MinechemChemicalType chemical = MinechemUtil.getChemical(frontBlock);

            if ((chemical != null) && MinechemUtil.canDrain(world, frontBlock, x, y, z))
            {
                ItemStack stack = MinechemUtil.chemicalToItemStack(chemical, 8);

                if (stack != null)
                {
                    if (itemStack.stackSize >= 8)
                    {
                        itemStack.stackSize -= 8;
                    } else
                    {
                        if (inventoryTile instanceof IInventory)
                        {
                            if (MinechemUtil.removeItemStacksFromInventory((IInventory) inventoryTile, MinechemItemsRegistration.element, 0, 8 - itemStack.stackSize) == null)
                            {
                                return itemStack;
                            }
                        }
                    }

                    TileEntity tile = world.getTileEntity(x, y, z);
                    if ((tile instanceof RadiationFluidTileEntity) && (((RadiationFluidTileEntity) tile).info != null))
                    {
                        RadiationInfo.setRadiationInfo(((RadiationFluidTileEntity) tile).info, stack);
                    }
                    world.setBlockToAir(x, y, z);

                    if (inventoryTile instanceof IInventory)
                    {
                        stack = MinechemUtil.addItemToInventory((IInventory) inventoryTile, stack);
                    }
                    MinechemUtil.throwItemStack(world, stack, x, y, z);
                }
            }
        } else
        {
            IInventory inventory;
            if (inventoryTile instanceof IInventory)
            {
                inventory = (IInventory) inventoryTile;
            } else
            {
                return itemStack;
            }

            Block fluidBlock = FluidHelper.getFluidBlock(MinechemUtil.getChemical(itemStack));

            if (!world.isAirBlock(x, y, z) && !world.getBlock(x, y, z).getMaterial().isSolid())
            {
                world.func_147480_a(x, y, z, true);
                world.setBlockToAir(x, y, z);
            }

            if (world.isAirBlock(x, y, z))
            {
                RadiationInfo radioactivity = ElementItem.getRadiationInfo(itemStack, world);
                long worldtime = world.getTotalWorldTime();
                long leftTime = radioactivity.radioactivity.getLife() - (worldtime - radioactivity.decayStarted);

                if (itemStack.stackSize >= 8)
                {
                    itemStack.stackSize -= 8;
                } else
                {
                    for (ItemStack removed : MinechemUtil.removeItemStacksFromInventory(inventory, itemStack.getItem(), itemStack.getItemDamage(), 8 - itemStack.stackSize))
                    {
                        RadiationInfo anotherRadiation = ElementItem.getRadiationInfo(removed, world);
                        long anotherLeft = anotherRadiation.radioactivity.getLife() - (worldtime - anotherRadiation.decayStarted);
                        if (anotherLeft < leftTime)
                        {
                            radioactivity = anotherRadiation;
                            leftTime = anotherLeft;
                        }
                    }
                }
                ItemStack empties = MinechemUtil.addItemToInventory(inventory, new ItemStack(MinechemItemsRegistration.element, 8, 0));
                MinechemUtil.throwItemStack(world, empties, x, y, z);

                world.setBlock(x, y, z, fluidBlock, 0, 3);
                TileEntity tile = world.getTileEntity(x, y, z);
                if (radioactivity.isRadioactive() && (tile instanceof RadiationFluidTileEntity))
                {
                    ((RadiationFluidTileEntity) tile).info = radioactivity;
                }
            }
            return itemStack;
        }

        return itemStack;
    }
}
