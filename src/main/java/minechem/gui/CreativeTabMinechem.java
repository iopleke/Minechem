package minechem.gui;

import minechem.MinechemBlocksGeneration;
import minechem.MinechemItemsRegistration;
import minechem.item.element.ElementEnum;
import minechem.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabMinechem extends CreativeTabs
{
	public static CreativeTabs CREATIVE_TAB_ITEMS = new CreativeTabMinechem(Reference.NAME, 0);
	public static CreativeTabs CREATIVE_TAB_ELEMENTS = new CreativeTabMinechem(Reference.NAME + ".Elements", 1);

	private int tabIcon;

	public CreativeTabMinechem(String tabName, int i)
	{
		super(tabName);
		this.tabIcon = i;
	}

	@Override
	public Item getTabIconItem()
	{
		return null;
	}

	@Override
	public ItemStack getIconItemStack()
	{
		switch (this.tabIcon)
		{
			case 0:
				return new ItemStack(MinechemBlocksGeneration.microscope);
			case 1:
				return new ItemStack(MinechemItemsRegistration.element, 1, ElementEnum.U.ordinal());
			default:
				return new ItemStack(Items.fermented_spider_eye);
		}
	}
}
