package ljdp.minechem.computercraft.method;

import net.minecraft.item.ItemStack;
import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;
import ljdp.minechem.computercraft.ICCMethod;
import ljdp.minechem.computercraft.IMinechemMachinePeripheral;

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
        return new Object[] { didTake };
    }

}
