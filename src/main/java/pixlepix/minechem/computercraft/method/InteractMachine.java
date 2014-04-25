package pixlepix.minechem.computercraft.method;

import dan200.turtle.api.ITurtleAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import pixlepix.minechem.common.utils.Position;
import pixlepix.minechem.computercraft.IMinechemMachinePeripheral;

public class InteractMachine {

    public IMinechemMachinePeripheral getMachineInFront(ITurtleAccess turtle) {
        Vec3 vector = turtle.getPosition();
        ForgeDirection direction = ForgeDirection.getOrientation(turtle.getFacingDir());
        Position position = new Position(vector.xCoord, vector.yCoord, vector.zCoord, direction);
        position.moveForwards(1.0F);
        World world = turtle.getWorld();
        TileEntity tileEntity = world.getBlockTileEntity((int) position.x, (int) position.y, (int) position.z);
        if (tileEntity != null && tileEntity instanceof IMinechemMachinePeripheral)
            return (IMinechemMachinePeripheral) tileEntity;
        else
            return null;
    }

    public boolean tryPut(ItemStack beforeStack, int used, ITurtleAccess turtle) {
        int selectedSlot = turtle.getSelectedSlot();
        beforeStack.stackSize -= used;
        if (beforeStack.stackSize <= 0)
            turtle.setSlotContents(selectedSlot, null);
        else
            turtle.setSlotContents(selectedSlot, beforeStack);
        if (used > 0)
            return true;
        else
            return false;
    }

}
