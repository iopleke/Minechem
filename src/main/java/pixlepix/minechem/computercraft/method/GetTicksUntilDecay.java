package pixlepix.minechem.computercraft.method;

import dan200.computer.api.IComputerAccess;
import dan200.turtle.api.ITurtleAccess;
import net.minecraft.item.ItemStack;
import pixlepix.minechem.api.util.Util;
import pixlepix.minechem.common.RadiationHandler;
import pixlepix.minechem.computercraft.ICCMethod;

public class GetTicksUntilDecay implements ICCMethod {

    @Override
    public String getMethodName() {
        return "getTicksUntilDecay";
    }

    @Override
    public Object[] call(IComputerAccess computer, ITurtleAccess turtle, Object[] arguments) throws Exception {
        int selectedSlot = turtle.getSelectedSlot();
        ItemStack selectedStack = turtle.getSlotContents(selectedSlot);
        Object result = null;
        if (selectedStack != null && Util.isStackAnElement(selectedStack)) {
            result = RadiationHandler.getInstance().getTicksUntilDecay(selectedStack, turtle.getWorld());
        }
        return new Object[]{result};
    }

}
