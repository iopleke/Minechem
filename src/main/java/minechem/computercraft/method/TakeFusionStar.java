package minechem.computercraft.method;

import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;
import minechem.computercraft.ICCMethod;
import minechem.computercraft.IMinechemMachinePeripheral;
import net.minecraft.item.ItemStack;

public class TakeFusionStar extends InteractMachine implements ICCMethod {

    @Override
    public String getMethodName() {
        return "takeFusionStar";
    }

    @Override
    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception {
        IMinechemMachinePeripheral machine = getMachineInFront(turtle);
        boolean didTake = false;
        if (machine != null) {
            ItemStack takenStack = machine.takeFusionStar();
            if (takenStack != null) {
                if (turtle.storeItemStack(takenStack)) {
                    didTake = true;
                } else {
                    machine.putFusionStar(takenStack);
                }
            }
        }
        return new Object[]{didTake};
    }

}
