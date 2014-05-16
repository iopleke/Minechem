package minechem.computercraft.method;

import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;
import minechem.api.util.Util;
import minechem.common.MinechemItems;
import minechem.common.items.ItemElement;
import minechem.computercraft.ICCMethod;
import net.minecraft.item.ItemStack;

public class GetFormula implements ICCMethod {

    @Override
    public String getMethodName() {
        return "getChemicalFormula";
    }

    @Override
    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception {
        Object result = null;
        int selectedSlot = turtle.getSelectedSlot();
        ItemStack selectedStack = turtle.getSlotContents(selectedSlot);
        if (selectedStack != null) {
            if (Util.isStackAMolecule(selectedStack))
                result = MinechemItems.molecule.getFormula(selectedStack);
            if (Util.isStackAnElement(selectedStack))
                result = ItemElement.getShortName(selectedStack);
        }
        return new Object[]{result};
    }

}
