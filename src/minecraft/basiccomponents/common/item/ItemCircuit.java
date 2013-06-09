package basiccomponents.common.item;

import java.util.List;

import basiccomponents.common.BasicComponents;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemCircuit extends ItemBase
{
	public static final String[] TYPES = { "circuitBasic", "circuitAdvanced", "circuitElite" };

	public ItemCircuit(int id, int texture)
	{
		super("circuit", id);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return "item." + BasicComponents.TEXTURE_NAME_PREFIX + TYPES[itemStack.getItemDamage()];
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for (int i = 0; i < TYPES.length; i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}
}
