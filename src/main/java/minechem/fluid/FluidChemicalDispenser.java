package minechem.fluid;

import minechem.MinechemItemsRegistration;
import minechem.item.MinechemChemicalType;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
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

import java.util.Set;

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

        if (itemStack.getItem() instanceof ElementItem && itemStack.getItemDamage() != 0)
        {
            Block frontBlock = world.getBlock(x, y, z);
            MinechemChemicalType chemical = MinechemUtil.getChemical(frontBlock);

            if (chemical != null && MinechemUtil.canDrain(world, frontBlock, x, y, z))
            {
                ItemStack stack = MinechemUtil.createItemStack(chemical, 8);

                if (stack != null)
                {
                    if (itemStack.stackSize >= 8)
                    {
                        itemStack.stackSize -= 8;
                    } else
                    {
                        if (inventoryTile instanceof IInventory)
                        {
                            int needs = 8 - itemStack.stackSize;
                            IInventory inventory = (IInventory) inventoryTile;
                            Set<ItemStack> otherTubes = MinechemUtil.findItemStacks(inventory, MinechemItemsRegistration.element, 0);
                            int free = 0;
                            otherTubes.remove(itemStack);
                            for (ItemStack emptyStack : otherTubes)
                                free += emptyStack.stackSize;
                            if (free < needs)
                            {
                                return itemStack;
                            }
                            itemStack.stackSize = 0;

                            for (ItemStack emptyStack : otherTubes)
                            {
                                if (emptyStack.stackSize >= needs)
                                {
                                    emptyStack.stackSize -= needs;
                                    needs = 0;
                                } else
                                {
                                    needs -= emptyStack.stackSize;
                                    emptyStack.stackSize = 0;
                                }

                                if (emptyStack.stackSize <= 0)
                                {
                                    MinechemUtil.removeStackInInventory(inventory, emptyStack);
                                }

                                if (needs == 0)
                                {
                                    break;
                                }
                            }
                        }
                    }

                    TileEntity tile = world.getTileEntity(x, y, z);
                    if (tile instanceof RadiationFluidTileEntity && ((RadiationFluidTileEntity) tile).info != null)
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
            Block block;
            if (inventoryTile instanceof IInventory)
            {
                inventory = (IInventory) inventoryTile;
            } else
            {
                return itemStack;
            }
            if (itemStack.getItem() instanceof ElementItem)
            {
                ElementEnum element = ElementItem.getElement(itemStack);
                block = FluidHelper.elementsBlocks.get(FluidHelper.elements.get(element));
            } else if (itemStack.getItem() instanceof MoleculeItem)
            {
                MoleculeEnum molecule = MoleculeEnum.getById(itemStack.getItemDamage());
                block = FluidHelper.moleculeBlocks.get(FluidHelper.molecules.get(molecule));
            } else
            {
                return itemStack;
            }

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
                    int needs = 8 - itemStack.stackSize;
                    itemStack.stackSize = 0;
                    Set<ItemStack> otherItemsStacks = MinechemUtil.findItemStacks(inventory, itemStack.getItem(), itemStack.getItemDamage());
                    otherItemsStacks.remove(itemStack);
                    int free = 0;
                    for (ItemStack stack : otherItemsStacks)
                        free += stack.stackSize;
                    if (free < needs)
                    {
                        return itemStack;
                    }

                    for (ItemStack stack : otherItemsStacks)
                    {
                        RadiationInfo anotherRadiation = ElementItem.getRadiationInfo(stack, world);
                        long anotherLeft = anotherRadiation.radioactivity.getLife() - (worldtime - anotherRadiation.decayStarted);
                        if (anotherLeft < leftTime)
                        {
                            radioactivity = anotherRadiation;
                            leftTime = anotherLeft;
                        }

                        if (stack.stackSize >= needs)
                        {
                            stack.stackSize -= needs;
                            needs = 0;
                        } else
                        {
                            needs -= stack.stackSize;
                            stack.stackSize = 0;
                        }

                        if (stack.stackSize <= 0)
                        {
                            MinechemUtil.removeStackInInventory(inventory, stack);
                        }

                        if (needs == 0)
                        {
                            break;
                        }
                    }
                }
                ItemStack empties = MinechemUtil.addItemToInventory(inventory, new ItemStack(MinechemItemsRegistration.element, 8, 0));
                MinechemUtil.throwItemStack(world, empties, x, y, z);

                world.setBlock(x, y, z, block, 0, 3);
                TileEntity tile = world.getTileEntity(x, y, z);
                if (radioactivity.isRadioactive() && tile instanceof RadiationFluidTileEntity)
                {
                    ((RadiationFluidTileEntity) tile).info = radioactivity;
                }
            }
            return itemStack;

//			Block block = null;
//			RadiationEnum radioactivity = null;
//			if (itemStack.getItem() instanceof ElementItem)
//			{
//				ElementEnum element = ElementItem.getElement(itemStack);
//				block = FluidHelper.elementsBlocks.get(FluidHelper.elements.get(element));
//				radioactivity = element.radioactivity();
//			} else if (itemStack.getItem() instanceof MoleculeItem)
//			{
//				MoleculeEnum molecule = MoleculeEnum.getById(itemStack.getItemDamage());
//				block = FluidHelper.moleculeBlocks.get(FluidHelper.molecules.get(molecule));
//				radioactivity = molecule.radioactivity();
//			}
//
//			if (!world.isAirBlock(x, y, z) && !world.getBlock(x, y, z).getMaterial().isSolid())
//			{
//				world.func_147480_a(x, y, z, true);
//				world.setBlockToAir(x, y, z);
//			}
//
//			if (world.isAirBlock(x, y, z) && block != null)
//			{
//				world.setBlock(x, y, z, block, 0, 3);
//				--itemStack.stackSize;
//				TileEntity tile = world.getTileEntity(x, y, z);
//				if (radioactivity != RadiationEnum.stable && tile instanceof RadiationFluidTileEntity)
//				{
//					((RadiationFluidTileEntity) tile).info = ElementItem.getRadiationInfo(itemStack, world);
//				}
//				ItemStack elementStack = new ItemStack(MinechemItemsRegistration.element, 1, ElementEnum.heaviestMass);
//				TileEntity inventoryTile = blockSource.getBlockTileEntity();
//				if (inventoryTile instanceof IInventory)
//				{
//					elementStack = MinechemUtil.addItemToInventory((IInventory) inventoryTile, elementStack);
//				}
//				MinechemUtil.throwItemStack(world, elementStack, x, y, z);
//			}
        }

        return itemStack;
    }
}
