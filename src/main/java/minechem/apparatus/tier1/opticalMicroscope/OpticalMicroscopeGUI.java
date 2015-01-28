package minechem.apparatus.tier1.opticalMicroscope;

import minechem.reference.Compendium;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

/**
 *
 * @author jakimfett
 */
public class OpticalMicroscopeGUI extends GuiContainer
{

    public OpticalMicroscopeGUI(InventoryPlayer inventoryPlayer, OpticalMicroscopeTileEntity opticalMicroscope)
    {
        super(new OpticalMicroscopeContainer(inventoryPlayer, opticalMicroscope));

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int mousex, int mousey)
    {
        this.mc.renderEngine.bindTexture(Compendium.Resource.GUI.opticalMicroscope);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

}
