package minechem.utils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.server.FMLServerHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import minechem.MinechemItemsRegistration;
import minechem.item.element.Element;
import minechem.item.element.ElementItem;
import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeItem;
import minechem.potion.PotionChemical;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;

public class MinechemHelper
{
	public static Random random = new Random();

	public static int getSplitStringHeight(FontRenderer fontRenderer, String string, int width)
	{
		List<?> stringRows = fontRenderer.listFormattedStringToWidth(string, width);
		return stringRows.size() * fontRenderer.FONT_HEIGHT;
	}

	public static float translateValue(float value, float leftMin, float leftMax, float rightMin, float rightMax)
	{
		float leftRange = leftMax - leftMin;
		float rightRange = rightMax - rightMin;
		float valueScaled = (value - leftMin) / leftRange;
		return rightMin + (valueScaled * rightRange);
	}

	public static String getLocalString(String key)
	{
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
		{
			String localString = StatCollector.translateToLocal(key);
			if (localString.equals(""))
			{
				return key;
			}
			return localString;
		}
		return key;
	}

	public static NBTTagList writeItemStackArrayToTagList(ItemStack[] itemstacks)
	{
		NBTTagList taglist = new NBTTagList();
		for (int slot = 0; slot < itemstacks.length; slot++)
		{
			ItemStack itemstack = itemstacks[slot];
			if (itemstack != null)
			{
				NBTTagCompound itemstackCompound = new NBTTagCompound();
				itemstackCompound.setByte("slot", (byte) slot);
				itemstack.writeToNBT(itemstackCompound);
				taglist.appendTag(itemstackCompound);
			}
		}
		return taglist;
	}

	public static ItemStack[] readTagListToItemStackArray(NBTTagList taglist, ItemStack[] itemstacks)
	{
		for (int i = 0; i < taglist.tagCount(); i++)
		{
			NBTTagCompound itemstackCompound = taglist.getCompoundTagAt(i);
			byte slot = itemstackCompound.getByte("slot");
			itemstacks[slot] = ItemStack.loadItemStackFromNBT(itemstackCompound);
		}
		return itemstacks;
	}

	public static NBTTagList writeItemStackListToTagList(ArrayList<ItemStack> list)
	{
		NBTTagList taglist = new NBTTagList();
		for (ItemStack itemstack : list)
		{
			NBTTagCompound itemstackCompound = new NBTTagCompound();
			itemstack.writeToNBT(itemstackCompound);
			taglist.appendTag(itemstackCompound);
		}
		return taglist;
	}

	public static ArrayList<ItemStack> readTagListToItemStackList(NBTTagList taglist)
	{
		ArrayList<ItemStack> itemlist = new ArrayList<ItemStack>();
		for (int i = 0; i < taglist.tagCount(); i++)
		{
			NBTTagCompound itemstackCompound = taglist.getCompoundTagAt(i);
			ItemStack itemstack = ItemStack.loadItemStackFromNBT(itemstackCompound);
			itemlist.add(itemstack);
		}
		return itemlist;
	}

