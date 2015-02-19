package minechem.apparatus.tier1.opticalMicroscope;

import minechem.apparatus.prefab.gui.container.BasicContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class OpticalMicroscopeContainer extends BasicContainer
{
    private OpticalMicroscopeTileEntity opticalMicroscope;
    /**
     * Container object for the opticalMicroscope
     *
     * @param inventoryPlayer   the player's inventory
     * @param opticalMicroscope the microscope TileEntity
     */
    public OpticalMicroscopeContainer(InventoryPlayer inventoryPlayer, OpticalMicroscopeTileEntity opticalMicroscope)
    {
        bindPlayerInventory(inventoryPlayer);
        addSlotToContainer(new Slot(opticalMicroscope, 0, 32, 32));
        this.opticalMicroscope = opticalMicroscope;
    }
    
    public OpticalMicroscopeTileEntity getOpticalMicroscope()
    {
        return opticalMicroscope;
    }
}
