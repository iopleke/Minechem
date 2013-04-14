package ljdp.minechem.api.util;

import net.minecraft.item.ItemStack;

public class Util {

    public static boolean stacksAreSameKind(ItemStack is1, ItemStack is2) {
        int dmg1 = is1.getItemDamage();
        int dmg2 = is2.getItemDamage();
        return is1.itemID == is2.itemID && (dmg1 == -1 || dmg2 == -1 || (dmg1 == dmg2));
    }

    public static boolean isStackAChemical(ItemStack itemstack) {
        return itemstack.getItemName().contains("minechem.itemElement") || itemstack.getItemName().contains("minechem.itemMolecule");
    }

    public static boolean isStackAnElement(ItemStack itemstack) {
        return itemstack.getItemName().contains("minechem.itemElement");
    }

    public static boolean isStackAMolecule(ItemStack itemstack) {
        return itemstack.getItemName().contains("minechem.itemMolecule");
    }

    public static boolean isStackAnEmptyTestTube(ItemStack itemstack) {
        return itemstack.getItemName().contains("minechem.itemTestTube");
    }

}
