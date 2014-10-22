package minechem.utils;

import java.util.Random;
import minechem.MinechemItemsRegistration;
import minechem.fluid.FluidChemical;
import minechem.fluid.FluidElement;
import minechem.item.MinechemChemicalType;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;

public final class MinechemUtil
{

	public static final Random ran = new Random();

	private MinechemUtil()
	{
	}

	public static ItemStack addItemToInventory(IInventory inventory, ItemStack itemStack)
	{
		if (itemStack == null)
		{
			return null;
		}

		for (int i = 0, l = inventory.getSizeInventory(); i < l; i++)
		{
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack == null)
			{
				int append = itemStack.stackSize > inventory.getInventoryStackLimit() ? inventory.getInventoryStackLimit() : itemStack.stackSize;
				inventory.setInventorySlotContents(i, new ItemStack(itemStack.getItem(), append, itemStack.getItemDamage()));
				itemStack.stackSize -= append;
			} else if (stack.getItem() == itemStack.getItem() && stack.getItemDamage() == itemStack.getItemDamage())
			{
				int free = inventory.getInventoryStackLimit() - stack.stackSize;
				int append = itemStack.stackSize > free ? free : itemStack.stackSize;
				itemStack.stackSize -= append;
				stack.stackSize += append;
				inventory.setInventorySlotContents(i, stack);
			}

			if (itemStack.stackSize <= 0)
			{
				itemStack = null;
				break;
			}
		}
		return itemStack;
	}

	public static void throwItemStack(World world, ItemStack itemStack, double x, double y, double z)
	{
		if (itemStack != null)
		{
			float f = ran.nextFloat() * 0.8F + 0.1F;
			float f1 = ran.nextFloat() * 0.8F + 0.1F;
			float f2 = ran.nextFloat() * 0.8F + 0.1F;

			EntityItem entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2), itemStack);
			float f3 = 0.05F;
			entityitem.motionX = (double) ((float) ran.nextGaussian() * f3);
			entityitem.motionY = (double) ((float) ran.nextGaussian() * f3 + 0.2F);
			entityitem.motionZ = (double) ((float) ran.nextGaussian() * f3);
			world.spawnEntityInWorld(entityitem);
		}
	}

	public static ItemStack createItemStack(MinechemChemicalType chemical, int amount)
	{
		ItemStack itemStack = null;
		if (chemical instanceof ElementEnum)
		{
			itemStack = ElementItem.createStackOf(ElementEnum.getByID(((ElementEnum) chemical).ordinal()), 1);
		} else if (chemical instanceof MoleculeEnum)
		{
			itemStack = new ItemStack(MinechemItemsRegistration.molecule, 1, ((MoleculeEnum) chemical).id());
		}
		return itemStack;
	}

	public static boolean canDrain(World world, Block block, int x, int y, int z)
	{
		if ((block == Blocks.water || block == Blocks.flowing_water) && world.getBlockMetadata(x, y, z) == 0)
		{
			return true;
		} else if (block instanceof IFluidBlock)
		{
			return ((IFluidBlock) block).canDrain(world, x, y, z);
		}

		return false;
	}

	public static MinechemChemicalType getChemical(Block block)
	{
		MinechemChemicalType chemical = null;
		if (block instanceof IFluidBlock)
		{
			Fluid fluid = ((IFluidBlock) block).getFluid();
			if (fluid instanceof FluidElement)
			{
				chemical = ((FluidElement) fluid).element;
			} else if (fluid instanceof FluidChemical)
			{
				chemical = ((FluidChemical) fluid).molecule;
			} else if (fluid == FluidRegistry.WATER)
			{
				chemical = MoleculeEnum.water;
			}
		} else if (block == Blocks.water || block == Blocks.flowing_water)
		{
			chemical = MoleculeEnum.water;
		}

		return chemical;
	}
}
