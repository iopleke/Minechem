package minechem.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import minechem.api.core.Chemical;
import minechem.api.core.Element;
import minechem.api.core.Molecule;
import minechem.api.util.Util;
import minechem.common.MinechemItems;
import minechem.common.items.ItemElement;
import minechem.common.items.ItemMolecule;
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
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.server.FMLServerHandler;

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
            NBTTagCompound itemstackCompound = (NBTTagCompound) taglist.tagAt(i);
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
            NBTTagCompound itemstackCompound = (NBTTagCompound) taglist.tagAt(i);
            ItemStack itemstack = ItemStack.loadItemStackFromNBT(itemstackCompound);
            itemlist.add(itemstack);
        }
        return itemlist;
    }

    public static ArrayList<ItemStack> convertChemicalsIntoItemStacks(ArrayList<Chemical> chemicals)
    {
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        if (chemicals == null)
            return stacks;
        for (Chemical chemical : chemicals)
        {
            if (chemical instanceof Element)
            {
                stacks.add(new ItemStack(MinechemItems.element, chemical.amount, ((Element) chemical).element.ordinal()));
            }
            else if (chemical instanceof Molecule)
            {
                stacks.add(new ItemStack(MinechemItems.molecule, chemical.amount, ((Molecule) chemical).molecule.ordinal()));
            }
        }
        return stacks;
    }

    public static ItemStack[] convertChemicalArrayIntoItemStackArray(Chemical[] chemicals)
    {
        ItemStack[] stacks = new ItemStack[chemicals.length];
        for (int i = 0; i < chemicals.length; i++)
        {
            Chemical chemical = chemicals[i];
            if (chemical instanceof Element)
            {
                stacks[i] = new ItemStack(MinechemItems.element, chemical.amount, ((Element) chemical).element.ordinal());
            }
            else if (chemical instanceof Molecule)
            {
                stacks[i] = new ItemStack(MinechemItems.molecule, chemical.amount, ((Molecule) chemical).molecule.ordinal());
            }
        }
        return stacks;
    }

    public static boolean itemStackMatchesChemical(ItemStack itemstack, Chemical chemical)
    {
        return itemStackMatchesChemical(itemstack, chemical, 1);
    }

    public static boolean itemStackMatchesChemical(ItemStack itemstack, Chemical chemical, int factor)
    {
        if (chemical instanceof Element && itemstack.itemID == MinechemItems.element.itemID)
        {
            Element element = (Element) chemical;
            return (itemstack.getItemDamage() == element.element.ordinal()) && (itemstack.stackSize >= element.amount * factor);
        }
        if (chemical instanceof Molecule && itemstack.itemID == MinechemItems.molecule.itemID)
        {
            Molecule molecule = (Molecule) chemical;
            return (itemstack.getItemDamage() == molecule.molecule.ordinal()) && (itemstack.stackSize >= molecule.amount * factor);
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
                randomN = itemstack.stackSize;
            itemstack.stackSize -= randomN;
            new EntityItem(world, x + randomX, y + randomY, z + randomZ, new ItemStack(itemstack.itemID, randomN, itemstack.getItemDamage()));

        }
    }

    /** Ensures that the given inventory is the full inventory, i.e. takes double chests into account.
     * 
     * @param inv
     * @return Modified inventory if double chest, unmodified otherwise. Credit to Buildcraft. */
    public static IInventory getInventory(IInventory inv)
    {
        if (inv instanceof TileEntityChest)
        {
            TileEntityChest chest = (TileEntityChest) inv;
            Position pos = new Position(chest.xCoord, chest.yCoord, chest.zCoord);
            TileEntity tile;
            IInventory chest2 = null;
            tile = getTile(chest.worldObj, pos, ForgeDirection.WEST);
            if (tile instanceof TileEntityChest)
                chest2 = (IInventory) tile;
            tile = getTile(chest.worldObj, pos, ForgeDirection.EAST);
            if (tile instanceof TileEntityChest)
                chest2 = (IInventory) tile;
            tile = getTile(chest.worldObj, pos, ForgeDirection.NORTH);
            if (tile instanceof TileEntityChest)
                chest2 = (IInventory) tile;
            tile = getTile(chest.worldObj, pos, ForgeDirection.SOUTH);
            if (tile instanceof TileEntityChest)
                chest2 = (IInventory) tile;
            if (chest2 != null)
                return new InventoryLargeChest("", inv, chest2);
        }
        return inv;
    }

    public static TileEntity getTile(World world, Position pos, ForgeDirection dir)
    {
        Position tmp = new Position(pos);
        tmp.orientation = dir;
        tmp.moveForwards(1.0);

        return world.getBlockTileEntity((int) tmp.x, (int) tmp.y, (int) tmp.z);
    }

    @SideOnly(Side.SERVER)
    public static WorldServer getDimension(int dimensionID)
    {
        WorldServer[] worlds = FMLServerHandler.instance().getServer().worldServers;
        for (WorldServer world : worlds)
        {
            if (world.provider.dimensionId == dimensionID)
                return world;
        }
        return null;
    }

    public static String getChemicalName(Chemical chemical)
    {
        if (chemical instanceof Element)
            return ((Element) chemical).element.descriptiveName();
        else
            return ((Molecule) chemical).molecule.descriptiveName();
    }

    public static ItemStack chemicalToItemStack(Chemical chemical, int amount)
    {
        if (chemical instanceof Element)
            return new ItemStack(MinechemItems.element, amount, ((Element) chemical).element.ordinal());
        else if (chemical instanceof Molecule)
            return new ItemStack(MinechemItems.molecule, amount, ((Molecule) chemical).molecule.id());
        return null;
    }

    public static Chemical itemStackToChemical(ItemStack itemstack)
    {
        if (Util.isStackAnElement(itemstack))
        {
            return new Element(ItemElement.getElement(itemstack), itemstack.stackSize);
        }
        else if (Util.isStackAMolecule(itemstack))
        {
            return new Molecule(ItemMolecule.getMolecule(itemstack), itemstack.stackSize);
        }
        return null;
    }
}
