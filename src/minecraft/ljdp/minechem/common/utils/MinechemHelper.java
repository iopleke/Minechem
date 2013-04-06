package ljdp.minechem.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ljdp.minechem.api.core.Chemical;
import ljdp.minechem.api.core.Element;
import ljdp.minechem.api.core.EnumMolecule;
import ljdp.minechem.api.core.Molecule;
import ljdp.minechem.api.util.Constants;
import ljdp.minechem.api.util.Util;
import ljdp.minechem.common.MinechemItems;
import ljdp.minechem.common.items.ItemElement;
import ljdp.minechem.common.items.ItemMolecule;

import buildcraft.api.core.Position;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.server.FMLServerHandler;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeDirection;

public class MinechemHelper {

    public static Random random = new Random();

    public static float translateValue(float value, float leftMin, float leftMax, float rightMin, float rightMax) {
        float leftRange = leftMax - leftMin;
        float rightRange = rightMax - rightMin;
        float valueScaled = (value - leftMin) / leftRange;
        return rightMin + (valueScaled * rightRange);
    }

    public static int getSplitStringHeight(FontRenderer fontRenderer, String string, int width) {
        List<?> stringRows = fontRenderer.listFormattedStringToWidth(string, width);
        return stringRows.size() * fontRenderer.FONT_HEIGHT;
    }

    public static String getLocalString(String key) {
        LanguageRegistry lr = LanguageRegistry.instance();
        String localString = lr.getStringLocalization(key);
        if (localString.equals("")) {
            localString = lr.getStringLocalization(key, "en_GB");
        }
        return localString;
    }

    public static NBTTagList writeItemStackArrayToTagList(ItemStack[] itemstacks) {
        NBTTagList taglist = new NBTTagList();
        for (int slot = 0; slot < itemstacks.length; slot++) {
            ItemStack itemstack = itemstacks[slot];
            if (itemstack != null) {
                NBTTagCompound itemstackCompound = new NBTTagCompound();
                itemstackCompound.setByte("slot", (byte) slot);
                itemstack.writeToNBT(itemstackCompound);
                taglist.appendTag(itemstackCompound);
            }
        }
        return taglist;
    }

    public static ItemStack[] readTagListToItemStackArray(NBTTagList taglist, ItemStack[] itemstacks) {
        for (int i = 0; i < taglist.tagCount(); i++) {
            NBTTagCompound itemstackCompound = (NBTTagCompound) taglist.tagAt(i);
            byte slot = itemstackCompound.getByte("slot");
            itemstacks[slot] = ItemStack.loadItemStackFromNBT(itemstackCompound);
        }
        return itemstacks;
    }

    public static NBTTagList writeItemStackListToTagList(ArrayList<ItemStack> list) {
        NBTTagList taglist = new NBTTagList();
        for (ItemStack itemstack : list) {
            NBTTagCompound itemstackCompound = new NBTTagCompound();
            itemstack.writeToNBT(itemstackCompound);
            taglist.appendTag(itemstackCompound);
        }
        return taglist;
    }

    public static ArrayList<ItemStack> readTagListToItemStackList(NBTTagList taglist) {
        ArrayList<ItemStack> itemlist = new ArrayList<ItemStack>();
        for (int i = 0; i < taglist.tagCount(); i++) {
            NBTTagCompound itemstackCompound = (NBTTagCompound) taglist.tagAt(i);
            ItemStack itemstack = ItemStack.loadItemStackFromNBT(itemstackCompound);
            itemlist.add(itemstack);
        }
        return itemlist;
    }

    public static ArrayList<ItemStack> convertChemicalsIntoItemStacks(ArrayList<Chemical> chemicals) {
        ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        if (chemicals == null)
            return stacks;
        for (Chemical chemical : chemicals) {
            if (chemical instanceof Element) {
                stacks.add(new ItemStack(MinechemItems.element, chemical.amount, ((Element) chemical).element.ordinal()));
            } else if (chemical instanceof Molecule) {
                stacks.add(new ItemStack(MinechemItems.molecule, chemical.amount, ((Molecule) chemical).molecule.ordinal()));
            }
        }
        return stacks;
    }

