package basiccomponents.common.item;

import net.minecraft.creativetab.CreativeTabs;

public class ItemPlate extends ItemBase
{
	public ItemPlate(String name, int id)
	{
		super(name, id);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}
}
