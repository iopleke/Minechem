package minechem.utils;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import minechem.MinechemItemsRegistration;
import minechem.Settings;
import minechem.fluid.FluidElement;
import minechem.fluid.FluidMolecule;
import minechem.item.MinechemChemicalType;
import minechem.item.element.Element;
import minechem.item.element.ElementEnum;
import minechem.item.element.ElementItem;
import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import minechem.potion.PotionChemical;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;
import com.google.common.base.Objects;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.server.FMLServerHandler;

public final class MinechemUtil
{

    public static final Random random = new Random();

    private MinechemUtil()
    {
    }

    /**
     * Adds an item stack to the inventory.
     * <p>
     * This method won't modify the given item stack.
     *
     * @param inventory
     * @param itemStack
     * @return the rest of the item stack, null if the whole item stack is added into the inventory
     */
    public static ItemStack addItemToInventory(IInventory inventory, ItemStack itemStack)
    {
        if (itemStack == null)
        {
            return null;
        }

        int amount = itemStack.stackSize;
        int inventorySize = inventory.getSizeInventory();
        int maxStackSize = Math.min(inventory.getInventoryStackLimit(), itemStack.getItem().getItemStackLimit(itemStack));
        for (int i = 0; (i < inventorySize) && (amount > 0); i++)
        {
            ItemStack target = inventory.getStackInSlot(i);
            if (target == null)
            {
                int appendAmount = Math.min(amount, maxStackSize);
                amount -= appendAmount;
                ItemStack append = itemStack.copy();
                append.stackSize = appendAmount;
                inventory.setInventorySlotContents(i, append);
            } else
            {
                if (canItemStackMerge(target, itemStack) && (target.stackSize < maxStackSize))
                {
                    int appendAmount = Math.min(amount, maxStackSize - amount);
                    amount -= appendAmount;
                    target.stackSize += appendAmount;
                    inventory.setInventorySlotContents(i, target);
                }
            }
        }

        if (amount <= 0)
        {
            return null;
        }

        ItemStack remaining = itemStack.copy();
        remaining.stackSize = amount;
        return remaining;
    }

    /**
     * Returns true if two item stacks can be merged.
     *
     * @param a
     * @param b
     * @return true if two item stacks can be merged
     */
    public static boolean canItemStackMerge(ItemStack a, ItemStack b)
    {
        return (a.getItem() == b.getItem()) && (a.getItemDamage() == b.getItemDamage()) && Objects.equal(a.stackTagCompound, b.stackTagCompound);
    }

    /**
     * Throws the given item stack around the given location.
     *
     * @param world
     * @param itemStack
     * @param x
     * @param y
     * @param z
     */
    public static void throwItemStack(World world, ItemStack itemStack, double x, double y, double z)
    {
        if (itemStack != null)
        {
            float f = (random.nextFloat() * 0.8F) + 0.1F;
            float f1 = (random.nextFloat() * 0.8F) + 0.1F;
            float f2 = (random.nextFloat() * 0.8F) + 0.1F;

            EntityItem entityitem = new EntityItem(world, (float) x + f, (float) y + f1, (float) z + f2, itemStack);
            float f3 = 0.05F;
            entityitem.motionX = (float) random.nextGaussian() * f3;
            entityitem.motionY = ((float) random.nextGaussian() * f3) + 0.2F;
            entityitem.motionZ = (float) random.nextGaussian() * f3;
            world.spawnEntityInWorld(entityitem);
        }
    }

    /**
     * Creates a stack of tubes filled with the given chemical.
     *
     * @param chemical
     * @param amount
     *            the amount of the item stack
     * @return a stack of tubes filled with the given chemical, null if the stack cannot be created
     */
    public static ItemStack chemicalToItemStack(MinechemChemicalType chemical, int amount)
    {
        if (chemical instanceof ElementEnum)
        {
            return new ItemStack(MinechemItemsRegistration.element, amount, ((ElementEnum) chemical).atomicNumber());
        } else if (chemical instanceof MoleculeEnum)
        {
            return new ItemStack(MinechemItemsRegistration.molecule, amount, ((MoleculeEnum) chemical).id());
        }
        return null;
    }