    public static ItemStack[] convertChemicalArrayIntoItemStackArray(Chemical[] chemicals) {
        ItemStack[] stacks = new ItemStack[chemicals.length];
        for (int i = 0; i < chemicals.length; i++) {
            Chemical chemical = chemicals[i];
            if (chemical instanceof Element) {
                stacks[i] = new ItemStack(MinechemItems.element, chemical.amount, ((Element) chemical).element.ordinal());
            } else if (chemical instanceof Molecule) {
                stacks[i] = new ItemStack(MinechemItems.molecule, chemical.amount, ((Molecule) chemical).molecule.ordinal());
            }
        }
        return stacks;
    }

    public static boolean itemStackMatchesChemical(ItemStack itemstack, Chemical chemical) {
        return itemStackMatchesChemical(itemstack, chemical, 1);
    }

    public static boolean itemStackMatchesChemical(ItemStack itemstack, Chemical chemical, int factor) {
        if (chemical instanceof Element && itemstack.itemID == MinechemItems.element.itemID) {
            Element element = (Element) chemical;
            return (itemstack.getItemDamage() == element.element.ordinal()) && (itemstack.stackSize >= element.amount * factor);
        }
        if (chemical instanceof Molecule && itemstack.itemID == MinechemItems.molecule.itemID) {
            Molecule molecule = (Molecule) chemical;
            return (itemstack.getItemDamage() == molecule.molecule.ordinal()) && (itemstack.stackSize >= molecule.amount * factor);
        }
        return false;
    }

