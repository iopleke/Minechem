package minechem.utils;

import minechem.item.element.ElementItem;
import minechem.item.molecule.MoleculeItem;
import net.minecraft.item.ItemStack;

public class Compare
{

	public static boolean stacksAreSameKind(ItemStack is1, ItemStack is2)
	{
		int dmg1 = is1.getItemDamage();
		int dmg2 = is2.getItemDamage();
		return is1.getItem() == is2.getItem() && (dmg1 == -1 || dmg2 == -1 || (dmg1 == dmg2));
	}

	public static boolean isStackAChemical(ItemStack itemstack)
	{
		return itemstack.getItem() instanceof ElementItem || itemstack.getItem() instanceof MoleculeItem;
	}

	public static boolean isStackAnElement(ItemStack itemstack)
	{
		return itemstack.getItem() instanceof ElementItem;
	}

	public static boolean isStackAMolecule(ItemStack itemstack)
	{
		return itemstack.getItem() instanceof MoleculeItem;
	}

	public static boolean isStackAnEmptyTestTube(ItemStack itemstack)
	{
		return itemstack.toString().contains("minechem.itemTestTube");
	}

	public static String stringSieve(String str)
	{
		return str.toLowerCase()
				.trim() // remove trailing whitespace
				.replaceAll("\\s", "") // replace spaces
				.replaceAll("block", "")
				.replaceAll("item", "");
	}

}
