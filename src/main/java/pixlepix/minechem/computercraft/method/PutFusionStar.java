package pixlepix.minechem.computercraft.method;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;
import pixlepix.minechem.common.MinechemItems;
import pixlepix.minechem.computercraft.ICCMethod;
import pixlepix.minechem.computercraft.IMinechemMachinePeripheral;

public class PutFusionStar extends InteractMachine implements ICCMethod {

    @Override
    public String getMethodName() {
        return "putFusionStar";
    }

    @Override
    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception {
        IMinechemMachinePeripheral machine = getMachineInFront(turtle);
        ItemStack selectedStack = turtle.getSlotContents(turtle.getSelectedSlot());
        boolean didPut = false;
        if (machine != null && selectedStack != null
                && (selectedStack.itemID == Item.netherStar.itemID || selectedStack.itemID == MinechemItems.fusionStar.itemID)) {
            ItemStack before = selectedStack.copy();
            int used = machine.putFusionStar(selectedStack);
            didPut = tryPut(before, used, turtle);
        }
        return new Object[] { didPut };
    }

}
