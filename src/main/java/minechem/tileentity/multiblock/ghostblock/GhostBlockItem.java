package minechem.tileentity.multiblock.ghostblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class GhostBlockItem extends ItemBlock
{
	private final static String[] subNames =
	{
		"white", "orange", "magenta", "lightBlue", "yellow", "lightGreen", "pink", "darkGrey", "lightGrey", "cyan", "purple", "blue", "brown", "green", "red", "black"
	};

	public GhostBlockItem(Block block)
	{
		super(block);
		setUnlocalizedName("itemGhostBlock");
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damageValue)
	{
		return damageValue;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		if (itemstack.getItemDamage() < GhostBlockItem.subNames.length)
		{
			return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
		}
		return "white";
	}

}
