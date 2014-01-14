package pixlepix.minechem.computercraft.method;

import pixlepix.minechem.api.core.EnumElement;
import pixlepix.minechem.api.util.Util;
import pixlepix.minechem.common.items.ItemElement;
import pixlepix.minechem.computercraft.ICCMethod;
import net.minecraft.item.ItemStack;
import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;

public class GetAtomicMass implements ICCMethod {

    @Override
    public String getMethodName() {
        return "getAtomicMass";
    }

    @Override
    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception {
        if (arguments.length == 1) {
            return getAtomicMassFromString(arguments[0]);
        } else {
            return getAtomicMassFromSlot(turtle.getSelectedSlot(), turtle);
        }
    }

    private Object[] getAtomicMassFromString(Object object) throws Exception {
        if (object instanceof String) {
            String query = (String) object;
            EnumElement element = EnumElement.valueOf(query);
            if (element != null)
                return new Object[] { element.atomicNumber() };
        } else {
            throw new Exception("Argument must be String");
        }
        return null;
    }

    private Object[] getAtomicMassFromSlot(int selectedSlot, ITurtleAccess turtle) {
        ItemStack selectedStack = turtle.getSlotContents(selectedSlot);
        if (selectedStack != null && Util.isStackAnElement(selectedStack)) {
            EnumElement element = ItemElement.getElement(selectedStack);
            return new Object[] { element.atomicNumber() };
        }
        return null;
    }

}
