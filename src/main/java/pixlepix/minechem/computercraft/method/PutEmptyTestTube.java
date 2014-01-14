package pixlepix.minechem.computercraft.method;

import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;
import net.minecraft.item.ItemStack;
import pixlepix.minechem.api.util.Util;
import pixlepix.minechem.computercraft.ICCMethod;
import pixlepix.minechem.computercraft.IMinechemMachinePeripheral;

public class PutEmptyTestTube extends InteractMachine implements ICCMethod {

    @Override
    public String getMethodName() {
        return "putEmptyTestTube";
    }

    @Override
    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception {
        boolean didPut = false;
        IMinechemMachinePeripheral machine = getMachineInFront(turtle);
        int selectedSlot = turtle.getSelectedSlot();
        ItemStack selectedStack = turtle.getSlotContents(selectedSlot);
        if (machine != null && selectedStack != null && Util.isStackAnEmptyTestTube(selectedStack)) {
            ItemStack before = selectedStack.copy();
            int used = machine.putEmptyTestTube(selectedStack);
            didPut = tryPut(before, used, turtle);
        }
        return new Object[]{didPut};
    }

}