    /**
     * Creates a stack of tubes filled with the given chemical. The amount of the stack is the amount of potionChemical.
     *
     * @param potionChemical
     * @return a stack of tubes filled with the given chemical
     */
    public static ItemStack chemicalToItemStack(PotionChemical potionChemical)
    {
        if (potionChemical instanceof Element)
        {
            return new ItemStack(MinechemItemsRegistration.element, potionChemical.amount, ((Element) potionChemical).element.atomicNumber());
        } else if (potionChemical instanceof Molecule)
        {
            return new ItemStack(MinechemItemsRegistration.molecule, potionChemical.amount, ((Molecule) potionChemical).molecule.id());
        }
        return null;
    }

    /**
     * Creates a PotionChemical.
     *
     * @param chemical
     *            the chemical type
     * @param amount
     *            the amount
     * @return
     */
    public static PotionChemical createPotionChemical(MinechemChemicalType chemical, int amount)
    {
        if (chemical instanceof ElementEnum)
        {
            return new Element((ElementEnum) chemical, amount);
        } else if (chemical instanceof MoleculeEnum)
        {
            return new Molecule((MoleculeEnum) chemical, amount);
        }
        return null;
    }

    /**
     * Returns true if the fluid block at the given location can be drained.
     *
     * @param world
     * @param block
     * @param x
     * @param y
     * @param z
     * @return true if the fluid block at the given location can be drained
     */
    public static boolean canDrain(World world, Block block, int x, int y, int z)
    {
        if (((block == Blocks.water) || (block == Blocks.flowing_water)) && (world.getBlockMetadata(x, y, z) == 0))
        {
            return true;
        } else if (block instanceof IFluidBlock)
        {
            return ((IFluidBlock) block).canDrain(world, x, y, z);
        }

        return false;
    }

    /**
     * Gets the chemical of the given block.
     *
     * @param block
     * @return the chemical of the given block, null if the chemical cannot be gotten
     */
    public static MinechemChemicalType getChemical(Block block)
    {
        MinechemChemicalType chemical = null;
        if (block instanceof IFluidBlock)
        {
            Fluid fluid = ((IFluidBlock) block).getFluid();
            chemical = getChemical(fluid);
        } else if ((block == Blocks.water) || (block == Blocks.flowing_water))
        {
            chemical = MoleculeEnum.water;
        }

        return chemical;
    }

