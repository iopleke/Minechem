<<<<<<< HEAD:src/main/java/minechem/slot/SlotChemical.java
package minechem.slot;

import minechem.MinechemItemsGeneration;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotChemical extends Slot
{

    public SlotChemical(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack)
    {
        return itemStack.itemID == MinechemItemsGeneration.element.itemID || itemStack.itemID == MinechemItemsGeneration.molecule.itemID;
    }

}
=======
package minechem.slot;

import minechem.MinechemItemsGeneration;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotChemical extends Slot
{

    public SlotChemical(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack)
    {
        return itemStack.itemID == MinechemItemsGeneration.element.itemID || itemStack.itemID == MinechemItemsGeneration.molecule.itemID;
    }

}
>>>>>>> MaxwolfRewrite:src/main/java/minechem/slot/SlotChemical.java
