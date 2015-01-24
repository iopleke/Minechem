package minechem.apparatus.tier1.opticalMicroscope;

import minechem.apparatus.prefab.container.BasicContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class OpticalMicroscopeContainer extends BasicContainer
{
    /**
     * Container object for the workbench
     *
     * @param inventoryPlayer   the player's inventory
     * @param opticalMicroscope the bench TileEntity
     */
    public OpticalMicroscopeContainer(InventoryPlayer inventoryPlayer, OpticalMicroscopeTileEntity opticalMicroscope)
    {
        bindPlayerInventory(inventoryPlayer);
    }
}
