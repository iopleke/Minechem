package pixlepix.minechem.common.polytool;

import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import pixlepix.minechem.api.core.EnumElement;
import pixlepix.minechem.common.GuiHandler;
import pixlepix.minechem.common.ModMinechem;
import pixlepix.minechem.common.items.ItemElement;
import pixlepix.minechem.common.utils.ConstantValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemPolytool extends ItemPickaxe implements IEnergyContainerItem {

	public static ItemPolytool instance;

	public ItemPolytool(int par1) {
		super(par1, EnumToolMaterial.IRON);
		instance = this;
		setCreativeTab(ModMinechem.minechemTab);
		setUnlocalizedName("Polytool");
	}

	public static boolean validAlloyInfusion(ItemStack polytool, ItemStack element) {
		if (element.getItem() instanceof ItemElement) {
			PolytoolUpgradeType upgrade = PolytoolHelper.getTypeFromElement((ItemElement.getElement(element)), 1);
			if (upgrade instanceof PolytoolTypeAlloy) {
				ItemStack toApply = polytool.copy();
				addTypeToNBT(toApply, upgrade);
				if (!(instance.getSwordStr(toApply) > 0 && instance.getPickaxeStr(toApply) > 0 && instance.getStoneStr(toApply) > 0 && instance.getAxeStr(toApply) > 0 && instance.getShovelStr(toApply) > 0)) {
					return false;

				}
			}
		}
		return true;

	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer entityPlayer) {
		//Copied from journal code
		//I don't know why chunkCoordX is used
		//But LJDP probably knows, and he is smarter than me
		entityPlayer.openGui(ModMinechem.instance, GuiHandler.GUI_ID_POLYTOOL, world, entityPlayer.chunkCoordX, entityPlayer.chunkCoordY,
				entityPlayer.chunkCoordY);
		return par1ItemStack;
	}

	public float getSwordStr(ItemStack stack) {
		return this.getStrVsBlock(stack, Block.web);
	}

	public float getPickaxeStr(ItemStack stack) {
		return this.getStrVsBlock(stack, Block.oreCoal);
	}

	public float getStoneStr(ItemStack stack) {
		return this.getStrVsBlock(stack, Block.stone);
	}

	public float getAxeStr(ItemStack stack) {
		return this.getStrVsBlock(stack, Block.wood);
	}

	public float getShovelStr(ItemStack stack) {
		return this.getStrVsBlock(stack, Block.dirt);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		itemIcon = ir.registerIcon(ConstantValue.POLYTOOL_TEX);
	}

	@Override
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block) {
		float sum = 8;
		ArrayList upgrades = getUpgrades(par1ItemStack);
		Iterator iter = upgrades.iterator();
		while (iter.hasNext()) {
			sum += ((PolytoolUpgradeType) iter.next()).getStrVsBlock(par1ItemStack, par2Block);
		}
		return sum;
	}

	public static ArrayList getUpgrades(ItemStack stack) {
		ensureNBT(stack);
		ArrayList toReturn = new ArrayList();
		NBTTagList list = stack.stackTagCompound.getTagList("Upgrades");
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt = (NBTTagCompound) list.tagAt(i);
			toReturn.add(PolytoolHelper.getTypeFromElement(EnumElement.values()[nbt.getInteger("Element")], nbt.getFloat("Power")));
		}
		return toReturn;

	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
		par2EntityLivingBase.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) par3EntityLivingBase), getSwordStr(par1ItemStack));

		ArrayList upgrades = getUpgrades(par1ItemStack);
		Iterator iter = upgrades.iterator();
		while (iter.hasNext()) {
			((PolytoolUpgradeType) iter.next()).hitEntity(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
		}
		return true;
	}

	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase) {

		ArrayList upgrades = getUpgrades(par1ItemStack);
		Iterator iter = upgrades.iterator();
		while (iter.hasNext()) {
			((PolytoolUpgradeType) iter.next()).onBlockDestroyed(par1ItemStack, par2World, par3, par4, par5, par6, par7EntityLivingBase);
		}
		return true;
	}

	public static void addTypeToNBT(ItemStack stack, PolytoolUpgradeType type) {
		ensureNBT(stack);
		NBTTagCompound compound = new NBTTagCompound();
		NBTTagList list = stack.stackTagCompound.getTagList("Upgrades");
		for (int i = 0; i < list.tagCount(); i++) {
			if (((NBTTagCompound) list.tagAt(i)).getInteger("Element") == type.getElement().ordinal()) {
				((NBTTagCompound) list.tagAt(i)).setFloat("Power", ((NBTTagCompound) list.tagAt(i)).getFloat("Power") + type.power);

				return;
			}
		}
		compound.setFloat("Power", type.power);
		compound.setInteger("Element", type.getElement().ordinal());
		list.appendTag(compound);

	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		ArrayList upgrades = getUpgrades(par1ItemStack);
		Iterator iter = upgrades.iterator();
		while (iter.hasNext()) {
			((PolytoolUpgradeType) iter.next()).onTickFull(par1ItemStack, par2World, par3Entity, par4, par5);
		}
	}

	public static void ensureNBT(ItemStack item) {
		if (item.stackTagCompound == null) {
			item.stackTagCompound = new NBTTagCompound();
		}
		if (!item.stackTagCompound.hasKey("Upgrades")) {
			item.stackTagCompound.setTag("Upgrades", new NBTTagList());
		}
		if (!item.stackTagCompound.hasKey("Energy")) {
			item.stackTagCompound.setInteger("Energy", 0);
		}
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive,
	                         boolean simulate) {
		ensureNBT(container);

		if (getPowerOfType(container, EnumElement.Li) != 0) {

			if (!simulate) {
				int toAdd = (int) (maxReceive * getPowerOfType(container, EnumElement.Li));
				container.stackTagCompound.setInteger("Energy", getEnergyStored(container) + toAdd);

			}
			return maxReceive;

		}
		return maxReceive;
	}

	public static float getPowerOfType(ItemStack item, EnumElement element) {
		ArrayList upgrades = getUpgrades(item);
		Iterator iter = upgrades.iterator();
		while (iter.hasNext()) {
			PolytoolUpgradeType next = (PolytoolUpgradeType) iter.next();
			if (next.getElement().ordinal() == element.ordinal()) {

				return next.power;
			}
		}
		return 0;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxReceive,
	                         boolean simulate) {
		return 0;

	}

	@Override
	public int getEnergyStored(ItemStack container) {
		ensureNBT(container);
		if (getPowerOfType(container, EnumElement.Li) != 0) {
			return container.stackTagCompound.getInteger("Energy");
		}
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
	                           EntityPlayer par2EntityPlayer, List list, boolean par4) {

		super.addInformation(par1ItemStack, par2EntityPlayer, list, par4);
		if (this.getPowerOfType(par1ItemStack, EnumElement.Be) != 0) {
			list.add("Stored: " + this.getEnergyStored(par1ItemStack) + ", +" + Math.ceil(Math.max(0, Math.log10(this.getEnergyStored(par1ItemStack)) - 7)));
		}
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {

		return Integer.MAX_VALUE;
	}

}
