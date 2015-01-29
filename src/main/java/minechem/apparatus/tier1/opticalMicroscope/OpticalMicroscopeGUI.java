package minechem.apparatus.tier1.opticalMicroscope;

import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.reference.Compendium;
import net.minecraft.entity.player.InventoryPlayer;

/**
 *
 * @author jakimfett
 */
public class OpticalMicroscopeGUI extends BasicGuiContainer
{

    public OpticalMicroscopeGUI(InventoryPlayer inventoryPlayer, OpticalMicroscopeTileEntity opticalMicroscope)
    {
        super(new OpticalMicroscopeContainer(inventoryPlayer, opticalMicroscope));
        texture = Compendium.Resource.GUI.opticalMicroscope;
    }
}
