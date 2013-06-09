package basiccomponents.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import basiccomponents.common.BasicComponents;

public class BlockBase extends Block
{
	public BlockBase(String name, int id)
	{
		super(BasicComponents.CONFIGURATION.getItem(name, id).getInt(id), Material.rock);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setUnlocalizedName(BasicComponents.TEXTURE_NAME_PREFIX + name);
		this.setHardness(2f);
	}
}
