package minechem.computercraft.method;

import minechem.computercraft.ICCMethod;
import minechem.computercraft.IMinechemMachinePeripheral;
import net.minecraft.item.ItemStack;
import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;

public class TakeJournal extends InteractMachine implements ICCMethod
{

    @Override
    public String getMethodName()
    {
        return "takeJournal";
    }

    @Override
    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception
    {
        IMinechemMachinePeripheral machine = getMachineInFront(turtle);
        boolean didTake = false;
        if (machine != null)
        {
            ItemStack takenStack = machine.takeJournal();
            if (takenStack != null)
            {
                if (turtle.storeItemStack(takenStack))
                {
                    didTake = true;
                }
                else
                {
                    machine.putJournal(takenStack);
                }
            }
        }
        return new Object[]
        { didTake };
    }

}
