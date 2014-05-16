package minechem.computercraft.method;

import minechem.api.util.Util;
import minechem.common.RadiationHandler;
import minechem.computercraft.ICCMethod;
import net.minecraft.item.ItemStack;
import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;

public class GetTicksUntilDecay implements ICCMethod
{

    @Override
    public String getMethodName()
    {
        return "getTicksUntilDecay";
    }

    @Override
    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception
    {
        int selectedSlot = turtle.getSelectedSlot();
        ItemStack selectedStack = turtle.getSlotContents(selectedSlot);
        Object result = null;
        if (selectedStack != null && Util.isStackAnElement(selectedStack))
        {
            result = RadiationHandler.getInstance().getTicksUntilDecay(selectedStack, turtle.getWorld());
        }
        return new Object[]
        { result };
    }

}
