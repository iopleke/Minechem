package universalelectricity.api.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import universalelectricity.api.UnitDisplay;
import universalelectricity.api.UnitDisplay.Unit;
import universalelectricity.compatibility.Compatibility;

import java.util.List;

/**
 * Extend from this class if your item requires electricity or to be charged. Optionally, you can
 * implement IItemElectric instead.
 *
 * @author Calclavia
 */
public abstract class ItemElectric extends Item implements IEnergyItem
{
	private static final String ENERGY_NBT = "electricity";

	public ItemElectric()
	{
		super();
		setMaxStackSize(1);
		setMaxDamage(100);
		setNoRepair();
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4)
	{
		String color = "";
		double joules = getEnergy(itemStack);

		if (joules <= getEnergyCapacity(itemStack) / 3)
		{
			color = "\u00a74";
		}
		else if (joules > getEnergyCapacity(itemStack) * 2 / 3)
		{
			color = "\u00a72";
		}
		else
		{
			color = "\u00a76";
		}

		list.add(color + new UnitDisplay(Unit.JOULES, joules) + "/" + new UnitDisplay(Unit.JOULES, getEnergyCapacity(itemStack)).symbol());
	}

	/**
	 * Makes sure the item is uncharged when it is crafted and not charged. Change this if you do
	 * not want this to happen!
	 */
	@Override
	public void onCreated(ItemStack itemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		setEnergy(itemStack, 0);
	}

	@Override
	public double recharge(ItemStack itemStack, double energy, boolean doReceive)
	{
		double energyReceived = Math.min(getEnergyCapacity(itemStack) - getEnergy(itemStack), Math.min(getTransferRate(itemStack), energy));

		if (doReceive)
		{
			setEnergy(itemStack, getEnergy(itemStack) + energyReceived);
		}

		return energyReceived;
	}

	public double getTransferRate(ItemStack itemStack)
	{
		return getEnergyCapacity(itemStack) / 100;
	}

	@Override
	public double discharge(ItemStack itemStack, double energy, boolean doTransfer)
	{
		double energyExtracted = Math.min(getEnergy(itemStack), Math.min(getTransferRate(itemStack), energy));

		if (doTransfer)
		{
			setEnergy(itemStack, getEnergy(itemStack) - energyExtracted);
		}

		return energyExtracted;
	}

	@Override
	public double getVoltage(ItemStack itemStack)
	{
		return 120;
	}

	@Override
	public void setEnergy(ItemStack itemStack, double joules)
	{
		if (itemStack.getTagCompound() == null)
		{
			itemStack.setTagCompound(new NBTTagCompound());
		}

		double electricityStored = Math.max(Math.min(joules, getEnergyCapacity(itemStack)), 0);
		itemStack.getTagCompound().setDouble(ENERGY_NBT, electricityStored);
		itemStack.setItemDamage((int) (100 - ((double) electricityStored / (double) getEnergyCapacity(itemStack)) * 100));
	}

	public double getTransfer(ItemStack itemStack)
	{
		return getEnergyCapacity(itemStack) - getEnergy(itemStack);
	}

	@Override
	public double getEnergy(ItemStack itemStack)
	{
		if (itemStack.getTagCompound() == null)
		{
			itemStack.setTagCompound(new NBTTagCompound());
		}

		double energyStored = itemStack.getTagCompound().getDouble(ENERGY_NBT);
		itemStack.setItemDamage((int) (100 - ((double) energyStored / (double) getEnergyCapacity(itemStack)) * 100));
		return energyStored;
	}

	@Override
	public void getSubItems(Item id, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(Compatibility.getHandler(this).getItemWithCharge(new ItemStack(this), 0));
		par3List.add(Compatibility.getHandler(this).getItemWithCharge(new ItemStack(this), getEnergyCapacity(new ItemStack(this))));
	}
}
