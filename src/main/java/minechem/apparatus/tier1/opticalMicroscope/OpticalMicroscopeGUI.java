package minechem.apparatus.tier1.opticalMicroscope;

import minechem.apparatus.prefab.gui.container.BasicGuiContainer;
import minechem.reference.Compendium;
import net.minecraft.client.Minecraft;
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

    private void drawMicroscopeOverlay()
    {
        this.zLevel = 600F;
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        drawTexturedModalRect(((width - 176) / 2) + 13, ((height - 166) / 2) + 13, 176, 0, 54, 54);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int mouseX, int mouseY)
    {
        super.drawGuiContainerBackgroundLayer(opacity, mouseX, mouseY);
        drawMicroscopeOverlay();
    }
}