    /**
     * Gets the chemical of the given fluid.
     *
     * @param block
     * @return the chemical of the given fluid, null if the chemical cannot be gotten
     */
    public static MinechemChemicalType getChemical(Fluid fluid)
    {
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

    /**
     * Gets the chemical type of the given PotionChemical.
     * <p>
     * <b>If chemical is a PotionChemical, the method will return null.</b>
     *
     * @param chemical
     * @return the chemical type of the given PotionChemical, null if chemical==null or the chemical is not an Element or a Molecule
     */
    public static MinechemChemicalType getChemical(PotionChemical chemical)
    {
        if (chemical instanceof Element)
        {
            return ((Element) chemical).element;
        } else if (chemical instanceof Molecule)
        {
            return ((Molecule) chemical).molecule;
        }
        return null;
    }

    /**
     * Gets the chemical of the given item stack.
     *
     * @param itemStack
     * @return the chemical of the given item stack, null if the chemical cannot be gotten
     */
    public static MinechemChemicalType getChemical(ItemStack itemStack)
    {
        if (itemStack.getItem() instanceof ElementItem)
        {
            if (itemStack.getItemDamage() == 0)
            {
                return null;
            }
            return ElementItem.getElement(itemStack);
        } else if (itemStack.getItem() instanceof MoleculeItem)
        {
            return MoleculeItem.getMolecule(itemStack);
        }
        return null;
    }

    /**
     * Gets the fluid in the given IFluidHandler.
     *
     * @param te
     * @return the fluid in the given IFluidHandler, null if there is no fluid in it
     */
    public static Fluid getFluid(IFluidHandler te)
    {
        FluidTankInfo[] tanks = null;
        for (int i = 0; i < 6; i++)
        {
            tanks = te.getTankInfo(ForgeDirection.getOrientation(i));
            if (tanks != null)
            {
                for (FluidTankInfo tank : tanks)
                {
                    if ((tank != null) && (tank.fluid != null))
                    {
                        return tank.fluid.getFluid();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Adds the given item stack into the inventory of the player.
     * <p>
     * If the amount of item stack is negative, the method will remove the stacks from the inventory. If the item stack is not added fully. the method will throw the rest of the item stack around the player.
     *
     * @param current
     * @param inc
     * @param player
     * @param give
     */
    public static void incPlayerInventory(ItemStack current, int inc, EntityPlayer player, ItemStack give)
    {
        if (inc < 0)
        {
            current.splitStack(-inc);
        } else if (inc > 0)
        {
            if ((current.stackSize + inc) <= current.getMaxStackSize())
            {
                current.stackSize += inc;
            } else
            {
                int added = current.getMaxStackSize() - current.stackSize;
                current.stackSize = current.getMaxStackSize();
                ItemStack extraStack = current.copy();
                extraStack.stackSize = inc - added;
                if (!player.inventory.addItemStackToInventory(extraStack))
                {
                    player.dropPlayerItemWithRandomChoice(extraStack, false);
                }
            }
        }

        if (!player.inventory.addItemStackToInventory(give))
        {
            player.dropPlayerItemWithRandomChoice(give, false);
        }
    }

    /**
     * Removes the item stacks with specify item and damage in the given inventory.
     * And returns them as a set.
     * <p>
     * If there's no enough items in the inventory, this method will return null, and won't modify any slot.
     *
     * @param inventory
     * @param item
     * @param damage
     * @param amount
     *            how many items should be removed
     * @return a set of the removed items, null if there's no enough items in the inventory
     */
    public static Set<ItemStack> removeItemStacksFromInventory(IInventory inventory, Item item, int damage, int amount)
    {
        int total = 0;
        for (int i = 0; (i < inventory.getSizeInventory()) && (total < amount); i++)
        {
            ItemStack itemStack = inventory.getStackInSlot(i);
            if ((itemStack != null) && (itemStack.getItem() == item) && (itemStack.getItemDamage() == damage))
            {
                total += itemStack.stackSize;
            }
        }
        if (total < amount)
        {
            return null;
        }

        Set<ItemStack> result = new LinkedHashSet<ItemStack>();
        for (int i = 0; (i < inventory.getSizeInventory()) && (amount > 0); i++)
        {
            ItemStack itemStack = inventory.getStackInSlot(i);
            if ((itemStack != null) && (itemStack.getItem() == item) && (itemStack.getItemDamage() == damage))
            {
                int cost = Math.min(amount, itemStack.stackSize);
                // store how many items we still need in 'amount'
                amount -= cost;
                itemStack.stackSize -= cost;
                inventory.setInventorySlotContents(i, itemStack.stackSize > 0 ? itemStack : null);
            }
        }
        return result;
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
        for (String string : stringInputs)
        {
            if ((string == null) || string.equals(""))
            {
                continue;
            }
            String[] splitString = string.split(":");
            ArrayList<String> wildcardMatch = new ArrayList<String>();
            if ((splitString.length < 2) || (splitString.length > 3))
            {
                LogHelper.debug(string + " is an invalid blacklist input");
                continue;
            }
            if (splitString[0].equals("ore"))
            {
                String itemID = splitString[1];
                if (itemID.contains("*"))
                {
                    itemID = itemID.replaceAll("\\*", ".*");
                }
                Pattern itemPattern = Pattern.compile(itemID, Pattern.CASE_INSENSITIVE);
                for (String item : OreDictionary.getOreNames())
                {
                    if (itemPattern.matcher(item).matches())
                    {
                        wildcardMatch.add(item);
                    }
                }
                if (wildcardMatch.isEmpty())
                {
                    LogHelper.debug(splitString[1] + " has no matches in the OreDictionary");
                    continue;
                }
                for (String key : wildcardMatch)
                {
                    decomposerBlacklist.addAll(OreDictionary.getOres(key));
                }
            } else
            {
                int meta;
                try
                {
                    meta = splitString.length == 3 ? Integer.valueOf(splitString[2]) : Short.MAX_VALUE;
                } catch (NumberFormatException e)
                {
                    if (splitString[2].equals("*"))
                    {
                        meta = Short.MAX_VALUE;
                    } else
                    {
                        LogHelper.debug(splitString[2] + " is an invalid damage value - defaulting to all values");
                        meta = Short.MAX_VALUE;
                    }
                }
                String itemID = splitString[0] + ":" + splitString[1];
                if (itemID.contains("*"))
                {
                    itemID = itemID.replaceAll("\\*", ".*");
                }
                Pattern itemPattern = Pattern.compile(itemID, Pattern.CASE_INSENSITIVE);
                for (String item : ids)
                {
                    if (itemPattern.matcher(item).matches())
                    {
                        wildcardMatch.add(item);
                    }
                }
                if (wildcardMatch.isEmpty())
                {
                    LogHelper.debug(string + " has no matches in the ItemRegistry");
                    continue;
                }
                for (String key : wildcardMatch)
                {
                    Object disable = GameData.getItemRegistry().getObject(key);
                    if (disable instanceof Item)
                    {
                        decomposerBlacklist.add(new ItemStack(((Item) disable), 1, meta));
                    } else if (disable instanceof Block)
                    {
                        decomposerBlacklist.add(new ItemStack(((Block) disable), 1, meta));
                    }
                }
            }
        }
    }

    public static void populateBlacklists()
    {
        Settings.decomposerBlacklist = new ArrayList<ItemStack>();
        Settings.synthesisBlacklist = new ArrayList<ItemStack>();

        Settings.decomposerBlacklist.add(MinechemItemsRegistration.emptyTube);

        ArrayList<String> registeredItems = new ArrayList<String>();
        for (Object key : GameData.getItemRegistry().getKeys())
        {
            registeredItems.add((String) key);
        }
        addDisabledStacks(Settings.DecomposerBlacklist, Settings.decomposerBlacklist, registeredItems);
        addDisabledStacks(Settings.SynthesisMachineBlacklist, Settings.synthesisBlacklist, registeredItems);
    }

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
        return getLocalString(key, false);
    }

    public static String getLocalString(String key, boolean capitalize)
    {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            String localString = StatCollector.translateToLocal(key);
            return capitalize ? capitalizeFully(localString.replaceAll("molecule\\.", "")) : localString;
        }
        return key;
    }

    public static String capitalizeFully(String input)
    {
        String[] splitString = input.split(" ");
        String result = "";
        for (int i = 0; i < splitString.length; i++)
        {
            char[] digit = splitString[i].toCharArray();
            if (digit.length < 1)
            {
                continue;
            }
            digit[0] = Character.toUpperCase(digit[0]);
            for (int j = 1; j < digit.length; j++)
            {
                digit[j] = Character.toLowerCase(digit[j]);
            }
            result += new String(digit) + (i < (splitString.length - 1) ? " " : "");
        }
        return result;
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
        if ((potionChemicals != null) && (potionChemicals.size() > 0))
        {
            for (PotionChemical potionChemical : potionChemicals)
            {
                if ((potionChemical instanceof Element) && (((Element) potionChemical).element != null))
                {
                    stacks.add(new ItemStack(MinechemItemsRegistration.element, potionChemical.amount, ((Element) potionChemical).element.atomicNumber()));
                } else if ((potionChemical instanceof Molecule) && (((Molecule) potionChemical).molecule != null))
                {
                    stacks.add(new ItemStack(MinechemItemsRegistration.molecule, potionChemical.amount, ((Molecule) potionChemical).molecule.id()));
                }
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
        if (chemicals == null)
        {
            return new ItemStack[0];
        }

        ItemStack[] stacks = new ItemStack[chemicals.length];
        for (int i = 0; i < chemicals.length; i++)
        {
            PotionChemical potionChemical = chemicals[i];
            if (potionChemical instanceof Element)
            {
                stacks[i] = new ItemStack(MinechemItemsRegistration.element, potionChemical.amount, ((Element) potionChemical).element.atomicNumber());
            } else if (potionChemical instanceof Molecule)
            {
                stacks[i] = new ItemStack(MinechemItemsRegistration.molecule, potionChemical.amount, ((Molecule) potionChemical).molecule.id());
            }
        }
        return stacks;
    }

    public static ArrayList<PotionChemical> pushTogetherChemicals(ArrayList<PotionChemical> oldList)
    {
        ArrayList<PotionChemical> list = new ArrayList<PotionChemical>();
        for (PotionChemical chemical : oldList)
        {
            list.add(chemical.copy());
        }
        for (int i = list.size() - 1; i >= 0; i--)
        {
            if (list.get(i) == null)
            {
                continue;
            }
            // spot for move
            for (int j = 0; j < i; j++)
            {
                // empty spot
                if (list.get(j) == null)
                {
                    list.set(j, list.get(i));
                    list.set(j, null);
                    break;
                } // same stack
                else if (list.get(j).sameAs(list.get(i)))
                {
                    list.get(j).amount += list.get(i).amount;
                    list.set(i, null);
                    break;
                }
            }
        }
        list.removeAll(Collections.singleton(null));
        return list;
    }

    public static boolean itemStackMatchesChemical(ItemStack itemstack, PotionChemical potionChemical)
    {
        return itemStackMatchesChemical(itemstack, potionChemical, 1);
    }

    public static boolean itemStackMatchesChemical(ItemStack itemstack, PotionChemical potionChemical, int factor)
    {
        if ((potionChemical instanceof Element) && (itemstack.getItem() == MinechemItemsRegistration.element))
        {
            Element element = (Element) potionChemical;
            return (itemstack.getItemDamage() == element.element.atomicNumber()) && (itemstack.stackSize >= (element.amount * factor));
        }
        if ((potionChemical instanceof Molecule) && (itemstack.getItem() == MinechemItemsRegistration.molecule))
        {
            Molecule molecule = (Molecule) potionChemical;
            return (itemstack.getItemDamage() == molecule.molecule.id()) && (itemstack.stackSize >= (molecule.amount * factor));
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
            return getLocalString(((Element) potionChemical).element.name(), true);
        }
        return getLocalString(((Molecule) potionChemical).molecule.name(), true);
    }

    public static PotionChemical itemStackToChemical(ItemStack itemstack)
    {
        if (Compare.isStackAnElement(itemstack))
        {
            if (itemstack.getItemDamage() == 0)
            {
                return null;
            }
            return new Element(ElementItem.getElement(itemstack), itemstack.stackSize);
        } else if (Compare.isStackAMolecule(itemstack))
        {
            return new Molecule(MoleculeItem.getMolecule(itemstack), itemstack.stackSize);
        }
        return null;
    }

    public static int getNumberOfDigits(int n)
    {
        return (int) (Math.log10(n) + 1);
    }

    /**
     * Opens passed in URL, MUST check
     * FMLClientHandler.instance().getClient(),mc.gameSettings.chatLinksPrompt
     * before using.
     *
     * @param url
     */
    public static void openURL(String url)
    {
        try
        {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
            oclass.getMethod("browse", new Class[]
            {
                    URI.class
            }).invoke(object, new Object[]
            {
                            new URI(url)
            });
        } catch (Throwable throwable)
        {
            LogHelper.debug("Couldn't open link: " + url);
        }
    }

}
