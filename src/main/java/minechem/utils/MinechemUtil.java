package minechem.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import cpw.mods.fml.common.registry.GameData;
import minechem.MinechemItemsRegistration;
import minechem.Settings;
import minechem.fluid.FluidElement;
import minechem.fluid.FluidHelper;
import minechem.fluid.FluidMolecule;
import minechem.item.MinechemChemicalType;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeEnum;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;

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
				ItemStack newStack = itemStack.copy();
				newStack.stackSize = append;
				inventory.setInventorySlotContents(i, newStack);
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
			itemStack = ElementItem.createStackOf(ElementEnum.getByID(((ElementEnum) chemical).ordinal()), amount);
		} else if (chemical instanceof MoleculeEnum)
		{
			itemStack = new ItemStack(MinechemItemsRegistration.molecule, amount, ((MoleculeEnum) chemical).id());
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
			chemical = getChemical(fluid);
		} else if (block == Blocks.water || block == Blocks.flowing_water)
		{
			chemical = MoleculeEnum.water;
		}

		return chemical;
	}
	
	public static MinechemChemicalType getChemical(Fluid fluid){
		if (fluid instanceof FluidElement)
		{
			return ((FluidElement) fluid).element;
		} else if (fluid instanceof FluidMolecule)
		{
			return ((FluidMolecule) fluid).molecule;
		} else if (fluid == FluidRegistry.WATER)
		{
			return MoleculeEnum.water;
		}
		return null;
	}
	
	public static ElementEnum getElement(Fluid fluid)
	{
		for (Map.Entry<ElementEnum, FluidElement> entry:FluidHelper.elements.entrySet())
		{
			if (entry.getValue()==fluid)
				return entry.getKey();
		}
		return null;
	}
	
	public static MoleculeEnum getMolecule(Fluid fluid)
	{
		for (Entry<MoleculeEnum, FluidMolecule> entry:FluidHelper.molecules.entrySet())
		{
			if (entry.getValue()==fluid)
				return entry.getKey();
		}
		return null;
	}
	
	public static Fluid getFluid(IFluidHandler te) {
		FluidTankInfo[] tanks = null;
		for (int i=0;i<6;i++)
		{
			tanks = te.getTankInfo(ForgeDirection.getOrientation(i));
			if (tanks!=null)
				for (FluidTankInfo tank:tanks)
				{
					if (tank!=null&&tank.fluid!=null)
						return tank.fluid.getFluid();
				}
		}	
		return null;
	}
	
	public static void scanForMoreStacks(ItemStack current, EntityPlayer player)
	{
		int getMore = 8-current.stackSize;
		InventoryPlayer inventory = player.inventory;
		int maxSlot = player.inventory.getSizeInventory()-4;
		int slot=0;
		do
		{
			if (slot!=inventory.currentItem)
			{
				ItemStack slotStack = inventory.getStackInSlot(slot);
				if (slotStack!=null && slotStack.isItemEqual(current))
				{
					ItemStack addStack = inventory.decrStackSize(slot, getMore);
					current.stackSize+=addStack.stackSize;
					getMore-=addStack.stackSize;
				}
			}
			slot++;
		}while (getMore>0&&slot<maxSlot);
	}
	
	public static void incPlayerInventory(ItemStack current, int inc, EntityPlayer player, ItemStack give)
	{
		current.stackSize+=inc;
		if (!player.inventory.addItemStackToInventory(give))
		{
			player.dropPlayerItemWithRandomChoice(give, false);
		}
	}
	
	public static Set<ItemStack> findItemStacks(IInventory inventory,Item item,int damage){
		Set<ItemStack> stacks=new HashSet<ItemStack>();
		for (int i=0;i<inventory.getSizeInventory();i++){
			ItemStack stack=inventory.getStackInSlot(i);
			if (stack!=null&&stack.getItem()==item&&stack.getItemDamage()==damage){
				stacks.add(stack);
			}
		}
		
		return stacks;
	}
	
	public static void removeStackInInventory(IInventory inventory,ItemStack stack){
		for (int i=0;i<inventory.getSizeInventory();i++){
			if (stack==inventory.getStackInSlot(i)){	//don't change == to equals()
				inventory.setInventorySlotContents(i, null);
				break;
			}
		}
	}

	public static String subscriptNumbers(String string)
	{
		string = string.replace('0', '\u2080');
		string = string.replace('1', '\u2081');
		string = string.replace('2', '\u2082');
		string = string.replace('3', '\u2083');
		string = string.replace('4', '\u2084');
		string = string.replace('5', '\u2085');
		string = string.replace('6', '\u2086');
		string = string.replace('7', '\u2087');
		string = string.replace('8', '\u2088');
		string = string.replace('9', '\u2089');
		return string;
	}
	
	public static void addDisabledStacks(String[] stringInputs, ArrayList<ItemStack> decomposerBlacklist, ArrayList<String> ids)
	{
		for (String string:stringInputs)
		{
			String[] splitString = string.split(":");
			ArrayList<String> wildcardMatch=new ArrayList<String>();
			if (splitString.length<2||splitString.length>3)
			{
				LogHelper.debug(string + " is an invalid blacklist input");
				continue;
			}
			if (splitString[0].equals("ore"))
			{
				String itemID = splitString[1];
				if (itemID.contains("*"))itemID=itemID.replaceAll("\\*", ".*");
				Pattern itemPattern = Pattern.compile(itemID,Pattern.CASE_INSENSITIVE);
				for (String item:OreDictionary.getOreNames()){
					if (itemPattern.matcher(item).matches()) wildcardMatch.add(item);
				}
				if (wildcardMatch.isEmpty())
				{
					LogHelper.debug(splitString[1]+ " has no matches in the OreDictionary");
					continue;
				}
				for (String key:wildcardMatch)
				{
					decomposerBlacklist.addAll(OreDictionary.getOres(key));
				}
			}
			else
			{
				int meta;
				try{
					meta = splitString.length==3?Integer.valueOf(splitString[2]):0;
				}catch (NumberFormatException e)
				{
					LogHelper.debug(splitString[2] + " is an invalid damage value - defaulting to 0");
					meta=0;
				}
				String itemID = splitString[0]+":"+splitString[1];
				if (itemID.contains("*"))itemID=itemID.replaceAll("\\*", ".*");
				Pattern itemPattern = Pattern.compile(itemID,Pattern.CASE_INSENSITIVE);
				for (String item:ids){
					if (itemPattern.matcher(item).matches()) wildcardMatch.add(item);
				}
				if (wildcardMatch.isEmpty())
				{
					LogHelper.debug(string+ " has no matches in the ItemRegistry");
					continue;
				}
				for (String key:wildcardMatch)
				{
					Object disable = GameData.getItemRegistry().getObject(key);
					if (disable instanceof Item)
						decomposerBlacklist.add(new ItemStack(((Item)disable),1,meta));
					else if (disable instanceof Block)
						decomposerBlacklist.add(new ItemStack(((Block)disable),1,meta));
				}
			}
		}
	}

	public static void populateBlacklists()
	{
		Settings.decomposerBlacklist = new ArrayList<ItemStack>();
		Settings.synthesisBlacklist = new ArrayList<ItemStack>();
		
		ArrayList<String> registeredItems=new ArrayList<String>();
		for (Object key:GameData.getItemRegistry().getKeys()){
			registeredItems.add((String) key);
		}
		addDisabledStacks(Settings.DecomposerBlacklist,Settings.decomposerBlacklist,registeredItems);
		addDisabledStacks(Settings.SynthesisMachineBlacklist,Settings.synthesisBlacklist,registeredItems);
	}

}
