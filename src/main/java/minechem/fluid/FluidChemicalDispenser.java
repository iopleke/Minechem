package minechem.fluid;

import minechem.MinechemItemsRegistration;
import minechem.item.MinechemChemicalType;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import minechem.radiation.RadiationEnum;
import minechem.radiation.RadiationFluidTileEntity;
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

		if (itemStack.getItem() instanceof ElementItem && itemStack.getItemDamage() >= ElementEnum.heaviestMass)
		{
			Block frontBlock = world.getBlock(x, y, z);
			MinechemChemicalType chemical = MinechemUtil.getChemical(frontBlock);

			if (chemical != null && MinechemUtil.canDrain(world, frontBlock, x, y, z))
			{
				ItemStack stack = MinechemUtil.createItemStack(chemical, 1);

				if (stack != null)
				{
					TileEntity tile = world.getTileEntity(x, y, z);
					if (tile instanceof RadiationFluidTileEntity && ((RadiationFluidTileEntity) tile).info != null)
					{
						ElementItem.setRadiationInfo(((RadiationFluidTileEntity) tile).info, stack);
					}

					TileEntity inventoryTile = blockSource.getBlockTileEntity();
					if (inventoryTile instanceof IInventory)
					{
						stack = MinechemUtil.addItemToInventory((IInventory) inventoryTile, stack);
					}
					MinechemUtil.throwItemStack(world, stack, x, y, z);

					--itemStack.stackSize;
					world.setBlockToAir(x, y, z);
				}
			}
		} else
		{
			Block block = null;
			RadiationEnum radioactivity = null;
			if (itemStack.getItem() instanceof ElementItem)
			{
				ElementEnum element = ElementItem.getElement(itemStack);
				block = FluidHelper.elementsBlocks.get(FluidHelper.elements.get(element));
				radioactivity = element.radioactivity();
			} else if (itemStack.getItem() instanceof MoleculeItem)
			{
				MoleculeEnum molecule = MoleculeEnum.getById(itemStack.getItemDamage());
				block = FluidHelper.moleculeBlocks.get(FluidHelper.molecules.get(molecule));
				radioactivity = molecule.radioactivity();
			}

			if (!world.isAirBlock(x, y, z) && !world.getBlock(x, y, z).getMaterial().isSolid())
			{
				world.func_147480_a(x, y, z, true);
				world.setBlockToAir(x, y, z);
			}

			if (world.isAirBlock(x, y, z) && block != null)
			{
				world.setBlock(x, y, z, block, 0, 3);
				--itemStack.stackSize;
				TileEntity tile = world.getTileEntity(x, y, z);
				if (radioactivity != RadiationEnum.stable && tile instanceof RadiationFluidTileEntity)
				{
					((RadiationFluidTileEntity) tile).info = ElementItem.getRadiationInfo(itemStack, world);
				}
				ItemStack elementStack = new ItemStack(MinechemItemsRegistration.element, 1, ElementEnum.heaviestMass);
				TileEntity inventoryTile = blockSource.getBlockTileEntity();
				if (inventoryTile instanceof IInventory)
				{
					elementStack = MinechemUtil.addItemToInventory((IInventory) inventoryTile, elementStack);
				}
				MinechemUtil.throwItemStack(world, elementStack, x, y, z);
			}
		}

		return itemStack;
	}
}
