package pixlepix.minechem.common.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotFake extends Slot {

    public SlotFake(IInventory iInventory, int id, int x, int y) {
        super(iInventory, id, x, y);
    }

}