	public static ArrayList<ItemStack> convertChemicalsIntoItemStacks(ArrayList<PotionChemical> potionChemicals)
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		if (potionChemicals == null)
		{
			return stacks;
		}
		for (PotionChemical potionChemical : potionChemicals)
		{
			if (potionChemical instanceof Element)
			{
				stacks.add(new ItemStack(MinechemItemsRegistration.element, potionChemical.amount, ((Element) potionChemical).element.ordinal()));
			} else if (potionChemical instanceof Molecule)
			{
				stacks.add(new ItemStack(MinechemItemsRegistration.molecule, potionChemical.amount, ((Molecule) potionChemical).molecule.id()));
			}
		}
		return stacks;
	}

	public static List<ItemStack> pushTogetherStacks(List<ItemStack> stacks)
	{
		// i slot to move
		for (int i = stacks.size() - 1; i >= 0; i--)
		{
			if (stacks.get(i) == null)
			{
				continue;
			}
			// spot for move
			for (int j = 0; j < i; j++)
			{
				// empty spot
				if (stacks.get(j) == null)
				{
					stacks.set(j, stacks.get(i));
					stacks.set(j, null);
					break;
				} // same stack
				else if (stacks.get(j).isItemEqual(stacks.get(i)))
				{
					stacks.get(j).stackSize += stacks.get(i).stackSize;
					stacks.set(i, null);
					break;
				}
			}
		}
		stacks.removeAll(Collections.singleton(null));
		return stacks;
	}

	public static ItemStack[] convertChemicalArrayIntoItemStackArray(PotionChemical[] chemicals)
	{
		ItemStack[] stacks = new ItemStack[chemicals.length];
		for (int i = 0; i < chemicals.length; i++)
		{
			PotionChemical potionChemical = chemicals[i];
			if (potionChemical instanceof Element)
			{
				stacks[i] = new ItemStack(MinechemItemsRegistration.element, potionChemical.amount, ((Element) potionChemical).element.ordinal());
			} else if (potionChemical instanceof Molecule)
			{
				stacks[i] = new ItemStack(MinechemItemsRegistration.molecule, potionChemical.amount, ((Molecule) potionChemical).molecule.id());
			}
		}
		return stacks;
	}

	public static boolean itemStackMatchesChemical(ItemStack itemstack, PotionChemical potionChemical)
	{
		return itemStackMatchesChemical(itemstack, potionChemical, 1);
	}

	public static boolean itemStackMatchesChemical(ItemStack itemstack, PotionChemical potionChemical, int factor)
	{
		if (potionChemical instanceof Element && itemstack.getItem() == MinechemItemsRegistration.element)
		{
			Element element = (Element) potionChemical;
			return (itemstack.getItemDamage() == element.element.ordinal()) && (itemstack.stackSize >= element.amount * factor);
		}
		if (potionChemical instanceof Molecule && itemstack.getItem() == MinechemItemsRegistration.molecule)
		{
			Molecule molecule = (Molecule) potionChemical;
			return (itemstack.getItemDamage() == molecule.molecule.id()) && (itemstack.stackSize >= molecule.amount * factor);
		}
		return false;
	}

	public static ForgeDirection getDirectionFromFacing(int facing)
	{
		switch (facing)
		{
			case 0:
				return ForgeDirection.SOUTH;
			case 1:
				return ForgeDirection.WEST;
			case 2:
				return ForgeDirection.NORTH;
			case 3:
				return ForgeDirection.EAST;
			default:
				return null;
		}
	}

	public static void ejectItemStackIntoWorld(ItemStack itemstack, World world, int x, int y, int z)
	{
		float randomX = random.nextFloat() * 0.8F + 0.1F;
		float randomY = random.nextFloat() * 0.8F + 0.1F;
		float randomZ = random.nextFloat() * 0.8F + 0.1F;
		while (itemstack.stackSize > 0)
		{
			int randomN = random.nextInt(21) + 10;
			if (randomN > itemstack.stackSize)
			{
				randomN = itemstack.stackSize;
			}
			itemstack.stackSize -= randomN;
			new EntityItem(world, x + randomX, y + randomY, z + randomZ, new ItemStack(itemstack.getItem(), randomN, itemstack.getItemDamage()));

		}
	}

	/**
	 * Ensures that the given inventory is the full inventory, i.e. takes double chests into account.
	 *
	 * @param inv
	 * @return Modified inventory if double chest, unmodified otherwise. Credit to Buildcraft.
	 */
	public static IInventory getInventory(IInventory inv)
	{
		if (inv instanceof TileEntityChest)
		{
			TileEntityChest chest = (TileEntityChest) inv;
			Position pos = new Position(chest.xCoord, chest.yCoord, chest.zCoord);
			TileEntity tile;
			IInventory chest2 = null;
			tile = getTile(chest.getWorldObj(), pos, ForgeDirection.WEST);
			if (tile instanceof TileEntityChest)
			{
				chest2 = (IInventory) tile;
			}
			tile = getTile(chest.getWorldObj(), pos, ForgeDirection.EAST);
			if (tile instanceof TileEntityChest)
			{
				chest2 = (IInventory) tile;
			}
			tile = getTile(chest.getWorldObj(), pos, ForgeDirection.NORTH);
			if (tile instanceof TileEntityChest)
			{
				chest2 = (IInventory) tile;
			}
			tile = getTile(chest.getWorldObj(), pos, ForgeDirection.SOUTH);
			if (tile instanceof TileEntityChest)
			{
				chest2 = (IInventory) tile;
			}
			if (chest2 != null)
			{
				return new InventoryLargeChest("", inv, chest2);
			}
		}
		return inv;
	}

	public static TileEntity getTile(World world, Position pos, ForgeDirection dir)
	{
		Position tmp = new Position(pos);
		tmp.orientation = dir;
		tmp.moveForwards(1.0);

		return world.getTileEntity((int) tmp.x, (int) tmp.y, (int) tmp.z);
	}

	@SideOnly(Side.SERVER)
	public static WorldServer getDimension(int dimensionID)
	{
		WorldServer[] worlds = FMLServerHandler.instance().getServer().worldServers;
		for (WorldServer world : worlds)
		{
			if (world.provider.dimensionId == dimensionID)
			{
				return world;
			}
		}
		return null;
	}

	public static String getChemicalName(PotionChemical potionChemical)
	{
		if (potionChemical instanceof Element)
		{
			return getLocalString(((Element) potionChemical).element.name());
		} else
		{
			return getLocalString(((Molecule) potionChemical).molecule.name());
		}
	}

	public static ItemStack chemicalToItemStack(PotionChemical potionChemical, int amount)
	{
		if (potionChemical instanceof Element)
		{
			return new ItemStack(MinechemItemsRegistration.element, amount, ((Element) potionChemical).element.ordinal());
		} else if (potionChemical instanceof Molecule)
		{
			return new ItemStack(MinechemItemsRegistration.molecule, amount, ((Molecule) potionChemical).molecule.id());
		}
		return null;
	}

	public static PotionChemical itemStackToChemical(ItemStack itemstack)
	{
		if (Compare.isStackAnElement(itemstack))
		{
			return new Element(ElementItem.getElement(itemstack), itemstack.stackSize);
		} else if (Compare.isStackAMolecule(itemstack))
		{
			return new Molecule(MoleculeItem.getMolecule(itemstack), itemstack.stackSize);
		}
		return null;
	}
}
