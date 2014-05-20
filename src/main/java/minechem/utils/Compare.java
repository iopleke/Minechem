package minechem.utils;

import net.minecraft.item.ItemStack;

public class Compare
{

    public static boolean stacksAreSameKind(ItemStack is1, ItemStack is2)
    {
        int dmg1 = is1.getItemDamage();
        int dmg2 = is2.getItemDamage();
        return is1.itemID == is2.itemID && (dmg1 == -1 || dmg2 == -1 || (dmg1 == dmg2));
    }

    public static boolean isStackAChemical(ItemStack itemstack)
    {
        return itemstack.toString().contains("minechem.itemElement") || itemstack.toString().contains("minechem.itemMolecule");
    }

    public static boolean isStackAnElement(ItemStack itemstack)
    {
        return itemstack.toString().contains("minechem.itemElement");
    }

    public static boolean isStackAMolecule(ItemStack itemstack)
    {
        return itemstack.toString().contains("minechem.itemMolecule");
    }

    public static boolean isStackAnEmptyTestTube(ItemStack itemstack)
    {
        return itemstack.toString().contains("minechem.itemTestTube");
    }

}
