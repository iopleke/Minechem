package pixlepix.minechem.minechem.computercraft.method;

import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;
import pixlepix.minechem.minechem.computercraft.ICCMethod;
import pixlepix.minechem.minechem.computercraft.IMinechemMachinePeripheral;

public class GetMachineState extends InteractMachine implements ICCMethod {

    @Override
    public String getMethodName() {
        return "getMachineState";
    }

    @Override
    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception {
        IMinechemMachinePeripheral machine = getMachineInFront(turtle);
        String result = null;
        if (machine != null) {
            result = machine.getMachineState();
        }
        return new Object[] { result };
    }

}
