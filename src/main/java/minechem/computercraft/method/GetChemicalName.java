package minechem.computercraft.method;

import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;
import minechem.api.util.Util;
import minechem.common.items.ItemElement;
import minechem.common.items.ItemMolecule;
import minechem.computercraft.ICCMethod;
import net.minecraft.item.ItemStack;

public class GetChemicalName implements ICCMethod {

    @Override
    public String getMethodName() {
        return "getChemicalName";
    }

    @Override
    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception {
        if (arguments.length != 0)
            throw new Exception("Invalid number of arguments");
        Object result = null;
        int selectedSlot = turtle.getSelectedSlot();
        ItemStack selectedStack = turtle.getSlotContents(selectedSlot);
        if (selectedStack != null) {
            if (Util.isStackAnElement(selectedStack)) {
                result = ItemElement.getLongName(selectedStack);
            } else if (Util.isStackAMolecule(selectedStack)) {
                result = ItemMolecule.getMolecule(selectedStack).descriptiveName();
            }
        }
        return new Object[]{result};
    }

}
