package basiccomponents.common.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import universalelectricity.core.item.ItemElectric;
import basiccomponents.common.BasicComponents;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBattery extends ItemElectric
{
	public ItemBattery(String name, int id)
	{
		super(BasicComponents.CONFIGURATION.getItem(name, id).getInt(id));
		this.setUnlocalizedName(BasicComponents.TEXTURE_NAME_PREFIX + name);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		this.itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public double getMaxJoules(ItemStack itemStack)
	{
		return 1000000;
	}

	@Override
	public double getVoltage(ItemStack itemStack)
	{
		return 25;
	}
}