    public static ForgeDirection getDirectionFromFacing(int facing) {
        switch (facing) {
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

    public static void ejectItemStackIntoWorld(ItemStack itemstack, World world, int x, int y, int z) {
        float randomX = random.nextFloat() * 0.8F + 0.1F;
        float randomY = random.nextFloat() * 0.8F + 0.1F;
        float randomZ = random.nextFloat() * 0.8F + 0.1F;
        while (itemstack.stackSize > 0) {
            int randomN = random.nextInt(21) + 10;
            if (randomN > itemstack.stackSize)
                randomN = itemstack.stackSize;
            itemstack.stackSize -= randomN;
            new EntityItem(world, (double) ((float) x + randomX), (double) ((float) y + randomY), (double) ((float) z + randomZ), new ItemStack(
                    itemstack.itemID, randomN, itemstack.getItemDamage()));

        }
    }

    public static void triggerPlayerEffect(EnumMolecule molecule, EntityPlayer entityPlayer) {
        World world = entityPlayer.worldObj;
        // Extra effects by Mandrake.
        switch (molecule) {
        case water:
            entityPlayer.getFoodStats().addStats(1, .1F);
            break;
        case starch:
            entityPlayer.getFoodStats().addStats(2, .2F);
            break;
        case sucrose:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 5, 0));
            entityPlayer.getFoodStats().addStats(1, .1F);
            break;
        case psilocybin:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 30, 5));
            entityPlayer.attackEntityFrom(DamageSource.generic, 2);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Constants.TICKS_PER_SECOND * 30, 5));
            break;
        case amphetamine:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 20, 7));
            break;
        case methamphetamine:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_SECOND * 30, 7));
            break;
        case muscarine:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 60, 2));
            break;
        case poison:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 60, 2));
            break;
        case ethanol:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 10, 1));
            entityPlayer.getFoodStats().addStats(3, .1F);
            break;
        case cyanide:
            entityPlayer.attackEntityFrom(DamageSource.generic, 20);
            break;
        case penicillin:
            cureAllPotions(world, entityPlayer);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_MINUTE * 2, 1));
            break;
        case testosterone:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_MINUTE * 5, 0));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            break;
        case xanax:
            cureAllPotions(world, entityPlayer);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 30, 5));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 30, 2));
            break;
        case mescaline:
            entityPlayer.attackEntityFrom(DamageSource.generic, 2);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 30, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.blindness.getId(), Constants.TICKS_PER_SECOND * 30, 2));
            break;
        case quinine:
            cureAllPotions(world, entityPlayer);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_MINUTE * 1, 1));
            break;
        case shikimicAcid:
            // No effect.
            break;
        case salt:
            // No effect.
            break;
        case sulfuricAcid:
            entityPlayer.attackEntityFrom(DamageSource.generic, 5);
            break;
        case buli:
            entityPlayer.attackEntityFrom(DamageSource.generic, 8);
            break;
        case phosgene:
            entityPlayer.setFire(100);
            break;
        case aalc:
            entityPlayer.attackEntityFrom(DamageSource.generic, 5);
            break;
        case ttx:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 60, 10));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 60, 1));
            break;
        case pkone: // Polyketides have many roles in medicine
            entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), Constants.TICKS_PER_MINUTE * 4, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_MINUTE * 4, 0));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_MINUTE * 4, 1));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.getId(), Constants.TICKS_PER_SECOND * 30, 1));
            break;
        case pkthree:
            break;
        case pkfour:
            break;
        case fingolimod:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), Constants.TICKS_PER_MINUTE * 5, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.getId(), Constants.TICKS_PER_SECOND * 80, 1));
            break;
        case afroman:
            cureAllPotions(world, entityPlayer);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 60, 5));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 60, 1));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.getId(), Constants.TICKS_PER_SECOND * 120, 1));
            break;
        case nod:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.getId(), Constants.TICKS_PER_MINUTE * 8, 1));
            break;
        case hist:
            cureAllPotions(world, entityPlayer);
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 20, 5));
            entityPlayer.getFoodStats().addStats(2, .2F);
            break;
        case pal2: // this sh*t is real nasty
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), Constants.TICKS_PER_SECOND * 60, 20));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.wither.getId(), Constants.TICKS_PER_SECOND * 60, 2));
            entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.getId(), Constants.TICKS_PER_SECOND * 60, 5));
            break;
        case theobromine: // Speed boost from coffie :D
            entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), Constants.TICKS_PER_MINUTE * 5, 1));
            break;
        case ret:
            entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Constants.TICKS_PER_SECOND * 120, 1));
            entityPlayer.getFoodStats().addStats(3, .1F);
            break;
        case stevenk:
            entityPlayer.getFoodStats().addStats(2, .2F);
            break;
        default:
            entityPlayer.attackEntityFrom(DamageSource.generic, 5);
            break;
        }
    }

    public static void cureAllPotions(World world, EntityPlayer entityPlayer) {
        List<PotionEffect> activePotions = new ArrayList<PotionEffect>(entityPlayer.getActivePotionEffects());
        for (PotionEffect potionEffect : activePotions) {
            entityPlayer.removePotionEffect(potionEffect.getPotionID());
        }
    }

    /**
     * Ensures that the given inventory is the full inventory, i.e. takes double chests into account.
     * 
     * @param inv
     * @return Modified inventory if double chest, unmodified otherwise. Credit to Buildcraft.
     */
    public static IInventory getInventory(IInventory inv) {
        if (inv instanceof TileEntityChest) {
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

    public static TileEntity getTile(World world, Position pos, ForgeDirection dir) {
        Position tmp = new Position(pos);
        tmp.orientation = dir;
        tmp.moveForwards(1.0);

        return world.getBlockTileEntity((int) tmp.x, (int) tmp.y, (int) tmp.z);
    }

    @SideOnly(Side.SERVER)
    public static WorldServer getDimension(int dimensionID) {
        WorldServer[] worlds = FMLServerHandler.instance().getServer().worldServers;
        for (WorldServer world : worlds) {
            if (world.getWorldInfo().getDimension() == dimensionID)
                return world;
        }
        return null;
    }

    public static String getChemicalName(Chemical chemical) {
        if (chemical instanceof Element)
            return ((Element) chemical).element.descriptiveName();
        else
            return ((Molecule) chemical).molecule.descriptiveName();
    }

    public static ItemStack chemicalToItemStack(Chemical chemical, int amount) {
        if (chemical instanceof Element)
            return new ItemStack(MinechemItems.element, amount, ((Element) chemical).element.ordinal());
        else if (chemical instanceof Molecule)
            return new ItemStack(MinechemItems.molecule, amount, ((Molecule) chemical).molecule.id());
        return null;
    }

    public static Chemical itemStackToChemical(ItemStack itemstack) {
        if (Util.isStackAnElement(itemstack)) {
            return new Element(ItemElement.getElement(itemstack), itemstack.stackSize);
        } else if (Util.isStackAMolecule(itemstack)) { return new Molecule(ItemMolecule.getMolecule(itemstack), itemstack.stackSize); }
        return null;
    }
}
